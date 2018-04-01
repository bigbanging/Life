package com.gwl.life.application;

import android.app.Application;

import com.gwl.life.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Created by litte on 2018/3/30.
 */

public class BaseApplication extends Application {

    //创建自己的Application
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, false);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
