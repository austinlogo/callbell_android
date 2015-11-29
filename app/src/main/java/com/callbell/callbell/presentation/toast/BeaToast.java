package com.callbell.callbell.presentation.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.callbell.callbell.R;
import com.callbell.callbell.models.State.MessageReason;

/**
 * Created by austin on 11/24/15.
 */
public class BeaToast extends Toast {

    public static BeaToast makeText(Context context, int res, int length) {
        BeaToast toast = new BeaToast(context);

        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.toast_bea, null);

        TextView text = (TextView) layout.findViewById(R.id.toast_bea_text);
        text.setText(context.getString(res));

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(length);
        toast.setView(layout);

        return toast;
    }

    public static BeaToast makeText(Context context, MessageReason reason, int length) {
        int resId = -1;

        switch (reason) {
            case ON_MY_WAY:
                resId = R.string.on_my_way;
                break;
            case WAIT:
                resId = R.string.wait;
                break;
            default:
                resId = R.string.acknowledge;
        }

        return makeText(context, resId, length);
    }

    public BeaToast(Context context) {
        super(context);
    }


}
