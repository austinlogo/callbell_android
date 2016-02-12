package com.callbell.callbell.models.request;

import com.callbell.callbell.models.State.MessageReason;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class CallBellRequestTest {
    CallBellRequest request;

    @Before
    public void setUp() throws Exception {
        request = new CallBellRequest(TestData.state_bed(), "TEST_ER_STATION", MessageReason.PAIN, "call_bell");
    }

    @Test
    public void testGetHospital() throws Exception {
        assertEquals("TEST", request.getHospital());
    }

    @Test
    public void testGetMessage() throws Exception {
        assertEquals(MessageReason.PAIN.name(), request.getMessage());
    }

    @Test
    public void testGetTo() throws Exception {
        assertEquals("TEST_ER_STATION", request.getTo());
    }

    @Test
    public void testGetFrom() throws Exception {
        assertEquals("1", request.getFrom());
    }

    @Test
    public void testGetOperation() throws Exception {
        assertEquals("/receive", request.getOperation());
    }

    @Test
    public void testToJSON() throws Exception {
        JSONObject obj = request.toJSON();
        assertNotNull(obj);
    }
}