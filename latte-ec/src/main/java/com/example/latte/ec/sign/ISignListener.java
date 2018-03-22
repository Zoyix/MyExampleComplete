package com.example.latte.ec.sign;

/**
 * Created by Administrator on 2017/12/6.
 */

/**
 * 和SignHandler的区别：
 * SignHandler是对登录成功再做了封装，将登录成功要做的一些事在框架里做了，如将数据存到数据库里等
 * 而本接口，是给启动登录注册的那个页面实现的，做一些如启动其它页面的操作，如本项目中启动EcBottomDelegate
 */
public interface ISignListener {

    /**
     * 登录成功的回调
     */
    void onSignInSuccess();

    /**
     * 注册成功的回调
     */
    void onSignUpSuccess();
}
