package com.example.myexample;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.example.latte.core.activities.ProxyActivity;
import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.ec.sign.ISignListener;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements ISignListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //引入包后（别忘记project的gradle文件中加入maven），一句话实现状态栏隐藏
        StatusBarCompat.translucentStatusBar(this, true);
//        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ffff8800"),99);
    }


    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }

    @Override
    public void onSignInSuccess() {

    }

    @Override
    public void onSignUpSuccess() {

    }
}
