package com.example.latte.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.latte.core.app.Latte;


/**
 * Created by Administrator on 2017/11/17.
 */

/**
 * 获取屏幕宽高工具类
 */
public class DimenUtil {

    /**
     * 获取屏幕的宽
     * @return
     */
    public static int getScreenWidth() {
        //获取ApplicationContext资源
        final Resources resources = Latte.getApplicationContext().getResources();
        //获取手机屏幕参数  http://blog.csdn.net/yujian_bing/article/details/8264780
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高
     * @return
     */
    public static int getScreenHight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

}
