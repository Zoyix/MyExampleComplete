package com.example.latte.core.util.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.core.app.Latte;


/**
 * Created by Zoyix on 2018/3/21
 */


/**
 * SharedPreferences工具类
 */
public final class LattePreference {

    /**
     * 提示:
     * Activity.getPreferences(int mode)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml
     * Context.getSharedPreferences(String name,int mode)生成name.xml
     */
    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(Latte.getApplicationContext());
    private static final String APP_PREFERENCES_KEY = "profile";

    //往preference加boolean
    public static void setAppFlag(String key, boolean flag) {
        getAppPreference()
                .edit()
                .putBoolean(key, flag)
                .apply();
    }

    public static boolean getAppFlag(String key) {
        return getAppPreference()
                .getBoolean(key, false);
    }

    //往preference加键值对 （String）
    public static void addCustomAppProfile(String key, String val) {
        getAppPreference()
                .edit()
                .putString(key, val)
                .apply();
    }

    public static String getCustomAppProfile(String key) {
        return getAppPreference().getString(key, "");
    }

    //根据key移除键值对
    public static void removeCustomAppProfilee(String key) {
        getAppPreference()
                .edit()
                .remove(key)
                .apply();
    }

    //清除所有缓存用
    public static void clearAppPreferences() {
        getAppPreference()
                .edit()
                .clear()
                .apply();
    }




    /**
     * 下面是为了方便使用才设计的
     */

    //添加“设置”
    public static void setAppProfile(String val) {
        getAppPreference()
                .edit()
                .putString(APP_PREFERENCES_KEY, val)
                .apply();
    }

    public static String getAppProfile() {
        return getAppPreference().getString(APP_PREFERENCES_KEY, null);
    }

    public static JSONObject getAppProfileJson() {
        final String profile = getAppProfile();
        return JSON.parseObject(profile);
    }

    //移除“设置”
    public static void removeAppProfile() {
        getAppPreference()
                .edit()
                .remove(APP_PREFERENCES_KEY)
                .apply();
    }


    /**
     * 内部使用了
     * @return
     */
    private static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }
}
