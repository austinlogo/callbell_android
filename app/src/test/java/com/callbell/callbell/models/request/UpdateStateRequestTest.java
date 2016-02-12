package com.callbell.callbell.models.request;

import org.junit.Before;
import org.junit.Test;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class UpdateStateRequestTest {
    UpdateStateRequest request;

    @Before
    public void setUp() throws Exception {
        request = new UpdateStateRequest(TestData.state_bed(), TestData.state_bed().getStationTabletName());
    }

    @Test
    public void testGetOperation() throws Exception {
        assertEquals("/updateState", request.getOperation());
    }

    @Test
    public void testToJSON() throws Exception {
        assertNotNull(request.toJSON());
    }
}