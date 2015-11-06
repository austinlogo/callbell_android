package com.callbell.callbell.presentation.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.callbell.callbell.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by austin on 10/15/15.
 */
public class PlanOfCareInfoDialog extends DialogFragment {

    public static final String TITLE_KEY = "DIALOG_TITLE";
    public static final String EXPANDED_KEY = "FULL_NAME_TEXT";
    public static final String BODY_KEY = "DIALOG_BODY";

    @InjectView(R.id.poc_info_body)
    TextView mBodyText;

    @InjectView(R.id.poc_info_title)
    TextView mTitleText;

    @InjectView(R.id.poc_info_expanded_name)
    TextView mExpandedNameText;

    @InjectView(R.id.poc_info_dismiss)
    Button dismissButton;

    private String mTitle;
    private String mExpandedName;
    private String mBody;

    public static PlanOfCareInfoDialog newInstance(String tit, String bod) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, tit);
        bundle.putString(BODY_KEY, bod);

        PlanOfCareInfoDialog dialog = new PlanOfCareInfoDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    public static PlanOfCareInfoDialog newInstance(String tit, String expanded, String bod) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, tit);
        bundle.putString(EXPANDED_KEY, expanded);
        bundle.putString(BODY_KEY, bod);

        PlanOfCareInfoDialog dialog = new PlanOfCareInfoDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = getArguments().getString(TITLE_KEY);
        mBody = getArguments().getString(BODY_KEY);

        if (getArguments().containsKey(EXPANDED_KEY)) {
            mExpandedName = getArguments().getString(EXPANDED_KEY);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_poc_info);

        ButterKnife.inject(this, dialog);

        if (!TextUtils.isEmpty(mExpandedName)) {
            mExpandedNameText.setVisibility(View.VISIBLE);
        }

        mTitleText.setText(mTitle);
        mExpandedNameText.setText(mExpandedName);
        mBodyText.setText(mBody);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }
}
