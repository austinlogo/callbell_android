package com.callbell.callbell.presentation.bed;

import android.app.Activity;

import com.callbell.callbell.BuildConfig;
import com.callbell.callbell.util.FragmentAssertionsUtil;
import com.callbell.callbell.util.FragmentTestCase;
import com.callbell.callbell.util.shell.BlankStaffFragmentActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import staticTestData.TestData;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class StaffFragmentTest {

    private StaffFragment fragment;

    @Before
    public void setUp() {
        fragment = StaffFragment.newInstance(TestData.state_bed());
    }

    @Test (expected = ClassCastException.class)
    public void start_classexception() {
        FragmentTestUtil.startFragment(fragment, Activity.class);
    }

    @Test
    public void clearValues() {
        FragmentTestUtil.startFragment(fragment, BlankStaffFragmentActivity.class);
        Assert.assertNotNull(fragment);

        FragmentAssertionsUtil.assertStatement(fragment.nurseField.getText().equals(TestData.state_bed().getNurse()));
        FragmentAssertionsUtil.assertStatement(fragment.nurseField.getText().equals(TestData.state_bed().getNurse()));
        FragmentAssertionsUtil.assertStatement(fragment.residentField.getText().equals(TestData.state_bed().getResident()));
        FragmentAssertionsUtil.assertStatement(fragment.physicianField.getText().equals(TestData.state_bed().getPhysician()));
        fragment.clearValues();

        FragmentAssertionsUtil.assertStatement(fragment.nurseField.getText().equals(""));
        FragmentAssertionsUtil.assertStatement(fragment.residentField.getText().equals(""));
        FragmentAssertionsUtil.assertStatement(fragment.physicianField.getText().equals(""));
    }

    @Test
    public void updateStateTest() {
        FragmentTestUtil.startFragment(fragment, BlankStaffFragmentActivity.class);
        fragment.updateState(TestData.state_bed_2());

        FragmentAssertionsUtil.assertStatement(fragment.nurseField.getText().equals(TestData.state_bed_2().getNurse()));
        FragmentAssertionsUtil.assertStatement(fragment.residentField.getText().equals(TestData.state_bed_2().getResident()));
        FragmentAssertionsUtil.assertStatement(fragment.physicianField.getText().equals(TestData.state_bed_2().getPhysician()));
    }

    @Test
    public void getStateTest() {
        FragmentTestUtil.startFragment(fragment, BlankStaffFragmentActivity.class);
        FragmentAssertionsUtil.assertStatement(TestData.state_bed().equals(fragment.getState()));
    }
}
