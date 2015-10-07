package com.callbell.callbell.dagger;


import com.callbell.callbell.data.ServerMessageToJSONTranslator;
import com.callbell.callbell.service.ServerEndpoints;
import com.callbell.callbell.service.tasks.PostRequestTask;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module ( injects = {
        PostRequestTask.class
}
)
public class ServiceModule {

    @Provides
    ServerMessageToJSONTranslator provideServerMessageToJSONTranslator() {
        return new ServerMessageToJSONTranslator();
    }

    @Provides @Singleton
    ServerEndpoints provideServerEndpoints() {
        return new ServerEndpoints();
    }
}
