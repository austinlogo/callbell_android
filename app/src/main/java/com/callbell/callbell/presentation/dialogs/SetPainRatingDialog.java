package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.callbell.callbell.R;
import com.callbell.callbell.service.tasks.PainRatingAsyncTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/25/15.
 */
public class SetPainRatingDialog extends DialogFragment {

    private static final String TAG = SetPainRatingDialog.class.getSimpleName();

    @InjectView(R.id.dialog_set_pain_interval)
    EditText intervalValue;

    @InjectView(R.id.dialog_set_pain_submit)
    Button submit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_set_pain_check);

        ButterKnife.inject(this, dialog);

        submit.setOnClickListener(new SubmitListener());

        return dialog;
    }

    public class SubmitListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Log.d(TAG, "Submitted new Pain Rating Task");

            PainRatingAsyncTask mTask = new PainRatingAsyncTask(getActivity());
            mTask.execute(Integer.valueOf(intervalValue.getText().toString()));

            dismiss();
        }
    }
}

