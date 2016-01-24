package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.models.Station.PriorityList;
import com.callbell.callbell.models.Station.StateItemWrapper;
import com.callbell.callbell.util.PrefManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 10/12/15.
 */
public class StationItemAdapter extends BaseAdapter {


    private static final String TAG = StationItemAdapter.class.getSimpleName();
    private Context context;
    private PriorityList<StateItemWrapper> stateList;
//    private List<MessageReason> stateListCallSettings;
    private static LayoutInflater mInflater = null;
    private MessageRouting mMessageRouting;

    public StationItemAdapter(Context cxt, List<State> sl, MessageRouting routing) {

        context = cxt;
        stateList = new PriorityList<>();
//        stateList = sl;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        stateListCallSettings = new ArrayList<>();
        mMessageRouting = routing;

        for (State st: sl) {
            stateList.add(new StateItemWrapper(st, MessageReason.QUIET));
//            stateListCallSettings.add(MessageReason.QUIET);
        }
    }

    @Override
    public int getCount() {
        return stateList.size();
    }

    @Override
    public State getItem(int position) {
        return stateList.get(position).getState();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tablet_state, null);
        }

        TextView locationField = (TextView) convertView.findViewById(R.id.station_item_bed_location);
        TextView physicianField = (TextView) convertView.findViewById(R.id.station_item_physician);
        TextView nurseField = (TextView) convertView.findViewById(R.id.station_item_nurse);
        TextView chiefComplaintField = (TextView) convertView.findViewById(R.id.station_item_chief_complaint);
        TextView painRatingField = (TextView) convertView.findViewById(R.id.station_item_pain_rating);
        ImageButton painRatingButton = (ImageButton) convertView.findViewById(R.id.station_item_image_button);
        View connectionIndicator = convertView.findViewById(R.id.station_item_state_indicator);

        locationField.setText(stateList.get(position).getState().getLocation());
        physicianField.setText(stateList.get(position).getState().getPhysician());
        nurseField.setText(stateList.get(position).getState().getNurse());
        chiefComplaintField.setText(POCValues.chiefComplaintMap.getValue(stateList.get(position).getState().getChiefComplaint()));
        painRatingField.setText(String.valueOf(stateList.get(position).getState().getPainRating()));

        connectionIndicator.setBackgroundColor(stateList.get(position).getState().isConnected()
                ? context.getResources().getColor(R.color.colorPrimary)
                : context.getResources().getColor(R.color.red));

        if (stateList.get(position).getMessageReason() == MessageReason.PAIN) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.pain_color));
        } else if (stateList.get(position).getMessageReason() == MessageReason.RESTROOM) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.restroom_color));
        } else if (stateList.get(position).getMessageReason() == MessageReason.BLANKET) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.blanket_color));
        } else if (stateList.get(position).getMessageReason() == MessageReason.FOOD) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.food_color));
        } else if (stateList.get(position).getMessageReason() == MessageReason.HELP) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.help_color));
        } else {
            convertView.setBackgroundColor(0);
        }

        painRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageRouting.sendMessage(stateList.get(position).getState().getLocation(), PrefManager.CATEGORY_RATE_PAIN, MessageReason.RATE_PAIN);
            }
        });

        painRatingButton.setFocusable(false);

        return convertView;
    }

    public void updateItem(State st) {
        Log.d(TAG, "UPDATING ITEM");

        int index = getPosition(st);
        if (index < 0) {
            stateList.add(new StateItemWrapper(new State(st), MessageReason.QUIET));
        } else {
            MessageReason reason = stateList.get(index).getMessageReason();
            stateList.set(index, new StateItemWrapper(st, reason));
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
            stateList.set(index, new StateItemWrapper(st, reason));
            reorderList(index, reason);
        }

        notifyDataSetChanged();
        return;
    }

    public void updateItem(int position, MessageReason reason) {

        stateList.get(position).setMessageReason(reason);
        reorderList(position, reason);

        notifyDataSetChanged();
    }

    private void reorderList(int position, MessageReason reason) {
        if (reason.equals(MessageReason.QUIET)) {
            stateList.setPriorityState(position, PriorityList.PriorityState.LOW);
        } else {
            stateList.setPriorityState(position, PriorityList.PriorityState.HIGH);
        }
    }

    private int getPosition(State st) {
        for (int index = 0; index < stateList.size(); index++) {
            if (st.equals(stateList.get(index).getState())) {
                return index;
            }
        }
        return -1;
    }

    public MessageReason getReason(int position) {
        return stateList.get(position).getMessageReason();
    }

    public void updateConnectedStatuses(List<String> connectedTablets) {
        for (StateItemWrapper item : stateList) {
            item.getState().setConnected(connectedTablets.contains(item.getState().getTabletName()));
        }

        notifyDataSetChanged();
    }

    public void updateConnectedStatus(String tabletName, boolean connectedStatus) {
        for (StateItemWrapper item : stateList) {
            if (item.getState().getTabletName().equals(tabletName)) {
                item.getState().setConnected(connectedStatus);

                notifyDataSetChanged();
                return;
            }
        }
    }

    public boolean isPriorityListEmpty() {
        return stateList.getPriorityItems().size() == 0;
    }
}
