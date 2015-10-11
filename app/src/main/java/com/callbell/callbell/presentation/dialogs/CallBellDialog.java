package com.callbell.callbell.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.callbell.callbell.R;

/**
 * Created by austin on 10/9/15.
 */
public class CallBellDialog extends DialogFragment {
    public static String TAG = CallBellDialog.class.getSimpleName();

    public static CallBellDialog newInstance(Bundle bundle) {
        CallBellDialog dialog = new CallBellDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.call_bell_alarm_title)
                .setPositiveButton(R.string.acknowledge, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Acknowleged");
                    }
                })
                .setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Dismissed");
                    }
                });

        return builder.create();
    }
}