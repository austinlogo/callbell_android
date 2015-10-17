package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.callbell.callbell.data.POCValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by austin on 10/15/15.
 */
public class PlanOfCareCheckBoxAdapter extends ArrayAdapter<String>{

    public PlanOfCareCheckBoxAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int index = 0; index < getCount(); index++) {
            list.add(getItem(index));
        }



        return list;
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
}
