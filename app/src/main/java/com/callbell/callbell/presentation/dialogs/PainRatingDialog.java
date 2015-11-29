package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.service.services.SocketService;
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

    @Inject
    MessageRouting messageRouting;

    @InjectView(R.id.layout_pain_rating_pain_0)
    LinearLayout mPainRating0;

    @InjectView(R.id.layout_pain_rating_pain_2)
    LinearLayout mPainRating2;

    @InjectView(R.id.layout_pain_rating_pain_4)
    LinearLayout mPainRating4;

    @InjectView(R.id.layout_pain_rating_pain_6)
    LinearLayout mPainRating6;

    @InjectView(R.id.layout_pain_rating_pain_8)
    LinearLayout mPainRating8;

    @InjectView(R.id.layout_pain_rating_pain_10)
    LinearLayout mPainRating10;

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

        ClickPainRating clickListener = new ClickPainRating();

        mPainRating0.setOnClickListener(clickListener);
        mPainRating2.setOnClickListener(clickListener);
        mPainRating4.setOnClickListener(clickListener);
        mPainRating6.setOnClickListener(clickListener);
        mPainRating8.setOnClickListener(clickListener);
        mPainRating10.setOnClickListener(clickListener);

        return dialog;
    }

    public class ClickPainRating implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.layout_pain_rating_pain_0:
                    prefs.setPainRating(0);
                    break;
                case R.id.layout_pain_rating_pain_2:
                    prefs.setPainRating(2);
                    break;
                case R.id.layout_pain_rating_pain_4:
                    prefs.setPainRating(4);
                    break;
                case R.id.layout_pain_rating_pain_6:
                    prefs.setPainRating(6);
                    break;
                case R.id.layout_pain_rating_pain_8:
                    prefs.setPainRating(8);
                    break;
                case R.id.layout_pain_rating_pain_10:
                    prefs.setPainRating(10);
                    break;
            }

            messageRouting.updateState(prefs.getCurrentState(), prefs.getStationName());
            dismiss();
        }
    }


    public interface DialogDismissedCallback {
        void onDialogDismissed();
    }
}
