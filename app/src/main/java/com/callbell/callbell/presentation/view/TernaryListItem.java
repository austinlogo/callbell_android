package com.callbell.callbell.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.callbell.callbell.R;

/**
 * Created by austin on 11/13/15.
 */
public class TernaryListItem extends RelativeLayout {


    public enum TernaryItemState {
        NOT_SELECTED,
        PENDING,
        DONE
    }

    private static final String TAG = TernaryListItem.class.getSimpleName();

    private static final int[] STATE_SELECTED_KEYS= {
            R.attr.state_pending
    };

    private static final int[] STATE_DONE= {
            R.attr.state_done
    };

    private boolean mIsPending;
    private boolean mIsDone;
    private TextView mText;
    private ImageView mImage;

    public TernaryListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TernaryListItem(Context context) {
        super(context);
        init();
    }

    public TernaryListItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public String getText() {
        return mText.getText().toString();
    }

    private void init() {
        View v = inflate(getContext(), R.layout.item_poc_list_item, this);
        mText = (TextView) v.findViewById(R.id.item_poc_list_item_text);
        mImage = (ImageView) v.findViewById(R.id.item_poc_list_item_img);

        mIsPending = false;
        mIsDone = false;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] result =  super.onCreateDrawableState(extraSpace + 2);

        if (mIsPending) {
            mergeDrawableStates(result, STATE_SELECTED_KEYS);
        }

        if (mIsDone) {
            mergeDrawableStates(result, STATE_DONE);
        }

        return result;
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public boolean isPending() {
        return mIsPending && !mIsDone;
    }

    public boolean isDone() {
        return mIsDone && mIsPending;
    }

    public void setState(TernaryItemState tis) {

        switch (tis) {
            case NOT_SELECTED:
                mIsPending = false;
                mIsDone = false;
                break;
            case PENDING:
                mIsPending = true;
                mIsDone = false;
                break;
            case DONE:
                mIsPending = true;
                mIsDone = true;
                break;
        }

        setUI();
    }

    public void moveToNextState() {

        if (!mIsPending) {
            mIsPending = true;
        } else if (!mIsDone) {
            mIsDone = true;
        } else {
            mIsDone = false;
            mIsPending = false;
        }

        setUI();

        return;
    }

    private void setUI() {
        refreshDrawableState();
        mImage.setColorFilter(mText.getCurrentTextColor());
    }


}
