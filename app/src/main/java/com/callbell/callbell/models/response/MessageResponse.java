package com.callbell.callbell.models.response;

import android.os.Bundle;

import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.models.request.Request;
import com.callbell.callbell.util.JSONUtil;

/**
 * Created by austin on 10/31/15.
 */
public class MessageResponse {

    public static String BED_ID = "BED_ID";

    public MessageReason messageReason;
    public String from;
    public State state;

    public MessageResponse(Bundle bundle) {
        messageReason = MessageReason.valueOf(bundle.getString(Request.PAYLOAD_KEY));
        from = bundle.getString(BED_ID);
        state = new State(JSONUtil.getJSONFromString(bundle.getString(State.STATE_ID)));
    }
}
