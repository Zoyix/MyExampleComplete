package com.example.latte.core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.latte.core.R;
import com.example.latte.core.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/17.
 */

/**
 * Loading动画加载类 是我们最终调用的类
 * 和LoaderCreator的区别：LoaderCreator.create出来的AVLoadingIndicatorView只是一个view，我们需要有
 * 控件去承载它，这里用了Dialog,并设置了Dialog的一些显示样式，在R.style.dialog（42：69）
 */
public class LatteLoader {
//TODO 有缺陷：可被点击取消掉
    //缩放比：让dialog可根据屏幕大小进行调整
    private static final int LOADER_SIZE_SCALE = 8;
    //loader根据屏幕的偏移量
    private static final int LOADER_OFFSET_SCALE = 10;

    //把所有Dialog都存进来，不需要时，只要遍历一下就可以关掉
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    //默认的loader样式
    private static String DEFAULT_LOADER = LoaderStyle.BallClipRotateIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    public static void showLoading(Context context, String type) {
        //注意：Dialog一定要传入当前Activity的context
        //第二个参数是对话框的xml样式
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        //获取设备（device）的宽高
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHight();

        //TODO 研究一下：这里得到的window是什么？不是在R.style.dialog中已经设置了全屏了么？
        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;

            //TODO  问老师：为什么一定要加偏移量？
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }


    //上下文和全局的有啥区别？  https://www.jianshu.com/p/583669632480
    //dialog 如果传ApplicationContext在WebView或者其它View上会报错。 凡是跟UI相关的，都应该使用Activity做为Context来处理。
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    //和dialog.dismiss()区别
                    //cancel会执行onCancel的回调，dismiss只是单纯的消失掉
                    dialog.cancel();
                }
            }
        }
    }

}

