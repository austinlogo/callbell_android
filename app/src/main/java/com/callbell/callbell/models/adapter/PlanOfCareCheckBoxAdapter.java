package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.State.BiMap;
import com.callbell.callbell.presentation.view.TernaryListItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by austin on 10/15/15.
 */
public class PlanOfCareCheckBoxAdapter extends ArrayAdapter<String>{
    private BiMap<Integer, String> map;
//    private List<String> mObjects;
    private Set<Integer> mPendingItems, mDoneItems;
    private boolean isSuperUser;

    public PlanOfCareCheckBoxAdapter(Context context, int resource, List objects, BiMap<Integer, String> mapping) {
        super(context, resource, objects);

//        mObjects = objects;
        isSuperUser = false;
        mPendingItems = new HashSet<>();
        mDoneItems = new HashSet<>();
        map = mapping;
    }

    public List<Integer> getList() {
        List<Integer> list = new ArrayList<>();
        for (int index = 0; index < getCount(); index++) {
            list.add(map.getKey(getItem(index)));
        }

        return list;
    }

    @Override
    public void add(String object) {
        super.add(object);
//        mObjects.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TernaryListItem view =  (TernaryListItem) convertView;

        if (view == null) {
            view = new TernaryListItem(getContext());
        }

        if (mDoneItems.contains(position)) {
            view.setState(TernaryListItem.TernaryItemState.DONE);
        } else if (mPendingItems.contains(position)) {
            view.setState(TernaryListItem.TernaryItemState.PENDING);
        } else {
            view.setState(TernaryListItem.TernaryItemState.NOT_SELECTED);
        }

        view.setText(getItem(position));

        return view;
    }

    public boolean contains(String item) {
        if (item == null) {
            return false;
        }
        for (int index = 0; index < getCount(); index++) {
            if (item.equals(getItem(index))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }

    public void resetList(String key) {
        super.clear();

        List<String> newList = new ArrayList<>(POCValues.pocMap.get(key));
        super.addAll(newList);
    }

    public void resetSelectedItems() {
        mPendingItems = new HashSet<>();
        mDoneItems = new HashSet<>();

        notifyDataSetChanged();

    }

    public void setPendingItems(List<Integer> pi) {
        mPendingItems = new HashSet<>(pi);
    }

    public void setDoneItems(List<Integer> di) {
        mDoneItems = new HashSet<>(di);
    }

    public void setPendingItem(int position, boolean pending) {
        if (pending) {
            mPendingItems.add(position);
        } else {
            mPendingItems.remove(position);
        }

        notifyDataSetChanged();
    }

    public void setDoneItem(int position, boolean done) {
        if (done) {
            mDoneItems.add(position);
        } else {
            mDoneItems.remove(position);
        }

        notifyDataSetChanged();
    }

    public void setSuperUser(boolean isu) {
        isSuperUser = isu;

        notifyDataSetChanged();
    }

    public Set<Integer> getPendingItems() {
        return mPendingItems;
    }

    public Set<Integer> getDoneItems() {
        return mDoneItems;
    }

    public static TernaryListItem getViewByPosition(int pos, ListView listView) {
        try {
            final int firstListItemPosition = listView
                    .getFirstVisiblePosition();
            final int lastListItemPosition = firstListItemPosition
                    + listView.getChildCount() - 1;

            if (pos < firstListItemPosition || pos > lastListItemPosition) {
                //This may occure using Android Monkey, else will work otherwise
                return (TernaryListItem) listView.getAdapter().getView(pos, null, listView);
            } else {
                final int childIndex = pos - firstListItemPosition;
                return (TernaryListItem) listView.getChildAt(childIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPendingItemsSize() {
        return mPendingItems.size();
    }
}
