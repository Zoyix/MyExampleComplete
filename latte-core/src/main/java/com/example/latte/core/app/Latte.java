package com.example.latte.core.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;


/**
 * Created by Administrator on 2017/11/13.
 */

/**
 * 统一的管理，所有的全局设置都在这个类里，Configurator只是它存储值的工具类，真正和用户交互是该类
 */
public final class Latte {
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigKeys.APPLICATION_CONFEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    //TODO 应该是已经被废弃
    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }


    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONFEXT);
    }


    //TODO 去掉所有调用的它的方法（去掉延迟）
    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

}
