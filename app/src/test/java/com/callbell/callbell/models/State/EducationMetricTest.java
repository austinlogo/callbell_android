package com.callbell.callbell.models.State;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class EducationMetricTest {
    public EducationMetric metric;

    @Before
    public void setUp() throws Exception {
        metric = new EducationMetric("Title", new Date(), 5000L);
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("Title", metric.getTitle());
    }

    @Test
    public void testGetDate() throws Exception {
        assertNotNull(metric.getDate());
    }

    @Test
    public void testGetElapsedTime() throws Exception {
        assertEquals(5000L, metric.getElapsedTime());
    }
}