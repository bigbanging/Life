package com.gwl.life.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.utils
 * 文件名： SPUtil
 * 创建者： GWL
 * 创建时间：2018/3/31 10:56
 * 描述  ： SharedPreferences工具类
 */
public class SPUtil {
    public static final String SHARED_FILE = "config";

    //put 存数据： 键 值
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    // get 取数据：键 默认值
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
    //put 存数据： 键 值
    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    // get 取数据：键 默认值
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }
    //put 存数据： 键 值
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    // get 取数据：键 默认值
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    //删除单个数据
    public static void delete(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //清除所有数据
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
