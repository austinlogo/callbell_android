package com.callbell.callbell.models.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by austin on 12/15/15.
 */
public class EducationMetric {

    public static String EDUCATION_METRIC_ID = "EDUCATION_METRIC_ID";
    public static String EDUCATION_METRIC_TITLE = "EDUCATION_METRIC_TITLE";
    public static String EDUCATION_METRIC_DATE = "EDUCATION_METRIC_DATE";
    public static String EDUCATION_METRIC_ELAPSED_TIME = "EDUCATION_METRIC_ELAPSED_TIME";

    private static List<EducationMetric> educationmetricList;
    private String title;
    private Date date;
    private long elapsedTime;
    private java.text.SimpleDateFormat sdf;

    public EducationMetric(String tit, Date dt, long elapsed) {

        title = tit;
        date = dt;
        elapsedTime = elapsed;

        sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return sdf.format(date);
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
