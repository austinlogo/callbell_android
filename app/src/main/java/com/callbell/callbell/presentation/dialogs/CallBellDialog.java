package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.util.PrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/9/15.
 */
public class CallBellDialog extends DialogFragment {
    public static String TAG = CallBellDialog.class.getSimpleName();

    @Inject
    MessageRouting mMessageRouting;

    @InjectView(R.id.dialog_station_acknowledge)
    Button acknowledgeButton;

    @InjectView(R.id.dialog_station_wait)
    Button waitButton;

    @InjectView(R.id.dialog_station_omw)
    Button onMyWayButton;

    @InjectView(R.id.dialog_station_title)
    TextView titleText;

    @InjectView(R.id.dialog_station_body)
    TextView bodyText;

    @InjectView(R.id.dialog_station_image)
    ImageView image;

    public static final String REASON_KEY = "payload";
    public static final String FROM_KEY = "bed_id";

    private String mTitle;
    private String fromString;
    private String mBody;
    private int mImage;
    private MessageReason mReason;

    public static CallBellDialog newInstance(State st, MessageReason reason) {
        CallBellDialog dialog = new CallBellDialog();
        Bundle bundle = new Bundle();

        bundle.putString(FROM_KEY, st.getLocation());
        bundle.putString(REASON_KEY, reason.name());

        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CallBellApplication) getActivity().getApplication()).inject(this);

        Log.d(TAG, "Bundle: " + getArguments().toString());
        fromString = getArguments().getString(FROM_KEY);
        mTitle = getText(R.string.bed) + ": " + fromString;
        mReason = MessageReason.valueOf(getArguments().getString(REASON_KEY));
        mBody = mReason.name() + " " + getString(R.string.requested);

        if (mReason == MessageReason.HELP) {
            mImage = R.drawable.call_bell_help;
        } else if (mReason == MessageReason.PAIN) {
            mImage = R.drawable.call_bell_pain;
        } else if (mReason == MessageReason.RESTROOM) {
            mImage = R.drawable.call_bell_restroom;
        } else if (mReason == MessageReason.BLANKET) {
            mImage = R.drawable.call_bell_blanket;
        } else if (mReason == MessageReason.FOOD) {
            mImage = R.drawable.call_bell_food;
        }

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_station_call_bell);
        dialog.setCanceledOnTouchOutside(false);
        ButterKnife.inject(this, dialog);

        titleText.setText(mTitle);
        bodyText.setText(mBody);
        image.setImageResource(mImage);

        acknowledgeButton.setOnClickListener(new DialogButtonOnClickListener());
        waitButton.setOnClickListener(new DialogButtonOnClickListener());
        onMyWayButton.setOnClickListener(new DialogButtonOnClickListener());

        return dialog;
    }

    public class DialogButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            MessageReason reason = MessageReason.ACKNOWLEDGED;

            switch(v.getId()) {
                case R.id.dialog_station_acknowledge:
                    reason = MessageReason.ACKNOWLEDGED;
                    break;
                case R.id.dialog_station_omw:
                    reason = MessageReason.ON_MY_WAY;
                    break;
                case R.id.dialog_station_wait:
                    reason = MessageReason.WAIT;
                    break;
            }

            mMessageRouting.sendMessage(fromString, PrefManager.CATEGORY_CALL_BELL_RESPONSE, reason);
            dismiss();
        }
    }
}