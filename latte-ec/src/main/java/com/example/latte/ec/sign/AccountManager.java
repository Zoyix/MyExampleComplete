package com.example.latte.ec.sign;


/**
 * Created by Administrator on 2017/12/6.
 */

import com.example.latte.core.util.storage.LattePreference;

/**
 * 登录管理者 （辅助类）
 * 判断用户有没有登录，存一下登录的状态
 */
public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void  setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    private static boolean isSignIn(){
        return  LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    /**
     * 判断用户是否登录，每次启动图或者轮播图结束后调用
     * @param checker 用户登录与否的回调接口
     */
    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }
}
