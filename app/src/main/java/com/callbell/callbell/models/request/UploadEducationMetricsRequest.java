package com.callbell.callbell.models.request;

import com.callbell.callbell.data.EducationMetricLogger;
import com.callbell.callbell.models.State.EducationMetric;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by austin on 12/15/15.
 */
public class UploadEducationMetricsRequest extends Request {

    private State mState;
    private List<EducationMetric> metricList;

    public UploadEducationMetricsRequest(State st, List<EducationMetric> list) {
        mState = st;
        metricList = list;
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();

        try {
            object.put(State.STATE_ID, mState.toJSON());
            object.put(EducationMetricLogger.METRIC_LIST, JSONUtil.EducationMetricListToJSONArray(metricList));
            return object;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }
}
