package com.callbell.callbell.models.request;

import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;

import org.bouncycastle.ocsp.Req;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 11/30/15.
 */
public class CallBellRequestTest {

    CallBellRequest request;

    @Before
    public void setUp() {
        request = new CallBellRequest(TestData.state_bed(), TestData.state_bed().getTabletName(), MessageReason.RESTROOM, "call_bell");
    }

    @Test
    public void testGetHospital() throws Exception {
        assertEquals(request.getHospital(), TestData.state_bed().getHospital());
    }

    @Test
    public void testGetMessage() throws Exception {
        assertEquals(request.getMessage(), MessageReason.RESTROOM.name());
    }

    @Test
    public void testGetTo() throws Exception {
        assertEquals(request.getTo(), TestData.state_station().getTabletName());
    }

    @Test
    public void testGetFrom() throws Exception {
        assertEquals(request.getFrom(), TestData.state_bed().getTabletName());
    }

    @Test
    public void testGetOperation() throws Exception {
        assertEquals(request.getOperation(), "/receive");
    }

    @Test
    public void testToJSON() throws Exception {
        JSONObject object = request.toJSON();

        assertEquals(object.getJSONObject(State.STATE_ID), TestData.state_bed().toJSON());
        assertEquals(object.getString(Request.TO_KEY), request.getTo());
    }
}