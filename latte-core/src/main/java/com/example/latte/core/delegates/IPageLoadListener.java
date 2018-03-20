package com.example.latte.core.delegates;

/**
 * Created by Administrator on 2017/12/28.
 */

/**
 * 跳转接口，用于每次跳转前后处理的事情
 */
public interface IPageLoadListener {
    void onLoadStart();

    void onLoadEnd();
}
