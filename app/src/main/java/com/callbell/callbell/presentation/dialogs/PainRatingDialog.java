package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/25/15.
 */
public class PainRatingDialog
        extends DialogFragment {

    @Inject
    PrefManager prefs;

    @InjectView(R.id.dialog_pain_rating_value)
    TextView painRatingValueText;

    @InjectView(R.id.dialog_pain_seekbar)
    SeekBar mPainSeekbar;

    @InjectView(R.id.dialog_pain_submit)
    Button mPainSubmitButton;

    private DialogDismissedCallback mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_pain_rating);
        dialog.setCanceledOnTouchOutside(false);

        ButterKnife.inject(this, dialog);
        ((CallBellApplication) getActivity().getApplication()).inject(this);

        mPainSeekbar.setOnSeekBarChangeListener(new SeekbarTracker());
        mPainSubmitButton.setOnClickListener(new SubmitClicked());

        int progress = prefs.getCurrentState().getPainRating();
        painRatingValueText.setText(String.valueOf(progress));
        mPainSeekbar.setProgress(progress);

        return dialog;
    }

    public void setCallback(DialogDismissedCallback callback) {
        mCallback = callback;
    }



    public class SubmitClicked implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            prefs.setPainRating(mPainSeekbar.getProgress());

            if (mCallback != null) {
                mCallback.onDialogDismissed();
            }
            dismiss();
        }
    }

    public class SeekbarTracker implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            painRatingValueText.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // NOOP
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // NOOP
        }
    }

    public interface DialogDismissedCallback {
        void onDialogDismissed();
    }
}
