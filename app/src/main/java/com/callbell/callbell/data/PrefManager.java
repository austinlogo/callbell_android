package com.callbell.callbell.data;
import javax.inject.Singleton;

@Singleton
public class PrefManager {

    public static final String REG_ID = "REGISTRATION_ID";
    public static final String REG_UPLOADED = "REGISTRATION_UPLOADED_TO_GCM_SERVER";
    public static final String DEFAULT_SENDER_ID = "DEFAULT_SENDER_ID";

    public String getToken() {
        return "token";
    }
}
