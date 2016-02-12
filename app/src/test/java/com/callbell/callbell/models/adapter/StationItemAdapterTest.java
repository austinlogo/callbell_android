package com.callbell.callbell.models.adapter;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.models.State.MessageReason;
import com.callbell.callbell.models.State.State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import staticTestData.TestData;

import static org.junit.Assert.*;

/**
 * Created by austin on 2/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class StationItemAdapterTest extends AndroidTestCase {

    StationItemAdapter mAdapter;

    @Mock
    MessageRouting routing;

    @Before
    public void setUp() throws Exception {
        mAdapter = new StationItemAdapter(new MockContext(), Arrays.asList(TestData.state_bed(), TestData.state_bed(), TestData.state_bed()), routing);
    }

    @Test
    public void testGetCount() throws Exception {
        assertEquals(3, mAdapter.getCount());
    }

    @Test
    public void testGetItem() throws Exception {
        State st = mAdapter.getItem(0);
        assertTrue(st.equals(TestData.state_bed()));
    }

    @Test
    public void testGetItemId() throws Exception {
        assertEquals(0, mAdapter.getItemId(0));
    }

    @Test
    public void testGetView() throws Exception {
        // TODO: 2/7/16 : Implement
    }

    @Test
    public void testUpdateItem() throws Exception {
        State st = TestData.state_bed();
        st.setStaff("A", "B", "C");
        mAdapter.updateItem(st);

        st = State.newBlankInstance("TEST", "ER", "2", "BED");
        mAdapter.updateItem(st, MessageReason.PAIN);
        mAdapter.updateItem(st);
    }

    @Test
    public void testUpdateItem1() throws Exception {
        State st = TestData.state_bed();
        st.setStaff("A", "B", "C");
        mAdapter.updateItem(st, MessageReason.PAIN);
        assertEquals(MessageReason.PAIN, mAdapter.getReason(0));

        mAdapter.updateItem(st, MessageReason.QUIET);
        assertEquals(MessageReason.QUIET, mAdapter.getReason(0));
    }

    @Test
    public void testUpdateItem2() throws Exception {
        State st = TestData.state_bed();
        st.setStaff("A", "B", "C");
        mAdapter.updateItem(0, MessageReason.PAIN);
        assertEquals(MessageReason.PAIN, mAdapter.getReason(0));
    }

    @Test
    public void testGetReason() throws Exception {
        assertEquals(MessageReason.QUIET, mAdapter.getReason(0));
    }

    @Test
    public void testUpdateConnectedStatuses() throws Exception {
        mAdapter.updateConnectedStatuses(Arrays.asList("TEST_ER_1"));
        assertEquals(true, mAdapter.getItem(0).isConnected());
    }

    @Test
    public void testUpdateConnectedStatus() throws Exception {
        mAdapter.updateConnectedStatus("TEST_ER_1", false);
        assertEquals(false, mAdapter.getItem(0).isConnected());

        mAdapter.updateConnectedStatus("TEST_ER_1", true);
        assertEquals(true, mAdapter.getItem(0).isConnected());
    }

    @Test
    public void testIsPriorityListEmpty() throws Exception {
        assertTrue(mAdapter.isPriorityListEmpty());

        mAdapter.updateItem(0, MessageReason.PAIN);
        assertFalse(mAdapter.isPriorityListEmpty());
    }
}