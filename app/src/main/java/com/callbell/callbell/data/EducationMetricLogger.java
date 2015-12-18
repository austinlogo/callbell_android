package com.callbell.callbell.data;

import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.EducationMetric;
import com.callbell.callbell.util.PrefManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by austin on 12/15/15.
 */
public class EducationMetricLogger {

    public static final String METRIC_LIST = "METRIC_LIST";
    private static EducationMetricLogger logger;
    private List<EducationMetric> educationMetricList = new ArrayList<>();

    //TODO: Why is inject coming up null?
    MessageRouting mMessageRouting;

    public EducationMetricLogger() {
        mMessageRouting = new MessageRouting();
    }


    public static EducationMetricLogger getInstance() {
        if (logger == null) {
            logger = new EducationMetricLogger();
        }

        return logger;
    }

    public void add(String tit, Date dt, long elapsedInMillis) {

        educationMetricList.add(new EducationMetric(tit, dt, elapsedInMillis));

        if (educationMetricList.size() == 10) {
            mMessageRouting.uploadEducationMetrics(PrefManager.getCurrentState(), educationMetricList);
        }
    }

    public List<EducationMetric> getEducationMetricList() {
        return educationMetricList;
    }
}
