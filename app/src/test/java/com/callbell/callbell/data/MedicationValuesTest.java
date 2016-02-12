package com.callbell.callbell.data;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/11/16.
 */
public class MedicationValuesTest extends AndroidTestCase {

    @Before
    public void setUp() throws Exception {
        MedicationValues values = new MedicationValues(new MockContext());
        assertNotNull(values);
    }
}