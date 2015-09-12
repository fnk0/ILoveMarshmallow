package com.gabilheri.ilovemarshmallow.ui.main;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabilheri.ilovemarshmallow.R;
import com.gabilheri.ilovemarshmallow.base.ItemCallback;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private ItemCallback<View> mCallback;
    private List<SearchResultItem> mItems;


    public SearchResultsAdapter(@Nullable List<SearchResultItem> items, ItemCallback<View> callback) {
        if (items == null) {
            mItems = new ArrayList<>();
        } else  {
            this.mItems = items;
        }
        this.mCallback = callback;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        SearchResultItem item = mItems.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .error(R.drawable.no_image)
                .into(holder.itemImage);

        holder.itemTitle.setText(item.getBrandName());

        float rating = item.getProductRating();

        holder.ratingBar.setProgress((int)rating);
        holder.itemPrice.setText(item.getPrice());
        holder.itemView.setTag(R.id.asin, item);
    }

    public List<SearchResultItem> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void swapResults(List<SearchResultItem> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    public void addAll(List<SearchResultItem> items) {
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void reset() {
        this.mItems = new ArrayList<>();
        notifyDataSetChanged();
    }
}
