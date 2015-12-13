package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.callbell.callbell.presentation.view.TernaryListItem;

import java.util.List;

/**
 * Created by austin on 12/12/15.
 */
public class PlanOfCareListViewAdapter extends ArrayAdapter<String> {

    private int mPendingItemsCount = 0;

    public PlanOfCareListViewAdapter(Context context, int resource, List<String> objects, int pendingItemCount) {
        super(context, resource, objects);

        mPendingItemsCount = pendingItemCount;
    }

    public void setPendingItemCount (int pic) {
        mPendingItemsCount = pic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TernaryListItem item = (TernaryListItem) convertView;

        if (item == null) {
            item = new TernaryListItem(getContext());
        }

        item.setText(getItem(position));

        TernaryListItem.TernaryItemState tis = position < mPendingItemsCount
                ? TernaryListItem.TernaryItemState.PENDING
                : TernaryListItem.TernaryItemState.DONE;

        item.setState(tis);

        return item;
    }

}
