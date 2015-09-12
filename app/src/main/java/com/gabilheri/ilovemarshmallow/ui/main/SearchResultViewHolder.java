package com.gabilheri.ilovemarshmallow.ui.main;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gabilheri.ilovemarshmallow.R;
import com.gabilheri.ilovemarshmallow.base.ItemCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/12/15.
 */
public class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @Bind(R.id.item_image)
    ImageView itemImage;

    @Bind(R.id.item_title)
    AppCompatTextView itemTitle;

    @Bind(R.id.item_price)
    AppCompatTextView itemPrice;

    @Bind(R.id.item_rating_bar)
    AppCompatRatingBar ratingBar;

    ItemCallback<View> callback;

    public SearchResultViewHolder(View itemView, ItemCallback<View> callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.callback = callback;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        callback.onItemClick(v);
    }
}
