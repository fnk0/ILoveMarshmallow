package com.gabilheri.ilovemarshmallow.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gabilheri.ilovemarshmallow.R;

import butterknife.Bind;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public abstract class BaseRecyclerListFragment extends BaseFragment {

    @Bind(R.id.recyclerview)
    protected RecyclerView recyclerView;

    protected void initGridCardsList(RecyclerView.Adapter adapter) {
        int numCols = getResources().getInteger(R.integer.num_cols);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), numCols));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.list_fragment;
    }
}
