package com.callbell.callbell.presentation.bed;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.callbell.callbell.BuildConfig;
import com.callbell.callbell.R;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.presentation.toast.BeaToast;
import com.callbell.callbell.presentation.view.ToggleListView;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.util.shell.BlankPlanOfCareFragmentActivity;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import java.util.ArrayList;

import staticTestData.TestData;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class PlanOfCareFragmentTest {

    @Mock(name = "prefs")
    PrefManager prefs;

    @Mock(name = "mListener")
    BlankPlanOfCareFragmentActivity activity;

    @Mock(name = "mState")
    private State mState;

    @Mock
    private State mStateReplace;

    @InjectMocks
    PlanOfCareFragment fragment = new PlanOfCareFragment();

    ToggleListView mPlanOfCareTests;
    ToggleListView mPlanOfCareMedications;
    Spinner mChiefComplaintSpinner;
    LinearLayout mSpinnerAndPainContainer;
    EditText mAcceptablePainText;
    Button otherCancel;
    Button submitOther;
    LinearLayout otherLayout;


    @Before
    public void setUp() throws Exception {
        fragment.setArguments(PlanOfCareFragment.newBundle(TestData.state_bed()));
        FragmentTestUtil.startFragment(fragment, BlankPlanOfCareFragmentActivity.class);
//        fragment.onAttach(activity);

        MockitoAnnotations.initMocks(this);

        mPlanOfCareTests = (ToggleListView) fragment.getView().findViewById(R.id.fragment_poc_tests);
        mPlanOfCareMedications = (ToggleListView) fragment.getView().findViewById(R.id.fragment_poc_medications);
        mChiefComplaintSpinner = (Spinner) fragment.getView().findViewById(R.id.fragment_poc_main_complaint_spinner);
        mSpinnerAndPainContainer = (LinearLayout) fragment.getView().findViewById(R.id.fragment_poc_tests_or_title_container);
        mAcceptablePainText = (EditText) fragment.getView().findViewById(R.id.fragment_poc_acceptable_pain_value);
        otherCancel = (Button) fragment.getView().findViewById(R.id.other_cancel);
        submitOther = (Button) fragment.getView().findViewById(R.id.other_submit);
        otherLayout = (LinearLayout) fragment.getView().findViewById(R.id.other_layout);
        Assert.assertNotNull(mPlanOfCareTests);
        Assert.assertNotNull(mPlanOfCareMedications);
        Assert.assertNotNull(mChiefComplaintSpinner);
        Assert.assertNotNull(mSpinnerAndPainContainer);
        Assert.assertNotNull(mAcceptablePainText);
        Assert.assertNotNull(otherCancel);
        Assert.assertNotNull(submitOther);
        Assert.assertNotNull(otherLayout);
    }

    @Test
    public void testSubmitOtherTestItem() throws Exception {

    }

    @Test (expected = ClassCastException.class)
    public void testOnAttach() throws Exception {
        fragment.onAttach(new Activity());
    }

    @Test
    public void testOnDetach() throws Exception {
        fragment.onDetach();
    }

    @Test
    public void testSetSuperUserPermissions_true() throws Exception {
        fragment.setSuperUserPermissions(true);

        Assert.assertTrue(mPlanOfCareTests.getAdminListView().getVisibility() == View.VISIBLE);
    }

    @Test
    public void testSetSuperUserPermissions_false() throws Exception {
        fragment.setSuperUserPermissions(false);

        Assert.assertTrue(mPlanOfCareTests.getAdminListView().getVisibility() == View.GONE);
    }

    @Test
    public void testClearValues() throws Exception {
        fragment.clearValues();

        verify(mState).setPendingTests(any(ArrayList.class));
    }

    @Test
    public void testUpdateState() throws Exception {
        fragment.updateState(mStateReplace);
        Assert.assertEquals(fragment.getState(), mStateReplace);
    }

    @Test
    public void testPlanOfCareTestListeners() {
        int index = 0;
        ListView patientList = mPlanOfCareTests.getPatientListView();
        patientList.performItemClick(patientList.getAdapter().getView(index, null, null), index, index);

        verify(activity).showInfoDialog(any(String.class), any(String.class), any(String.class));
    }


    @Test
    public void testPlanOfCareMedicationListeners() {
        int index = 0;
        ListView patientList = mPlanOfCareMedications.getPatientListView();
        patientList.performItemClick(patientList.getAdapter().getView(index, null, null), index, index);

        verify(activity).showInfoDialog(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void testNewInstance() throws Exception {
        PlanOfCareFragment testFragment = PlanOfCareFragment.newInstance(TestData.state_bed());
        FragmentTestUtil.startFragment(testFragment, BlankPlanOfCareFragmentActivity.class);

        Assert.assertNotNull(testFragment);
        Assert.assertTrue(testFragment.getState().equals(TestData.state_bed()));

    }

    @Test
    public void testGetState() throws Exception {
        Assert.assertNotNull(fragment.getState());
    }
}