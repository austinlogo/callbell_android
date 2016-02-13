package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

    public static final String TAG = PainRatingDialog.class.getSimpleName();

    @Inject
    PrefManager prefs;

    @Inject
    MessageRouting messageRouting;

    @InjectView(R.id.layout_pain_rating_0)
    Button mPainRating0;

    @InjectView(R.id.layout_pain_rating_1)
    Button mPainRating1;

    @InjectView(R.id.layout_pain_rating_2)
    Button mPainRating2;

    @InjectView(R.id.layout_pain_rating_3)
    Button mPainRating3;

    @InjectView(R.id.layout_pain_rating_4)
    Button mPainRating4;

    @InjectView(R.id.layout_pain_rating_5)
    Button mPainRating5;

    @InjectView(R.id.layout_pain_rating_6)
    Button mPainRating6;

    @InjectView(R.id.layout_pain_rating_7)
    Button mPainRating7;

    @InjectView(R.id.layout_pain_rating_8)
    Button mPainRating8;

    @InjectView(R.id.layout_pain_rating_9)
    Button mPainRating9;

    @InjectView(R.id.layout_pain_rating_10)
    Button mPainRating10;


    private DialogDismissedCallback mCallback;
    private int selectedChoice;

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(TAG) == null) {
            super.show(manager, tag);
        }
    }

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
        mPainRating1.setOnClickListener(clickListener);
        mPainRating2.setOnClickListener(clickListener);
        mPainRating3.setOnClickListener(clickListener);
        mPainRating4.setOnClickListener(clickListener);
        mPainRating5.setOnClickListener(clickListener);
        mPainRating6.setOnClickListener(clickListener);
        mPainRating7.setOnClickListener(clickListener);
        mPainRating8.setOnClickListener(clickListener);
        mPainRating9.setOnClickListener(clickListener);
        mPainRating10.setOnClickListener(clickListener);

        setSelectedChoice(prefs.painRating());



        return dialog;
    }

    public class ClickPainRating implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // Get pain rating from the Button's text.
            Button btn = (Button) v;
            String str = btn.getText().toString();
            prefs.setPainRating(Integer.parseInt(str));

            messageRouting.updateState(prefs.getCurrentState(), prefs.getStationName());
            dismiss();
        }
    }

    public void setSelectedChoice(int selectedChoice) {

        switch (selectedChoice) {
            case 0:
                mPainRating0.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 1:
                mPainRating1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 2:
                mPainRating2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 3:
                mPainRating3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 4:
                mPainRating4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 5:
                mPainRating5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 6:
                mPainRating6.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 7:
                mPainRating7.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 8:
                mPainRating8.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 9:
                mPainRating9.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 10:
                mPainRating10.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;


        }
    }

    public interface DialogDismissedCallback {
        void onDialogDismissed();
    }
}
