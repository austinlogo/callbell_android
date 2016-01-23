package com.callbell.callbell.presentation.bed.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.callbell.callbell.R;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.models.State.BiMap;
import com.callbell.callbell.models.adapter.PlanOfCareCheckBoxAdapter;
import com.callbell.callbell.models.adapter.PlanOfCareListViewAdapter;
import com.callbell.callbell.presentation.view.TernaryListItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Compound view of Admin and Patient Lists
 */
public class ToggleListView extends LinearLayout {
    private static final String TAG = ToggleListView.class.getSimpleName();

    @InjectView(R.id.layout_display_title)
    TextView mTitle;

    @InjectView(R.id.layout_display_item_list_admin)
    ListView adminList;

    @InjectView(R.id.layout_display_item_patient)
    ListView patientList;

    PlanOfCareCheckBoxAdapter adminAdapter;
    PlanOfCareListViewAdapter patientAdapter;

    public ToggleListView(Context context) {
        super(context);
    }

    public ToggleListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ToggleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_toggle_list, this);
        ButterKnife.inject(this);

        adminList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        adminList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TernaryListItem item = (TernaryListItem) view;
                item.moveToNextState();

                adminAdapter.setDoneItem(position, item.isDone());
                adminAdapter.setPendingItem(position, item.isPending());
            }
        });
    }

    public void setTitle(int titleId) {
        mTitle.setText(titleId);
    }

    public void setDisplayMode(boolean isSuperUser) {
//        boolean isSuperUser = mode == DisplayMode.ADMIN;

        adminAdapter.setSuperUser(isSuperUser);
        adminList.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);
        patientList.setVisibility(isSuperUser ? View.GONE : View.VISIBLE);
    }

    public void setAdminAdapter(PlanOfCareCheckBoxAdapter adapter) {
        adminAdapter = adapter;
        adminList.setAdapter(adminAdapter);

    }

    public void setPatientListAdapter(PlanOfCareListViewAdapter adapter) {
        patientAdapter = adapter;
        patientList.setAdapter(patientAdapter);
    }

    public ListView getAdminListView() {
        return adminList;
    }

    public ListView getPatientListView() {
        return patientList;
    }

    public List<Integer> getActionList() {
        return adminAdapter.getList();
    }

     public void updatePatientList() {

         List<String> shownItems = constructPatientList(adminAdapter.getPendingItems(), adminAdapter.getDoneItems());
         int pendingItemsCount = adminAdapter.getPendingItemsSize();

         if (patientAdapter == null) {
            setPatientListAdapter (new PlanOfCareListViewAdapter(getContext(), R.layout.item_ternary_checkbox, shownItems, pendingItemsCount));
         } else {
             patientAdapter.clear();
             patientAdapter.addAll(shownItems);
             patientAdapter.setPendingItemCount(pendingItemsCount);
             patientList.setAdapter(patientAdapter);
         }
         return;
    }

    private List<String> constructPatientList(Set<Integer> pendingItems, Set<Integer> doneItems) {

        List<String> result = filterSelectedChoices(pendingItems, adminAdapter);
        result.addAll (filterSelectedChoices(doneItems, adminAdapter));

        return result;
    }

    public int getShownItemCount() {
        return adminList.getCheckedItemCount();
    }

    public void setCheckedItems(List<Integer> pending, List<Integer> done) {
        adminAdapter.setPendingItems(pending);
        adminAdapter.setDoneItems(done);

        updatePatientList();
    }


    //TODO: reimplement this
    public List<Integer> getPendingIndexes() {
        List<Integer> list = new ArrayList<>();
        list.addAll(adminAdapter.getPendingItems());

        return list;
    }

    public List<Integer> getDoneIndexes() {
        List<Integer> list = new ArrayList<>();
        list.addAll(adminAdapter.getDoneItems());

        return list;
    }

    private List<String> filterSelectedChoices(Collection<Integer> shownItems, PlanOfCareCheckBoxAdapter adapter) {
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
        adminAdapter.resetSelectedItems();
        patientAdapter.clear();
    }

    public void resetList(String key) {
        adminAdapter.resetList(key);
    }

    public void setPendingItem(int index, boolean pending) {
        adminAdapter.setPendingItem(index, pending);
    }
}
