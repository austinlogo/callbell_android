package com.callbell.callbell.presentation.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.callbell.callbell.R;
import com.callbell.callbell.data.EducationMetricLogger;
import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.presentation.bed.PlanOfCareFragment;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlanOfCareInfoDialog extends Activity {

    public static final String TITLE_KEY = "DIALOG_TITLE";
    public static final String EXPANDED_KEY = "FULL_NAME_TEXT";
    public static final String BODY_KEY = "DIALOG_BODY";
    private static final String TAG = PlanOfCareFragment.class.getSimpleName();

    @InjectView(R.id.poc_info_layout)
    LinearLayout layout;

    @InjectView(R.id.poc_info_body)
    TextView mBodyText;

    @InjectView(R.id.poc_info_title)
    TextView mTitleText;

    @InjectView(R.id.poc_info_video)
    VideoView mVideo;

    @InjectView(R.id.poc_info_expanded_name)
    TextView mExpandedNameText;

    @InjectView(R.id.poc_info_dismiss)
    Button dismissButton;

    private Date timeOpen;
    private String mTitle;
    private String mExpandedName;
    private String mBody;

    public static Bundle newInstance(String tit, String bod) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, tit);
        bundle.putString(BODY_KEY, bod);

        return bundle;
    }

    public static Bundle newInstance(String tit, String expanded, String bod) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, tit);
        bundle.putString(EXPANDED_KEY, expanded);
        bundle.putString(BODY_KEY, bod);

        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFinishOnTouchOutside(true);

        mTitle = getIntent().getStringExtra(TITLE_KEY);
        mBody = getIntent().getStringExtra(BODY_KEY);

        timeOpen = new Date();

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (getIntent().getExtras().containsKey(EXPANDED_KEY)) {
            mExpandedName = getIntent().getStringExtra(EXPANDED_KEY);
        }

//        setTheme(R.style.Bea_Theme_AlertDialog);
        setContentView(R.layout.layout_poc_info);

        ButterKnife.inject(this);

        if (!TextUtils.isEmpty(mExpandedName)) {
            mExpandedNameText.setVisibility(View.VISIBLE);
        }

        // TODO: Change trigger to not Demo version
        if (mTitle.equals(MedicationValues.ALBUTEROL)) {
            mVideo.setVisibility(View.VISIBLE);
            mBodyText.setVisibility(View.GONE);
        } else {
            mVideo.setVisibility(View.GONE);
            mBodyText.setVisibility(View.VISIBLE);
        }



        MediaController videoControls = new MediaController(this);
        mVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_tutorial));
        videoControls.setAnchorView(mVideo);
        videoControls.setMediaPlayer(mVideo);
        mVideo.setMediaController(videoControls);
        mVideo.start();

        mTitleText.setText(mTitle);
        mExpandedNameText.setText(mExpandedName);
        mBodyText.setText(mBody);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EducationMetricLogger.getInstance().add(mTitle, timeOpen, new Date().getTime() - timeOpen.getTime());
    }
}
