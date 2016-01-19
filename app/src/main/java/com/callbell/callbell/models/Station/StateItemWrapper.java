package com.callbell.callbell.models.Station;

import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;

import java.util.List;

/**
 * Created by austin on 1/18/16.
 */
public class StateItemWrapper {

    State mState;
    MessageReason mReason;

    public StateItemWrapper(State st, MessageReason mr) {
        mState = new State(st);
        mReason = mr;
    }

    public State getState() {
        return mState;
    }

    public MessageReason getMessageReason() {
        return mReason;
    }

    public void setState(State st) {
        mState = new State(st);
    }

    public void setMessageReason(MessageReason reason) {
        mReason = reason;
    }
}
