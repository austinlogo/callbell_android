package com.callbell.callbell.dagger;

import android.app.Application;

import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.presentation.bed.PlanOfCareFragment;
import com.callbell.callbell.presentation.bed.StaffFragment;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.presentation.station.StationActivity;
import com.callbell.callbell.presentation.station.StationFragment;
import com.callbell.callbell.service.RegistrationIntentService;
import com.callbell.callbell.service.tasks.PostRequestWithCallbackTask;
import com.callbell.callbell.util.PrefManager;
import com.callbell.callbell.data.ServerMessageToJSONTranslator;
import com.callbell.callbell.CallBellApplication;
import com.callbell.callbell.presentation.bed.BedModeActivity;
import com.callbell.callbell.presentation.login.LoginActivity;
import com.callbell.callbell.presentation.login.LoginFragment;
import com.callbell.callbell.service.ServerEndpoints;
import com.callbell.callbell.service.tasks.PostRequestTask;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module ( injects = {
        MessageRouting.class,
        BedModeActivity.class,
        LoginActivity.class,
        PostRequestTask.class,
        LoginFragment.class,
        StaffFragment.class,
        EnableSuperUserDialog.class,
        PlanOfCareFragment.class,
        StationFragment.class,
        StationActivity.class,
        PostRequestWithCallbackTask.class,
        RegistrationIntentService.class,

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

    @Provides
    ServerMessageToJSONTranslator provideServerMessageToJSONTranslator() {
        return new ServerMessageToJSONTranslator();
    }

    @Provides @Singleton
    ServerEndpoints provideServerEndpoints() {
        return new ServerEndpoints();
    }

}
