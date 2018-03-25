package com.example.latte.ec.database;

import android.content.Context;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2017/12/5.
 */

/**
 * greenDao自己生成的DaoMaster.DevOpenHelper 是开发版的即每次都会把数据库清空
 * 所以我们要重新写个发布版的helper,辅助DatabaseManager类
 *
 * 注意：DaoMaster是建了表以后才生成的，即本类包括DatabaseManager都要在表建了以后再写
 */
public class ReleaseOpenHelper extends DaoMaster.OpenHelper {
    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    /**
     * 更新的时候被调用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //数据库更新时用
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        },UserProfileDao.class);
    }
}
