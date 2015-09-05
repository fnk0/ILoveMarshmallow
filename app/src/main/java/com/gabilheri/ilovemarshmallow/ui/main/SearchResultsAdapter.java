package com.gabilheri.ilovemarshmallow.ui.main;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gabilheri.ilovemarshmallow.R;
import com.gabilheri.ilovemarshmallow.base.ViewItemCallback;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder> {

    private ViewItemCallback callback;
    private List<SearchResultItem> items;

    public SearchResultsAdapter(List<SearchResultItem> items, ViewItemCallback callback) {
        this.items = items;
        this.callback = callback;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(view, callback);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        SearchResultItem item = items.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .into(holder.itemImage);

        holder.itemTitle.setText(item.getBrandName());

        float rating = item.getProductRating();

        holder.ratingBar.setProgress((int)rating);
        holder.itemPrice.setText(item.getPrice());
        holder.itemView.setTag(R.id.asin, item);
    }

    public List<SearchResultItem> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void refreshResults(List<SearchResultItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addAll(List<SearchResultItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.item_image)
        ImageView itemImage;

        @Bind(R.id.item_title)
        AppCompatTextView itemTitle;
        
        @Bind(R.id.item_price)
        AppCompatTextView itemPrice;

        @Bind(R.id.item_rating_bar)
        AppCompatRatingBar ratingBar;

        ViewItemCallback callback;

        public SearchResultViewHolder(View itemView, ViewItemCallback callback) {
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
}
