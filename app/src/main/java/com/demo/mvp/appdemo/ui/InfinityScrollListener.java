package com.demo.mvp.appdemo.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class  InfinityScrollListener extends RecyclerView.OnScrollListener {
    private static final int VISIBLE_THRESHOLD = 5;
    private final GridLayoutManager mLayoutManager;
    private final DataLoading mDataLoading;

    public InfinityScrollListener(DataLoading dataLoading, GridLayoutManager linearLayoutManager) {
        mDataLoading = checkNotNull(dataLoading);
        mLayoutManager = checkNotNull(linearLayoutManager);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy < 0 || mDataLoading.isLoadingData() || !mDataLoading.isThereMoreData()) return;

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = mLayoutManager.getItemCount();
        final int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
