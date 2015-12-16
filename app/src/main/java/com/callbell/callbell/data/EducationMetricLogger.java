package com.callbell.callbell.data;

import com.callbell.callbell.models.State.EducationMetric;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 12/15/15.
 */
public class EducationMetricLogger {

    public static final String METRIC_LIST = "METRIC_LIST";
    private static EducationMetricLogger logger;
    private List<EducationMetric> educationMetricList = new ArrayList<>();


    public static EducationMetricLogger getInstance() {
        if (logger == null) {
            logger = new EducationMetricLogger();
        }

        return logger;
    }

    public void add(String tit, long dt, long elapsed) {
        educationMetricList.add(new EducationMetric(tit, dt, elapsed));
    }

    public List<EducationMetric> getEducationMetricList() {
        return educationMetricList;
    }
}
