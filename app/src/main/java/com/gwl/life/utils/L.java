package com.gwl.life.utils;

import android.util.Log;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.utils
 * 文件名： L
 * 创建者： GWL
 * 创建时间：2018/3/31 10:35
 * 描述  ： Logo封装类
 */
public class L {
    public static final boolean DEBUG = true;
    public static final String TAG = "日志信息";
    //日志类型IWDEF
    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }
    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }
    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }
    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
