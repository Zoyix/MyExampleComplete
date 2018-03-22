package com.example.myexample;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.latte.core.app.Latte;
import com.example.latte.ec.icon.FontEcModule;
import com.example.latte.ec.database.DatabaseManager;
import com.facebook.stetho.Stetho;
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
        //公司ip: http://192.168.2.107:8080/RestServer/api/
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://192.168.2.107:8080/RestServer/api/")
                .configure();

        //AndroidUtilCode初始化
        Utils.init(this);

        initStetho();
        DatabaseManager.getInstance().init(this);

    }


    /**
     * faceBook 数据库查看工具   原理 7-3 17：00
     * 配置好后在chrome中输入  chrome://inspect
     * 就能看到我们的项目了，点进去，在Resources一栏中可查看数据库
     */
    private void initStetho() {
        //都是官网上的做法，老师也没研究
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }

}
