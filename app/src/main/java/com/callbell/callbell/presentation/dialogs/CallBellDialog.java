package com.callbell.callbell.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.R;
import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.request.CallBellRequest;
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

    public static final String MESSAGE_KEY = "message";
    public static final String FROM_KEY = "bed_id";

    private String mTitle;
    private String fromString;
    private String mBody;
    private int mImage;
    private int mMessage;

    public static CallBellDialog newInstance(Bundle bundle) {
        CallBellDialog dialog = new CallBellDialog();
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
        mMessage = Integer.parseInt(getArguments().getString(MESSAGE_KEY));


        switch(mMessage) {
            case CallBellRequest.PAIN_ID:
                mBody = getString(R.string.pain) + " has been pressed";
                mImage = R.drawable.call_bell_pain;
                break;
            case CallBellRequest.HELP_ID:
                mBody = getString(R.string.help) + " has been pressed";
                mImage = R.drawable.call_bell_help;
                break;
            case CallBellRequest.BLANKET_ID:
                mBody = getString(R.string.blanket) + " has been pressed";
                mImage = R.drawable.call_bell_blanket;
                break;
            case CallBellRequest.FOOD_ID:
                mBody = getString(R.string.food_water) + " has been pressed";
                mImage = R.drawable.call_bell_food;
                break;
            case CallBellRequest.RESTROOM_ID:
                mBody = getString(R.string.restroom) + " has been pressed";
                mImage = R.drawable.call_bell_restroom;
                break;
                default:
                    mBody = "";
                    mImage = 0;
                    break;
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

            int message = 0;

            switch(v.getId()) {
                case R.id.dialog_station_acknowledge:
                    message = R.string.call_received;
                    break;
                case R.id.dialog_station_omw:
                    message = R.string.on_my_way;
                    break;
                case R.id.dialog_station_wait:
                    message = R.string.wait;
                    break;
            }

            mMessageRouting.sendMessage(fromString, PrefManager.CATEGORY_CALL_BELL_RESPONSE, message);
            dismiss();
        }
    }
}