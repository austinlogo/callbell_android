package com.callbell.callbell.presentation.bed.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.callbell.callbell.R;
import com.callbell.callbell.models.adapter.PlanOfCareCheckBoxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 11/5/15.
 */
public class DisplayItemList extends LinearLayout {

    private static final String TAG = DisplayItemList.class.getSimpleName();

    @InjectView(R.id.layout_display_title)
    TextView mTitle;

    @InjectView(R.id.layout_display_item_list_admin)
    ListView adminList;

    @InjectView(R.id.layout_display_item_patient)
    ListView patientList;

    PlanOfCareCheckBoxAdapter adminAdapter;
    ArrayAdapter<String> patientAdapter;

    public DisplayItemList(Context context) {
        super(context);
    }

    public DisplayItemList(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public DisplayItemList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_toggle_list, this);
        ButterKnife.inject(this);

        adminList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public void setTitle(int titleId) {
        mTitle.setText(titleId);
    }

    public void setDisplayMode(boolean isSuperUser) {
//        boolean isSuperUser = mode == DisplayMode.ADMIN;

        adminList.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        patientList.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);
    }

    public void setAdminAdapter(PlanOfCareCheckBoxAdapter adapter) {
        adminAdapter = adapter;
        adminList.setAdapter(adminAdapter);
    }

    public void setPatientListAdapter(ArrayAdapter<String> adapter) {
        patientAdapter = adapter;
        patientList.setAdapter(patientAdapter);
    }

    public ListView getAdminListView() {
        return adminList;
    }

    public ListView getPatientListView() {
        return patientList;
    }

    public List<String> getActionList() {
        return adminAdapter.getList();
    }
    public List<Integer> updatePatientList() {
        List<Integer> checkedIndexes = getCheckedIndexes();
        List<String> shownItems = filterSelectedChoices(checkedIndexes, adminAdapter);

        if (patientAdapter == null) {
            setPatientListAdapter (new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, shownItems));
        } else {
            patientAdapter.clear();
            patientAdapter.addAll(shownItems);
            patientList.setAdapter(patientAdapter);
        }
        return checkedIndexes;
    }

    public void updateAdminList(String key) {
        adminAdapter.resetList(key);
    }

    public int getShownItemCount() {
        return adminList.getCheckedItemCount();
    }

    public void setCheckedItems(List<Integer> shownItems) {
        for (int i = 0; i < adminList.getCount(); i++) {
            adminList.setItemChecked(i, shownItems.contains(i));
        }

        updatePatientList();
    }

    private List<Integer> getCheckedIndexes() {
        SparseBooleanArray checked = adminList.getCheckedItemPositions();

        List<Integer> checkedArray = new ArrayList<>();
        for (int index = 0; index < adminList.getAdapter().getCount(); index++) {
            if (checked.get(index)) {
                checkedArray.add(index);
            }
        }

        return checkedArray;
    }

    private List<String> filterSelectedChoices(List<Integer> shownItems, PlanOfCareCheckBoxAdapter adapter) {
        ArrayList<String> selectedActions = new ArrayList<>(shownItems.size());

        for (int i : shownItems) {
            //check for if there were any temp actions stored when we closed out.
            if (i >= adapter.getCount()) {
                continue;
            }
            selectedActions.add(adapter.getItem(i));
        }

        return selectedActions == null ? new ArrayList<String>() : selectedActions;
    }

    // Adapter wrappers

    public boolean contains(String item) {
        return adminAdapter.contains(item);
    }

    public int getPosition(String s) {
        return adminAdapter.getPosition(s);
    }

    public void add(String s) {
        adminAdapter.add(s);
    }

    public int getCount() {
        return adminAdapter.getCount();
    }

    public void clear() {
        setCheckedItems(new ArrayList<Integer>());
        patientAdapter.clear();
    }

    public void resetList(String key) {
        adminAdapter.resetList(key);
    }
}
