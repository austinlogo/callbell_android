package com.callbell.callbell.data;

import android.test.mock.MockContext;

import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/11/16.
 */
public class POCValuesTest {

    @Before
    public void setUp() throws Exception {
        POCValues values = new POCValues(new MockContext());
        assertNotNull(values);
    }
}