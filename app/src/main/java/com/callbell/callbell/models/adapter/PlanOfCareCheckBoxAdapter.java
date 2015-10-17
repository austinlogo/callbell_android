package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.callbell.callbell.data.POCValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by austin on 10/15/15.
 */
public class PlanOfCareCheckBoxAdapter<T> extends ArrayAdapter<T>{

    public PlanOfCareCheckBoxAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public List<T> getList() {
        List<T> list = new ArrayList<>();
        for (int index = 0; index < getCount(); index++) {
            list.add(getItem(index));
        }



        return list;
    }

    public boolean contains(T item) {
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
        super.addAll((Collection<? extends T>) new ArrayList<>(POCValues.pocMap.get(key)));
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position);
    }
}
