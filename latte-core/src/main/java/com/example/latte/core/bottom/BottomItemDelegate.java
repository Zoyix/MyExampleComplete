package com.example.latte.core.bottom;

import android.widget.Toast;

import com.example.latte.core.delegates.LatteDelegate;


/**
 * Created by Administrator on 2017/12/12.
 */

/**
 * 是每个底部导航栏对应fragment的基础类，双击返回键退出
 */
public abstract class BottomItemDelegate extends LatteDelegate {
    //再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
