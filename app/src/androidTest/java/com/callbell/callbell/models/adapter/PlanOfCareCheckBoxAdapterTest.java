package com.callbell.callbell.models.adapter;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;

import com.callbell.callbell.R;
import com.callbell.callbell.data.POCValues;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by austin on 2/7/16.
 */
public class PlanOfCareCheckBoxAdapterTest extends AndroidTestCase {

    public void setUp() throws Exception {
        super.setUp();

        PlanOfCareCheckBoxAdapter adapter = new PlanOfCareCheckBoxAdapter(new MockContext(),
                R.layout.item_multi_check,
                Arrays.asList("one", "two", "three"),
                POCValues.masterMap);

        assertEquals(3, adapter.getCount());
    }

}