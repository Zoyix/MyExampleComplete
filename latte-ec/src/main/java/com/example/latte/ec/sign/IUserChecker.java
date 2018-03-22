package com.example.latte.ec.sign;

/**
 * Created by Administrator on 2017/12/6.
 */

/**
 * 用户登录与否的回调接口
 *
 * 调用AccountManager的checkAccount方法时要传入的接口实现，即判断完登录后各自要做的事
 */
public interface IUserChecker {

    /**
     * 若已登录做的事
     */
    void onSignIn();

    /**
     * 若没登录做的事
     */
    void onNotSignIn();
}
