package com.callbell.callbell.util.shell;

import android.app.Activity;

import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.presentation.bed.CallBellsFragment;
import com.callbell.callbell.presentation.bed.StaffFragment;


public class BlankCallBellsFragmentActivity extends Activity implements CallBellsFragment.onCallBellFragmentInteractionListener {

    @Override
    public void onCallBellPressed(MessageReason reason) {

    }
}
