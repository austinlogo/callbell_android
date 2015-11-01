package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.callbell.callbell.R;
import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/9/15.
 */
public class EnableSuperUserDialog extends DialogFragment {
    public static String TAG = EnableSuperUserDialog.class.getSimpleName();

    @Inject
    PrefManager prefs;

    @InjectView(R.id.dialog_password)
    EditText passwordEditText;

    @InjectView(R.id.dialog_submit)
    Button submitButton;

    public static EnableSuperUserDialog newInstance(Bundle bundle) {
        EnableSuperUserDialog dialog = new EnableSuperUserDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ((CallBellApplication) getActivity().getApplication()).inject(this);

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_superuser);
        ButterKnife.inject(this, dialog);

        passwordEditText.setOnEditorActionListener(new EditTextListener());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();

            }
        });

        passwordEditText.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        return dialog;
    }

    private void checkPassword() {
        boolean enableSUMode = PrefManager.DEFAULT_SU_PASSWORD.equals(passwordEditText.getText().toString());

        if (enableSUMode) {
            prefs.setSuperUser(true);
            Intent i = new Intent(PrefManager.EVENT_SU_MODE_CHANGE);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(i);
            dismiss();
        } else {
            Toast.makeText(getActivity(), R.string.incorrect_password, Toast.LENGTH_SHORT).show();
        }
    }



    public class EditTextListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkPassword();
                return true;
            }

            return false;
        }
    }
}