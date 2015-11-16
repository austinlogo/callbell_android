package com.callbell.callbell;

import android.app.Application;
import android.view.WindowManager;

import com.callbell.callbell.dagger.AndroidModule;
import com.callbell.callbell.data.MedicationValues;
import com.callbell.callbell.data.POCValues;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

public class CallBellApplication extends Application {

    private ObjectGraph mObjectGraph;

    private List<Object> moduleList;

    @Override
    public void onCreate() {
        super.onCreate();



        new POCValues(this);
        new MedicationValues(this);
    }

    public void createModuleListIfNeeded() {
        if (moduleList != null) {
            return;
        }

        moduleList = new ArrayList<>();
        moduleList.add(new AndroidModule(this));
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
