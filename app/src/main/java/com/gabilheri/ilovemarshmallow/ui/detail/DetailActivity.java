package com.gabilheri.ilovemarshmallow.ui.detail;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
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
import com.gabilheri.ilovemarshmallow.data.endpoint_models.AsinProduct;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.ChildAsin;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;
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
    static final float PERCETANGE_CHANGE_TITLE_COLOR = 0.9f;

    @Bind(R.id.main_content)
    CoordinatorLayout mainLayout;

    @Bind(R.id.item_bg)
    ImageView mItemBackground;

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

            if(savedInstanceState != null) {
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

        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mItemBackground.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        mItemBackground.setLayoutParams(petDetailsLp);
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
    void favoriteItem(final View v) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.window_background)));
                        if (!mIsFavorite) {
                            mIsFavorite = true;
                            fabFavorite.setColorFilter(getResources().getColor(R.color.primary));
                        } else {
                            mIsFavorite = false;
                            fabFavorite.setColorFilter(getResources().getColor(R.color.grey_200));
                        }

                        mCircularReveal = ViewAnimationUtils.createCircularReveal(v, 0, 0, 0, finalRadius);
                        mCircularReveal.setDuration(1000).start();
                    }
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
    }

    @Override
    public void onDataReady(AsinProduct data) {
        mAsinProduct = data;

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

            mPrice.setText(String.format("$%1.2f", price));

            if (mItem == null) {
                loadItemImage(firstChild.getImageUrl());
            }
        }

        if(pn.length > 0) {
            productName = pn[0];
        }

        mProductName.setText(productName);
        Picasso.with(this).
                load(mAsinProduct.getDefaultImageUrl())
                .into(mItemBackground);
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

        MenuItem item = menu.add(SHARE, SHARE, 0, "Share");
        item.setIcon(R.drawable.ic_social_share);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if(initialY <= 0) {
            initialY = mItemImage.getY();
        }
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        float scale = Math.abs(-1f + percentage);
        mItemImage.setY(initialY * scale);
        mItemImage.setScaleX(scale);
        mItemImage.setScaleY(scale);

        if(percentage > 0.8f) {
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, "http://marshmallow.gabilheri.com/" + mAsinProduct.getAsin());
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Share the love!"));
        }

        return super.onOptionsItemSelected(item);
    }
}
