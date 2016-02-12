package com.callbell.callbell.models.request;

import com.callbell.callbell.models.State.EducationMetric;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class UploadEducationMetricsRequestTest {
    UploadEducationMetricsRequest request;


    @Before
    public void setUp() throws Exception {
        request = new UploadEducationMetricsRequest(TestData.state_bed(), new ArrayList<EducationMetric>());
    }

    @Test
    public void testToJSON() throws Exception {
        assertNotNull(request.toJSON());
    }
}