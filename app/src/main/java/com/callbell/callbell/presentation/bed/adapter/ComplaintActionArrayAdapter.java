package com.callbell.callbell.presentation.bed.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by austin on 10/11/15.
 */
public class ComplaintActionArrayAdapter<T> extends ArrayAdapter<T> {

    private boolean[] isShown;

    public ComplaintActionArrayAdapter(Context context, int resource, T[] objects) {
        super(context, resource, objects);

        isShown = new boolean[objects.length];
        for (int i = 0; i < isShown.length; i++) {
            isShown[i] = false;
        }
    }

    public void switchVisibility(int position) {
        isShown[position] = !isShown[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (!isShown[position]) {
            convertView.setVisibility(View.GONE);
        }

        return super.getView(position, convertView, parent);
    }
}
