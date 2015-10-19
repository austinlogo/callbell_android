package com.callbell.callbell.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.callbell.callbell.R;
import com.callbell.callbell.util.PrefManager;

/**
 * Created by austin on 10/9/15.
 */
public class CallBellDialog extends DialogFragment {
    public static String TAG = CallBellDialog.class.getSimpleName();

    public static final String MESSAGE_KEY = "message";
    public static final String FROM_KEY = "bed_id";

    private String mTitle;
    private String mMessage;

    public static CallBellDialog newInstance(Bundle bundle) {
        CallBellDialog dialog = new CallBellDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Bundle: " + getArguments().toString());
        mTitle = getText(R.string.bed) + ": " + getArguments().getString(FROM_KEY);//getArguments().getString(PrefManager)\
        mMessage = getArguments().getString(MESSAGE_KEY) + " has been pressed";
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(R.string.acknowledge, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Acknowleged");
                    }
                });

        return builder.create();
    }
}