package com.example.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ec.database.DatabaseManager;
import com.example.latte.ec.database.UserProfile;

/**
 * Created by Administrator on 2017/12/5.
 */

/**
 * 注册登录辅助类  把注册登录信息存入数据库
 * 辅助一下SignInDelegate和SignUpDelegate使他们的类不至于太冗长（完全可以将这个类去掉，然后将方法写到各自的类里）
 *
 * TODO 这样做会不会导致静态区的方法太多？
 */
public class SignHandler {

    /**
     * 登录成功要做的事（存数据库）
     * @param response
     * @param signListener
     */
    public static void onSignIn(String response,ISignListener signListener){
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");

        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册并登录成功了
        AccountManager.setSignState(true);
        //回调登录成功的接口
        signListener.onSignInSuccess();
    }


    /**
     * 注册成功要做的事（存数据库）
     * @param response
     * @param signListener
     */
    public static void onSignUp(String response,ISignListener signListener){
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");

        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册并登录成功了
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }
}
