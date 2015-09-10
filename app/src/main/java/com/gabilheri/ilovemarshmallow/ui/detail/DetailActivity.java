package com.gabilheri.ilovemarshmallow.ui.detail;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.gabilheri.ilovemarshmallow.Const;
import com.gabilheri.ilovemarshmallow.MarshmallowApp;
import com.gabilheri.ilovemarshmallow.MarshmallowUtils;
import com.gabilheri.ilovemarshmallow.R;
import com.gabilheri.ilovemarshmallow.base.BaseActivity;
import com.gabilheri.ilovemarshmallow.base.RxCallback;
import com.gabilheri.ilovemarshmallow.base.RxSubscriber;
import com.gabilheri.ilovemarshmallow.data.DataContract;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.AsinProduct;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.ChildAsin;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;
import com.gabilheri.ilovemarshmallow.ui.ImagePagerAdapter;
import com.gabilheri.ilovemarshmallow.ui.RoundTransformation;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public class DetailActivity extends BaseActivity implements RxCallback<AsinProduct>, AppBarLayout.OnOffsetChangedListener {

    static final String ASIN_DATA = "asinData";
    static final String ITEM_DATA = "itemData";
    static final int SHARE = 999;

    @Bind(R.id.main_content)
    CoordinatorLayout mainLayout;

//    @Nullable
//    @Bind(R.id.item_bg)
//    ImageView mItemBackground;

    @Bind(R.id.viewpager)
    ViewPager mPager;

    @Bind(R.id.item_image)
    ImageView mItemImage;

    @Bind(R.id.description)
    HtmlTextView mDescription;

    @Bind(R.id.product_name)
    AppCompatTextView mProductName;

    @Bind(R.id.item_price)
    AppCompatTextView mPrice;

    @Bind(R.id.item_rating_bar)
    AppCompatRatingBar mRatingBar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.appbar)
    AppBarLayout mAppBar;

    @Bind(R.id.fab_favorite)
    FloatingActionButton fabFavorite;

    boolean mIsFavorite = false;
    AsinProduct mAsinProduct;
    SearchResultItem mItem;

    Animator mCircularReveal;

    float initialY = -1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableBackNav();
        setTitle("");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mItem = Parcels.unwrap(extras.getParcelable(Const.ASIN));
        }

        if (mItem != null) {
            loadItemImage(mItem.getImageUrl());
            mRatingBar.setProgress((int) mItem.getProductRating());
            mPrice.setText(mItem.getPrice());

            if (savedInstanceState != null) {
                mAsinProduct = Parcels.unwrap(savedInstanceState.getParcelable(ASIN_DATA));
                mItem = Parcels.unwrap(savedInstanceState.getParcelable(ITEM_DATA));
                onDataReady(mAsinProduct);
            } else {
                loadDataFromAsin(mItem.getAsin());
            }
        } else {
            Uri data = getIntent().getData();
            if (data != null) {
                String asin = data.getPathSegments().get(0);
                loadDataFromAsin(asin);
            }
        }

        mAppBar.addOnOffsetChangedListener(this);

        CollapsingToolbarLayout.LayoutParams detailsLP =
                (CollapsingToolbarLayout.LayoutParams) mPager.getLayoutParams();

        detailsLP.setParallaxMultiplier(0.9f);
        mPager.setLayoutParams(detailsLP);
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.grey_800));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.grey_200));
    }

    void loadDataFromAsin(String asin) {
        MarshmallowApp.instance().api().getAsinProduct(asin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<>(this));
    }

    void loadItemImage(String url) {
        Picasso.with(this)
                .load(url)
                .transform(new RoundTransformation(10))
                .into(mItemImage);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @OnClick(R.id.fab_favorite)
    void favoriteItem(View v) {
        if (mAsinProduct != null) {
            handleFavoritePressed();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                animateCircularReveal(v);
            } else {
                changeFabColor();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void animateCircularReveal(final View v) {
        int centerX = (v.getLeft() + v.getRight()) / 2;
        int centerY = (v.getTop() + v.getBottom()) / 2;
        final int finalRadius = Math.max(mainLayout.getWidth(), mainLayout.getHeight());
        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.accent_color)));

        mCircularReveal = ViewAnimationUtils.createCircularReveal(mainLayout, centerX, centerY, 0, finalRadius);
        mCircularReveal.setDuration(1000);
        mCircularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.window_background)));
                changeFabColor();
                mCircularReveal = ViewAnimationUtils.createCircularReveal(v, 0, 0, 0, finalRadius);
                mCircularReveal.setDuration(1000).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mCircularReveal.start();
    }

    void handleFavoritePressed() {
        if (mAsinProduct != null) {
            if (!mIsFavorite) {
                mIsFavorite = true;

                ChildAsin childAsin = null;
                String price = "No price available";
                String originalPrice = "";
                float rating = 0f;

                if (mItem != null) {
                    rating = mItem.getProductRating();
                    price = mItem.getPrice();
                }

                if (mAsinProduct.getChildAsins().size() > 0) {
                    childAsin = mAsinProduct.getChildAsins().get(0);
                    if(childAsin.getPrice() != 0.00) {
                        price = String.format(getString(R.string.money_format), childAsin.getPrice());
                    } else {
                        price = String.format(getString(R.string.money_format), childAsin.getOriginalPrice());
                    }

                    originalPrice = String.format(getString(R.string.money_format), childAsin.getOriginalPrice());
                }

                SearchResultItem item = new SearchResultItem()
                        .setAsin(mAsinProduct.getAsin())
                        .setBrandName(mAsinProduct.getBrandName())
                        .setImageUrl(mAsinProduct.getDefaultImageUrl())
                        .setProductName(mAsinProduct.getProductName())
                        .setPrice(price)
                        .setOriginalPrice(originalPrice)
                        .setProductRating(rating);

                getContentResolver().insert(DataContract.SearchResultEntry.CONTENT_URI, SearchResultItem.toContentValues(item));
            } else {
                getContentResolver().delete(DataContract.SearchResultEntry.CONTENT_URI, null, new String[] {mAsinProduct.getAsin()});
                mIsFavorite = false;
            }
        }
    }

    void changeFabColor() {
        fabFavorite.setColorFilter(getResources().getColor(mIsFavorite ? R.color.primary : R.color.grey_200));
    }

    @Override
    public void onDataReady(AsinProduct data) {
        mAsinProduct = data;

        Cursor cursor = getContentResolver().query(DataContract.SearchResultEntry.buildUriwithAsin(mAsinProduct.getAsin()), null, null, null, null);

        if(cursor != null) {
            if(cursor.getCount() != 0) {
                mIsFavorite = true;
                changeFabColor();
            }
            cursor.close();
        }

        mCollapsingToolbarLayout.setTitle(mAsinProduct.getBrandName());
        String description = MarshmallowUtils.appendZapposBaseUrl(mAsinProduct.getDescription());
        mDescription.setHtmlFromString(description, new HtmlTextView.RemoteImageGetter());

        String[] pn = mAsinProduct.getProductName().split(" - ");
        String productName = mAsinProduct.getProductName();

        if (data.getChildAsins().size() > 0) {

            ChildAsin firstChild = data.getChildAsins().get(0);
            float price = firstChild.getOriginalPrice();

            if (firstChild.getPrice() != 0f) {
                price = Math.min(price, firstChild.getPrice());
            }

            mPrice.setText(String.format(getString(R.string.money_format), price));

            if (mItem == null) {
                loadItemImage(firstChild.getImageUrl());
            }
        }

        if (pn.length > 0) {
            productName = pn[0];
        }

        mProductName.setText(productName);
//        if (mItemBackground != null) {
//            Picasso.with(this).
//                    load(mAsinProduct.getDefaultImageUrl())
//                    .into(mItemBackground);
//        }

        if (mPager != null) {
            int counter = 0;
            SparseArray<String> imageUrls = new SparseArray<>();
            imageUrls.put(counter, mAsinProduct.getDefaultImageUrl());
            for (ChildAsin childAsin : mAsinProduct.getChildAsins()) {
                counter++;
                imageUrls.put(counter, childAsin.getImageUrl());
            }

            ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(this, imageUrls);
            mPager.setAdapter(imagePagerAdapter);
        }

    }

    @Override
    public void onDataError(Throwable e) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ASIN_DATA, Parcels.wrap(mAsinProduct));
        if (mItem != null) {
            outState.putParcelable(ITEM_DATA, Parcels.wrap(mItem));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(SHARE, SHARE, 0, R.string.share);
        item.setIcon(R.drawable.ic_social_share);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (initialY <= 0) {
            initialY = mItemImage.getY();
        }
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        float scale = Math.abs(-1f + percentage);
        mItemImage.setY(initialY * scale);
        mItemImage.setScaleX(scale);
        mItemImage.setScaleY(scale);

        if (percentage > 0.8f) {
            MarshmallowUtils.colorizeToolbar(mToolbar, R.color.grey_200);
        } else {
            MarshmallowUtils.colorizeToolbar(mToolbar, R.color.grey_800);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == SHARE) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.marshmallow_url) + mAsinProduct.getAsin());
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_the_love)));
        }

        return super.onOptionsItemSelected(item);
    }
}
