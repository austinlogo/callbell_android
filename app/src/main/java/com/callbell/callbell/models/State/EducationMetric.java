package com.callbell.callbell.models.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by austin on 12/15/15.
 */
public class EducationMetric {

    public static String EDUCATION_METRIC_TITLE = "EDUCATION_METRIC_TITLE";
    public static String EDUCATION_METRIC_DATE = "EDUCATION_METRIC_DATE";
    public static String EDUCATION_METRIC_ELAPSED_TIME = "EDUCATION_METRIC_ELAPSED_TIME";

    private static List<EducationMetric> educationmetricList;
    private String title;
    private long date;
    private long elapsedTime;

    public EducationMetric(String tit, long dt, long elapsed) {

        title = tit;
        date = dt;
        elapsedTime = elapsed;
    }

    public String getTitle() {
        return title;
    }

    public long getDate() {
        return date;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
