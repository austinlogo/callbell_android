package com.callbell.callbell.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.callbell.callbell.R;
import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

/**
 * Created by austin on 10/9/15.
 */
public class EnableSuperUserDialog extends DialogFragment {
    public static String TAG = EnableSuperUserDialog.class.getSimpleName();

    @Inject
    PrefManager prefs;

    private EditText password;

    public static EnableSuperUserDialog newInstance(Bundle bundle) {
        EnableSuperUserDialog dialog = new EnableSuperUserDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        password = new EditText(getActivity());
        password.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ((CallBellApplication) getActivity().getApplication()).inject(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.su_enter_alarm_title)
                .setPositiveButton(R.string.acknowledge, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPassword();
                    }
                });

        builder.setView(password);

        return builder.create();
    }

    private void checkPassword() {
        boolean enableSUMode = PrefManager.DEFAULT_SU_PASSWORD.equals(password.getText().toString());

        if (enableSUMode) {
            prefs.setSuperUserStatus(true);
            Intent i = new Intent(PrefManager.EVENT_SU_MODE_CHANGE);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(i);
        } else {
            Toast.makeText(getActivity(), R.string.incorrect_password, Toast.LENGTH_SHORT).show();
        }
    }
}