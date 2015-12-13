package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.callbell.callbell.data.POCValues;
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

    private Set<Integer> mPendingItems, mDoneItems;
    private boolean isSuperUser;

    public PlanOfCareCheckBoxAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);

        isSuperUser = false;
        mPendingItems = new HashSet<>();
        mDoneItems = new HashSet<>();
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int index = 0; index < getCount(); index++) {
            list.add(getItem(index));
        }

        return list;
    }

    @Override
    public int getCount() {
        if (isSuperUser) {
            return super.getCount() - mPendingItems.size();
        } else {
            return super.getCount();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TernaryListItem view =  (TernaryListItem) convertView;

        while (!isSuperUser && mPendingItems.contains(position++));

        if (view == null) {
            view = new TernaryListItem(getContext());
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

    public void resetList(String key) {
        super.clear();
        List<String> newList = new ArrayList<>(POCValues.pocMap.get(key));
//        Collections.sort(newList);
        super.addAll(newList);
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);

    }

    public void setPendingItems(int position, boolean pending) {
        if (pending) {
            mPendingItems.add(position);
        } else {
            mPendingItems.remove(position);
        }
    }

    public void setDoneItems(int position, boolean done) {
        if (done) {
            mDoneItems.add(position);
        } else {
            mDoneItems.remove(position);
        }
    }

    public void setSuperUser(boolean isu) {
        isSuperUser = isu;

        notifyDataSetChanged();
    }

//    public List<Integer> getPendingItems() {
//        List<Integer> pendingItems = new ArrayList<>();
//        for (int index : mPendingItems.keySet()) {
//            if (mPendingItems.get(index)) {
//                pendingItems.add(index);
//            }
//        }
//
//        return pendingItems;
//    }
//
//    public List<Integer> getDoneItems() {
//        List<Integer> doneItems = new ArrayList<>();
//        for (int index : mDoneItems.keySet()) {
//            if (mDoneItems.get(index)) {
//                doneItems.add(index);
//            }
//        }
//
//        return doneItems;
//    }

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
}
