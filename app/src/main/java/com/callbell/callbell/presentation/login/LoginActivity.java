package com.callbell.callbell.presentation.login;

import android.os.Bundle;
import android.view.WindowManager;

import com.callbell.callbell.R;
import com.callbell.callbell.presentation.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginFragment.LoginFragmentCallback {

    private static final String TAG = LoginActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginFragment loginFragment = LoginFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.base_fragment_container, loginFragment)
                .commit();
    }
}
