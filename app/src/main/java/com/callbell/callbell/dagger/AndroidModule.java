package com.callbell.callbell.dagger;

import com.callbell.callbell.data.PrefManager;
import com.callbell.callbell.presentation.DashboardActivity;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module ( injects = {
        DashboardActivity.class
}
)
public class AndroidModule {

    @Provides @Singleton
    PrefManager providePrefManager() {
        return new PrefManager();
    }
}
