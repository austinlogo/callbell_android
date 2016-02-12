package com.callbell.callbell.models.response;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class ConnectionStatusUpdateResponseTest {
    ConnectionStatusUpdateResponse res;

    @Before
    public void setUp() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("CONNECTION_STATUS", true);
        obj.put("TABLET_NAME", TestData.state_bed().getTabletName());

        res = new ConnectionStatusUpdateResponse(obj);
    }

    @Test
    public void testGetConnectionStatus() throws Exception {
//        assertTrue(res.getConnectionStatus());
    }

    @Test
    public void testGetTabletName() throws Exception {
//        assertEquals(TestData.state_bed().getTabletName(), res.getTabletName());
    }
}