package com.callbell.callbell.util.shell;

import android.app.Activity;

import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.presentation.bed.CallBellsFragment;
import com.callbell.callbell.presentation.bed.PlanOfCareFragment;


public class BlankPlanOfCareFragmentActivity extends Activity implements PlanOfCareFragment.PlanOfCareInteraction {

    @Override
    public void showInfoDialog(String tit, String expandedName, String bod) {

    }

    @Override
    public void savePOCState(State st) {

    }
}
