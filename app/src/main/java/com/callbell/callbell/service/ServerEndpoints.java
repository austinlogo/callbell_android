package com.callbell.callbell.service;

import javax.inject.Singleton;

/**
 * Created by austin on 10/4/15.
 */

@Singleton
public class ServerEndpoints {

    public static final String EMULATOR_LOCALHOST_SERVER_ENDPOINT = "http://10.0.0.18:6000";
    public static final String PROD_SERVER_ENDPOINT = "http://104.236.211.81:6000";

//    public static final String SERVER_ENDPOINT_IN_USE = PROD_SERVER_ENDPOINT;
    public static final String SERVER_ENDPOINT_IN_USE = EMULATOR_LOCALHOST_SERVER_ENDPOINT;
}
