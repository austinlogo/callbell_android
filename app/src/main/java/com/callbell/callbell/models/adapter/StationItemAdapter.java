package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.callbell.callbell.R;
import com.callbell.callbell.models.State;

import java.util.List;

/**
 * Created by austin on 10/12/15.
 */
public class StationItemAdapter extends BaseAdapter {


    private static final String TAG = StationItemAdapter.class.getSimpleName();
    private Context context;
    private List<State> stateList;
    private static LayoutInflater mInflater = null;

    public StationItemAdapter(Context cxt, List<State> sl) {

        context = cxt;
        stateList = sl;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return stateList.size();
    }

    @Override
    public Object getItem(int position) {
        return stateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tablet_state, null);
        }

        TextView locationField = (TextView) convertView.findViewById(R.id.station_item_bed_location);
        TextView physicianField = (TextView) convertView.findViewById(R.id.station_item_physician);
        TextView nurseField = (TextView) convertView.findViewById(R.id.station_item_nurse);
        TextView chiefComplaintField = (TextView) convertView.findViewById(R.id.station_item_chief_complaint);
        TextView painRatingField = (TextView) convertView.findViewById(R.id.station_item_pain_rating);

        locationField.setText(stateList.get(position).getLocation());
        physicianField.setText(stateList.get(position).getPhysician());
        nurseField.setText(stateList.get(position).getNurse());
        chiefComplaintField.setText(stateList.get(position).getChiefComplaint());
        painRatingField.setText(String.valueOf(stateList.get(position).getPainRating()));

        return convertView;
    }

    public void updateItem(State st) {
        Log.d(TAG, "UPDATING ITEM");
        for (int index = 0; index < stateList.size(); index++) {
            if (st.equals(stateList.get(index))) {
                Log.d(TAG, "WE have a winner");
                stateList.set(index, new State(st));
                notifyDataSetChanged();
                return;
            }
        }

        stateList.add(new State(st));
        notifyDataSetChanged();
        return;
    }
}
