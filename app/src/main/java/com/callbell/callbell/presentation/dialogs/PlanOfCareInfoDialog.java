package com.callbell.callbell.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.callbell.callbell.R;

/**
 * Created by austin on 10/15/15.
 */
public class PlanOfCareInfoDialog extends DialogFragment {

    public static final String TITLE_KEY = "DIALOG_TITLE";
    public static final String BODY_KEY = "DIALOG_BODY";

    private String mTitle;
    private String mBody;

    public static PlanOfCareInfoDialog newInstance(String tit, String bod) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, tit);
        bundle.putString(BODY_KEY, bod);

        PlanOfCareInfoDialog dialog = new PlanOfCareInfoDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = getArguments().getString(TITLE_KEY);
        mBody = getArguments().getString(BODY_KEY);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setMessage(mBody)
                .setPositiveButton(R.string.acknowledge, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }
}
