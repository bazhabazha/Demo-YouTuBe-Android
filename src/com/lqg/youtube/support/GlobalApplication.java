package com.lqg.youtube.support;

import android.app.Application;


/**
 * Created by LQG on 2014/12/4.
 */
public class GlobalApplication extends Application {

    static GlobalApplication instance;

    public static GlobalApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }


}
