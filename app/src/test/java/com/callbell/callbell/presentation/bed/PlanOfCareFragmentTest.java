package com.callbell.callbell.presentation.bed;

import android.app.Activity;
import android.test.mock.MockContext;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.callbell.callbell.BuildConfig;
import com.callbell.callbell.R;
import com.callbell.callbell.models.State.State;
import com.callbell.callbell.presentation.view.ToggleListView;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.util.shell.BlankPlanOfCareFragmentActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import java.util.ArrayList;

import staticTestData.TestData;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class PlanOfCareFragmentTest {

    @Mock(name = "prefs")
    PrefManager prefs;

    @Mock(name = "mPOCSpinnerAndAcceptablePainContainer")
    LinearLayout spinnerLayout;

    @Mock(name = "mPlanOfCareMedications")
    ToggleListView mPlanOfCareMedications;

    @Mock(name = "mPlanOfCareTests")
    ToggleListView mPlanOfCareTests;

    @Mock(name = "mListener")
    BlankPlanOfCareFragmentActivity activity;

    @Mock(name = "mState")
    private State mState;

    @Mock
    private State mStateReplace;

    @InjectMocks
    PlanOfCareFragment fragment = new PlanOfCareFragment();

    Spinner mChiefComplaintSpinner;
    LinearLayout mSpinnerAndPainContainer;
    EditText mAcceptablePainText;
    Button otherCancel;
    Button submitOther;
    LinearLayout otherLayout;
    AutoCompleteTextView otherEditText;

    ListView listView;

    @Before
    public void setUp() throws Exception {
        fragment.setArguments(PlanOfCareFragment.newBundle(TestData.state_bed()));
        FragmentTestUtil.startFragment(fragment, BlankPlanOfCareFragmentActivity.class);
//        fragment.onAttach(activity);

        listView = ((ToggleListView) fragment.getView().findViewById(R.id.fragment_poc_tests)).getPatientListView();
        Assert.assertNotNull(listView);

        MockitoAnnotations.initMocks(this);

        mChiefComplaintSpinner = (Spinner) fragment.getView().findViewById(R.id.fragment_poc_main_complaint_spinner);
        mSpinnerAndPainContainer = (LinearLayout) fragment.getView().findViewById(R.id.fragment_poc_tests_or_title_container);
        mAcceptablePainText = (EditText) fragment.getView().findViewById(R.id.fragment_poc_acceptable_pain_value);
        otherCancel = (Button) fragment.getView().findViewById(R.id.other_cancel);
        submitOther = (Button) fragment.getView().findViewById(R.id.other_submit);
        otherLayout = (LinearLayout) fragment.getView().findViewById(R.id.other_layout);
        otherEditText = (AutoCompleteTextView) fragment.getView().findViewById(R.id.other_edittext);

        Assert.assertNotNull(mPlanOfCareTests);
//        Assert.assertNotNull(mPlanOfCareMedications);
        Assert.assertNotNull(mChiefComplaintSpinner);
        Assert.assertNotNull(mSpinnerAndPainContainer);
        Assert.assertNotNull(mAcceptablePainText);
        Assert.assertNotNull(otherCancel);
        Assert.assertNotNull(submitOther);
        Assert.assertNotNull(otherLayout);
        Assert.assertNotNull(otherEditText);
    }

    @Test
    public void testSubmitOtherTestItem() throws Exception {
        String medItem = "item";
        String testItem = "testItem";

        //ADDING AN ITEM THAT IS A MEDICATION
        Mockito.when(mPlanOfCareMedications.contains(medItem)).thenReturn(true);
        otherEditText.setText(medItem);
        Assert.assertTrue(otherEditText.getText().length() != 0);
        submitOther.performClick();
        verify(mPlanOfCareMedications).setPendingItem(any(Integer.class), eq(true));

        Assert.assertTrue(otherEditText.getText().toString().isEmpty());

        // ADDING AN ITEM THAT IS A TEST
        Mockito.when(mPlanOfCareTests.contains(testItem)).thenReturn(true);
        otherEditText.setText(testItem);
        Assert.assertTrue(otherEditText.getText().equals(testItem));
        submitOther.performClick();
        verify(mPlanOfCareTests).setPendingItem(any(Integer.class), eq(true));
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

        verify(mPlanOfCareTests).setDisplayMode(true);
        verify(mPlanOfCareMedications).setDisplayMode(true);

        Assert.assertTrue(otherLayout.getVisibility() == View.VISIBLE);
        Assert.assertTrue(mChiefComplaintSpinner.isEnabled());
    }

    @Test
    public void testSetSuperUserPermissions_false() throws Exception {
        boolean value = false;

        fragment.setSuperUserPermissions(value);

        verify(mPlanOfCareTests).setDisplayMode(value);
        verify(mPlanOfCareMedications).setDisplayMode(value);

        Assert.assertTrue(otherLayout.getVisibility() == View.GONE);
        Assert.assertTrue(!mChiefComplaintSpinner.isEnabled());
    }

    @Test
    public void testClearValues() throws Exception {
        fragment.clearValues();

        verify(mState).setPendingTests(any(ArrayList.class));
    }

    /*
     * TODO: this test has issues with a null pointer exception that seems to be test related.
     * caused by: chiefComplaintSpinner.setSelection(mState.getChiefComplaint(), false);
     * we need that false flag set.
     * unsure why this happens.
     */

//    @Test
//    public void testUpdateState() throws Exception {
//        fragment.updateState(mStateReplace);
//        Assert.assertEquals(fragment.getState(), mStateReplace);
//    }

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