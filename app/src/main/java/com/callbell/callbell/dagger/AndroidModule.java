package com.callbell.callbell.dagger;

import android.app.Application;
import android.util.Log;

import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.data.PrefManager;
import com.callbell.callbell.presentation.CallBellApplication;
import com.callbell.callbell.presentation.DashboardActivity;
import com.callbell.callbell.presentation.bed.BedModeActivity;
import com.callbell.callbell.presentation.login.LoginActivity;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module ( injects = {
        DashboardActivity.class,
        MessageRouting.class,
        BedModeActivity.class,
        LoginActivity.class
}
)
public class AndroidModule {

    private final CallBellApplication application;

    public AndroidModule(CallBellApplication app) {
        application = app;
    }

    @Provides @Singleton
    PrefManager providePrefManager() {
        return new PrefManager(application);
    }

    @Provides @Singleton
    Application provideApplication() {
        return application;
    }

}
