package com.callbell.callbell.models.request;

import org.junit.Before;
import org.junit.Test;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class RetrieveStateRequestTest {
    RetrieveStateRequest request;

    @Before
    public void setUp() throws Exception {
        request = new RetrieveStateRequest(TestData.state_bed());
    }

    @Test
    public void testToJSON() throws Exception {
        assertNotNull(request.toJSON());
    }
}