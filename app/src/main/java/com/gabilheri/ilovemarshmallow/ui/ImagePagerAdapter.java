package com.gabilheri.ilovemarshmallow.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gabilheri.ilovemarshmallow.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/9/15.
 */
public class ImagePagerAdapter extends PagerAdapter {

    Context mContext;
    SparseArray<String> imageUrls;
    LayoutInflater mInflater;

    public ImagePagerAdapter(Context mContext, SparseArray<String> imageUrls) {
        this.mContext = mContext;
        this.imageUrls = imageUrls;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setImageUrls(SparseArray<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = mInflater.inflate(R.layout.page_image, container, false);
        ImageView imageView = ButterKnife.findById(imageLayout, R.id.image);

        Picasso.with(mContext)
                .load(imageUrls.get(position))
                .transform(new RoundTransformation(10))
                .into(imageView);

        container.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((RelativeLayout) object));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
}
