package com.example.latte.core.ui.recycle;

/**
 * Created by zhouyixin on 2017/12/19.
 */

/**
 * 存放视图类型
 */
public class ItemType {
    //这里没办法用枚举，因为这个三方库里存的是int值
    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int TEXT_IMAGE = 3;
    public static final int BANNER = 4;
    public static final int VERTICAL_MENU_LIST =5;
    public static final int SINGLE_BIG_IMAGE = 6;
}
