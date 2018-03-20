package com.example.latte.core.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by Administrator on 2017/11/17.
 */

/**
 * AVLoadingIndicatorView 的建造类
 * 调用create方法返回要的AVLoadingIndicatorView
 */
final class LoaderCreator {
    //暂时存放Indicator的hashMap，类似缓存下来，不用每次要用的时候都通过反射的方法去获取Indicator
    //因为这个库每次都是通过反射去获取样式（Indicator），大大的影响性能
    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    /**
     * 根据type创建AVLoadingIndicatorView
     * @param type 样式类型
     * @param context
     * @return
     */
    //加public和不加有什么区别?  不加表示只有在同一个包下才能访问的到
    static AVLoadingIndicatorView create(String type, Context context) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);


        if (LOADING_MAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }

        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    /**
     * 根据type返回AVLoadingIndicatorView要设置的Indicator（类似样式一样的东西）
     * @param name
     * @return
     */
    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            //得到包名
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            //com.wang.avi.indicators所有的样式都放在这个包下（看AVLoadingIndicatorView源码就知道了）
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
