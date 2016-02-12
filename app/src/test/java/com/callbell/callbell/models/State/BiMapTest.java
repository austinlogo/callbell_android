package com.callbell.callbell.models.State;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
public class BiMapTest {
    BiMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new BiMap<>();
    }

    @Test
    public void testPut() throws Exception {
        map.put(0, "zero");
        assertEquals("zero", map.getValue(0));
        assertEquals(0, (int) map.getKey("zero"));
    }
}