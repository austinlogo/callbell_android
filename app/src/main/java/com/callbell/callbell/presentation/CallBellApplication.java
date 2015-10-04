package com.callbell.callbell.presentation;

import android.app.Application;

import com.callbell.callbell.dagger.AndroidModule;
import com.callbell.callbell.dagger.ServiceModule;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

public class CallBellApplication extends Application {

    private ObjectGraph mObjectGraph;

    private List<Object> moduleList;

    public void createModuleListIfNeeded() {
        if (moduleList != null) {
            return;
        }

        moduleList = new ArrayList<>();
        moduleList.add(new AndroidModule());
        moduleList.add(new ServiceModule());
    }

    private void createObjectGraphIfNeeded() {
        createModuleListIfNeeded();
        if (mObjectGraph == null && moduleList != null) {
            Object[] modules = moduleList.toArray();
            mObjectGraph = ObjectGraph.create(modules);
        }
    }

    public void inject(Object object) {
        createObjectGraphIfNeeded();
        mObjectGraph.inject(object);
    }

}
