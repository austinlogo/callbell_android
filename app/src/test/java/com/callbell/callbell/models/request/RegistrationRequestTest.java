package com.callbell.callbell.models.request;

import org.junit.Before;
import org.junit.Test;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class RegistrationRequestTest {
    RegistrationRequest request, request_2;

    @Before
    public void setUp() throws Exception {
        request = new RegistrationRequest(TestData.state_bed(), TestData.state_bed().getTabletName());

//        request_2 = new RegistrationRequest(request);
    }

    @Test
    public void testGetState() throws Exception {
        assertTrue(TestData.state_bed().equals(request.getState()));
    }

    @Test
    public void testGetHospitalId() throws Exception {
        assertEquals(TestData.state_bed().getHospital(), request.getHospitalId());
    }

    @Test
    public void testGetGroupId() throws Exception {
        assertEquals(TestData.state_bed().getGroup(), request.getGroupId());
    }

    @Test
    public void testGetLocationId() throws Exception {
        assertEquals(TestData.state_bed().getLocation(), request.getLocationId());
    }

    @Test
    public void testGetRegisterId() throws Exception {
        assertEquals(TestData.state_bed().getTabletName(), request.getRegisterId());
    }

    @Test
    public void testGetOperation() throws Exception {
        assertEquals("/register", request.getOperation());
    }

    @Test
    public void testToJSON() throws Exception {
        assertNotNull(request.toJSON());
    }
}