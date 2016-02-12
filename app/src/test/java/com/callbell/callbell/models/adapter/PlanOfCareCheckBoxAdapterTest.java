package com.callbell.callbell.models.adapter;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import android.view.View;

import com.callbell.callbell.data.POCValues;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotEquals;


/**
 * Created by austin on 2/7/16.
 */
//@RunWith(MockitoJUnitRunner.class)
public class PlanOfCareCheckBoxAdapterTest  extends AndroidTestCase {
    PlanOfCareCheckBoxAdapter mAdapter;
    List<String> values;

    Context mContext;

    public PlanOfCareCheckBoxAdapterTest(){
        super();

//        mContext = new MockContext().createPackageContext(new CallBellApplication().getPackageName(), MockContext.CONTEXT_INCLUDE_CODE);
        mContext = new MockContext();
        new POCValues(mContext);
    }

    @Before
    public void setUp() throws Exception {
//        super.setUp();

        setContext(mContext);
        testAndroidTestCaseSetupProperly();

        values = new ArrayList<>();
        values.add("one");
        values.add("two");
        values.add("three");

        mAdapter = new PlanOfCareCheckBoxAdapter(mContext, 0, values, POCValues.masterMap);
        assertEquals("one", mAdapter.getItem(0));
    }

    @After
    public void tearDown() throws Exception {
        mContext = null;
    }

    @Test
    public void testGetList() throws Exception {
        assertEquals(values.size(), mAdapter.getList().size());
    }

    @Test
    public void testAdd() throws Exception {
        mAdapter.add("four");
        assertEquals(4, mAdapter.getCount());
    }

    @Test
    public void testGetView() throws Exception {
//        View view = mAdapter.getView(0, null, null);
//        assertNotNull(view);
    }

    @Test
    public void testContains() throws Exception {
        assertTrue(mAdapter.contains("one"));
        assertFalse(mAdapter.contains("s"));
    }

    @Test
    public void testcontains_nullEntry() {
        assertFalse(mAdapter.contains(null));
    }

    @Test
    public void testResetList() throws Exception {
        // TODO: 2/7/16 : Implement 
    }

    @Test
    public void testGetItem() throws Exception {
        assertEquals("one", mAdapter.getItem(0));
    }

    @Test
    public void testResetSelectedItems() throws Exception {
        mAdapter.setPendingItem(0, true);
        mAdapter.setDoneItem(0, true);
        assertNotEquals(0, mAdapter.getPendingItems());
        assertNotEquals(0, mAdapter.getDoneItems());

        mAdapter.resetSelectedItems();
        assertEquals(0, mAdapter.getPendingItems().size());
        assertEquals(0, mAdapter.getDoneItems().size());
    }

    @Test
    public void testSetPendingItems() throws Exception {
        mAdapter.setPendingItems(Arrays.asList(0, 1, 2));
        assertEquals(3, mAdapter.getPendingItems().size());

    }

    @Test
    public void testSetDoneItems() throws Exception {
        mAdapter.setDoneItems(Arrays.asList(0, 1, 2));
        assertEquals(3, mAdapter.getDoneItems().size());
    }

    @Test
    public void testSetPendingItem() throws Exception {
        mAdapter.setPendingItem(0, true);
        mAdapter.setDoneItem(0, true);
        assertNotEquals(0, mAdapter.getPendingItems());
        assertNotEquals(0, mAdapter.getDoneItems());

        mAdapter.setPendingItem(0, false);
    }

    @Test
    public void testSetDoneItem() throws Exception {
        mAdapter.setPendingItem(0, true);
        mAdapter.setDoneItem(0, true);
        assertNotEquals(0, mAdapter.getPendingItems());
        assertNotEquals(0, mAdapter.getDoneItems());

        mAdapter.setPendingItem(0, false);
    }

    @Test
    public void testSetSuperUser() throws Exception {
        mAdapter.setSuperUser(true);
    }

    @Test
    public void testGetPendingItems() throws Exception {
        mAdapter.setPendingItems(Arrays.asList(0, 1, 2));
        Set<Integer> set = mAdapter.getPendingItems();

        assertEquals(3, set.size());
        assertTrue(set.contains(0) && set.contains(1) && set.contains(2));
    }

    @Test
    public void testGetDoneItems() throws Exception {
        mAdapter.setDoneItems(Arrays.asList(0, 1, 2));
        Set<Integer> set = mAdapter.getDoneItems();

        assertEquals(3, set.size());
        assertTrue(set.contains(0) && set.contains(1) && set.contains(2));
    }

    @Test
    public void testGetViewByPosition() throws Exception {
        // TODO: 2/7/16 : Implement
    }

    @Test
    public void testGetPendingItemsSize() throws Exception {
        mAdapter.setPendingItems(Arrays.asList(0, 1, 2));
        assertEquals(3, mAdapter.getPendingItemsSize());
    }
}