package com.callbell.callbell.models.request;

import org.junit.Before;
import org.junit.Test;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class GetStatesRequestTest {
    GetStatesRequest request;

    @Before
    public void setUp() {
        request = new GetStatesRequest(TestData.state_bed());
    }

    @Test
    public void testGetOperation() throws Exception {
        assertEquals("/getDeviceStates", request.getOperation());
    }

    @Test
    public void testToJSON() throws Exception {
        assertNotNull(request.toJSON());
    }
}