package com.callbell.callbell.data;

import com.callbell.callbell.models.State.EducationMetric;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/11/16.
 */
public class EducationMetricLoggerTest {
    private EducationMetricLogger mLogger;

    @Before
    public void setUp() throws Exception {
//        mLogger = new EducationMetricLogger();
//
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(EducationMetricLogger.getInstance());
        assertNotNull(EducationMetricLogger.getInstance());
    }

    @Test
    public void testAdd() throws Exception {
        mLogger = EducationMetricLogger.getInstance();
        mLogger.add("title", new Date(), 1000L);

        List<EducationMetric> metrics = mLogger.getEducationMetricList();

        assert(metrics.size() == 1);
        assert(metrics.get(0).getTitle().equals("title"));
    }
}