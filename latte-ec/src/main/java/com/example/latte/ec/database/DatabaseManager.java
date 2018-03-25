package com.example.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2017/12/5.
 */

/**
 * 数据库管理类（二次封装）
 *
 * 创建方法：（参考UserProfile的创建方法）
 * 1.创建出表（参考UserProfile）
 * 2.在initDao方法中给该表对应的dao赋值
 * 3.底部创建返回该dao的方法
 *
 * 使用方法：（参考SignHandler类27：9）
 * 先在Application初始化
 * DatabaseManager.getInstance().getDao()
 *
 * 注意：版本控制要在使用GreenDao的module的gradle文件，加入greenDao配置
 * 我在简书上写了笔记：https://www.jianshu.com/p/ae954043f29a
 */
public class DatabaseManager {

    private DaoSession mDaoSession = null;
    private UserProfileDao mDao = null;

    public DatabaseManager init (Context context){
        initDao(context);
        return this;
    }

    /**
     * 初始化数据  主要的得到dao
     * @param context
     */
    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "fast_ec_db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

        mDao = mDaoSession.getUserProfileDao();
    }


    //单例模式
    private DatabaseManager(){
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }


    /**
     * 以下是返回dao的方法
     */

    public final UserProfileDao getDao(){
        return mDao;
    }
}
