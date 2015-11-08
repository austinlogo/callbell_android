package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.callbell.callbell.R;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 10/12/15.
 */
public class StationItemAdapter extends BaseAdapter {


    private static final String TAG = StationItemAdapter.class.getSimpleName();
    private Context context;
    private List<State> stateList;
    private List<MessageReason> stateListCallSettings;
    private static LayoutInflater mInflater = null;

    public StationItemAdapter(Context cxt, List<State> sl) {

        context = cxt;
        stateList = sl;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        stateListCallSettings = new ArrayList<>();

        for (int i = 0; i < sl.size();i++) {
            stateListCallSettings.add(MessageReason.QUIET);
        }
    }

    @Override
    public int getCount() {
        return stateList.size();
    }

    @Override
    public State getItem(int position) {
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
        View connectionIndicator = convertView.findViewById(R.id.station_item_state_indicator);

        locationField.setText(stateList.get(position).getLocation());
        physicianField.setText(stateList.get(position).getPhysician());
        nurseField.setText(stateList.get(position).getNurse());
        chiefComplaintField.setText(stateList.get(position).getChiefComplaint());
        painRatingField.setText(String.valueOf(stateList.get(position).getPainRating()));

        connectionIndicator.setBackgroundColor(stateList.get(position).isConnected()
                ? context.getResources().getColor(R.color.colorPrimary)
                : context.getResources().getColor(R.color.red));

        if (stateListCallSettings.get(position) == MessageReason.PAIN) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.pain_color));
        } else if (stateListCallSettings.get(position) == MessageReason.RESTROOM) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.restroom_color));
        } else if (stateListCallSettings.get(position) == MessageReason.BLANKET) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.blanket_color));
        } else if (stateListCallSettings.get(position) == MessageReason.FOOD) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.food_color));
        } else if (stateListCallSettings.get(position) == MessageReason.HELP) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.help_color));
        } else {
            convertView.setBackgroundColor(0);
        }

        return convertView;
    }

    public void updateItem(State st) {
        Log.d(TAG, "UPDATING ITEM");

        int index = getPosition(st);
        if (index < 0) {
            stateList.add(new State(st));
            stateListCallSettings.add(MessageReason.QUIET);
        } else {
            stateList.set(index, new State(st));
        }

        notifyDataSetChanged();
        return;
    }

    public void updateItem(State st, MessageReason reason) {
        int index;
        if ((index = getPosition(st)) < 0) {
            Log.d(TAG, "Item is not in the list");
            // TODO: implement
        } else {
            stateList.set(index, new State(st));
            stateListCallSettings.set(index, reason);
        }

        notifyDataSetChanged();
        return;
    }

    public void updateItem(int position, MessageReason reason) {
        stateListCallSettings.set(position, reason);
        notifyDataSetChanged();
    }

    private int getPosition(State st) {
        for (int index = 0; index < stateList.size(); index++) {
            if (st.equals(stateList.get(index))) {
                return index;
            }
        }
        return -1;
    }

    public MessageReason getReason(int position) {
        return stateListCallSettings.get(position);
    }

    public void updateConnectedStatuses(List<String> connectedTablets) {
        for (State state : stateList) {
            state.setConnected(connectedTablets.contains(state.getTabletName()));
        }

        notifyDataSetChanged();
    }
}
