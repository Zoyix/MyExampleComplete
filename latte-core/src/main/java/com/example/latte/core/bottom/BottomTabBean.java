package com.example.latte.core.bottom;

/**
 * Created by Administrator on 2017/12/12.
 */

/**
 * 底部导航栏存储每一个的bean类
 */
public final class BottomTabBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence ICON, CharSequence TITLE) {
        this.ICON = ICON;
        this.TITLE = TITLE;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
