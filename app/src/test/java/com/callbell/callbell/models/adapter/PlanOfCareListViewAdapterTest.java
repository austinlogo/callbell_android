package com.callbell.callbell.models.adapter;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import android.view.View;

import com.callbell.callbell.R;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class PlanOfCareListViewAdapterTest extends AndroidTestCase {

    PlanOfCareListViewAdapter mAdapter;

    @Before
    public void setUp() {
        mAdapter = new PlanOfCareListViewAdapter(new MockContext(), 0, Arrays.asList("one", "two", "three"), 1);
    }

    @Test
    public void testSetPendingItemCount() throws Exception {
        mAdapter.setPendingItemCount(2);
    }

    @Test
    public void testGetView() throws Exception {
//        View v = mAdapter.getView(0, , null);
    }
}