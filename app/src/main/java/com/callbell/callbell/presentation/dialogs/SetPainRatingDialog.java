package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.callbell.callbell.R;

/**
 * Created by austin on 10/25/15.
 */
public class SetPainRatingDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_dialog_set_pain_check);
        return dialog;
    }
}

