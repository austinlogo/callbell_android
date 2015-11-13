package com.callbell.callbell.models.response;

import com.callbell.callbell.util.JSONUtil;

import org.json.JSONObject;

/**
 * Created by austin on 11/10/15.
 */
public class ConnectionStatusUpdateResponse extends Response{

    public static String INTENT_ACTION = "CONNECTION_STATUS_UPDATE";
    public static String INTENT_EXTRA_JSON_STRING = "INTENT_EXTRA_JSON_STRING";

    public static String CONNECTION_STATUS = "CONNECTION_STATUS";
    public static String TABLET_NAME = "TABLET_NAME";

    private boolean mConnectionStatus;
    private String mTabletName;

    public ConnectionStatusUpdateResponse(JSONObject object) {

        mConnectionStatus = JSONUtil.getValueBooleanValueIfExists(object, CONNECTION_STATUS);
        mTabletName = JSONUtil.getValueStringIfExists(object, TABLET_NAME);
    }

    public boolean getConnectionStatus() {
        return mConnectionStatus;
    }

    public String getTabletName() {
        return mTabletName;
    }

}
