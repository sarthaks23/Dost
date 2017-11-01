package com.sanjyog.www.tabbedacttest;



/**
 * Created by Sarthak on 12/26/2015.
 */
public class MyApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        com.firebase.client.Firebase.setAndroidContext(getApplicationContext());


    }
}
