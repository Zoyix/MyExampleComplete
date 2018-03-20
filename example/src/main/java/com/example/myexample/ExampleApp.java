package com.example.myexample;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.example.latte.core.app.Latte;
import com.example.latte.core.icon.FontEcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;


/**
 * Created by Administrator on 2017/11/13.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //TODO 以后解决，问老师，程哥，可以优化，试试, 有啥用, 可以解决，有时间看（有个“原始数据系列”可以解决下）,研究一下

        //手机热点：http://192.168.43.32:8080/RestServer/api/
        //我的电脑无线Ip：http://192.168.1.3:8080/RestServer/api/
        //公司ip: http://192.168.2.126:8080/RestServer/api/
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://192.168.2.126:8080/RestServer/api/")
                .configure();

        //AndroidUtilCode初始化
        Utils.init(this);


    }

}
