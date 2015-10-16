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

    public void resetList(String key) {
        super.clear();
        super.addAll((Collection<? extends T>) new ArrayList<>(POCValues.pocMap.get(key)));
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position);
    }
}
