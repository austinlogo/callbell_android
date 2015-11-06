package com.callbell.callbell.dagger;

import android.app.Application;
import android.content.Context;

import com.callbell.callbell.business.MessageRouting;
import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.data.POCValues;
import com.callbell.callbell.presentation.bed.PlanOfCareFragment;
import com.callbell.callbell.presentation.bed.StaffFragment;
import com.callbell.callbell.presentation.dialogs.CallBellDialog;
import com.callbell.callbell.presentation.dialogs.EnableSuperUserDialog;
import com.callbell.callbell.presentation.dialogs.PainRatingDialog;
import com.callbell.callbell.presentation.station.StationActivity;
import com.callbell.callbell.presentation.station.StationFragment;
import com.callbell.callbell.presentation.title.TitleBarFragment;
import com.callbell.callbell.service.RegistrationIntentService;
import com.callbell.callbell.service.services.SocketService;
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
        //Data
        MessageRouting.class,
        PostRequestTask.class,
        POCValues.class,
        MedicationValues.class,
        PostRequestWithCallbackTask.class,
        RegistrationIntentService.class,

        //Activity
        BedModeActivity.class,
        LoginActivity.class,
        StationActivity.class,

        //Fragment
        LoginFragment.class,
        StaffFragment.class,
        PlanOfCareFragment.class,
        StationFragment.class,
        TitleBarFragment.class,

        //Dialog
        EnableSuperUserDialog.class,
        CallBellDialog.class,
        PainRatingDialog.class,

        //Service
        SocketService.class
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
    Context provideContext() {
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
