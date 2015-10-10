package com.callbell.callbell.presentation.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.callbell.callbell.R;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentCallback {

    private static final String TAG = LoginActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);


        LoginFragment loginFragment = LoginFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.base_fragment_container, loginFragment)
                .commit();
    }
}
