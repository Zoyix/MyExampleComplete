package com.example.latte.core.ui.banner;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.latte.core.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/20.
 */

/**
 * Banner建造类
 *
 * 只需调用 BannerCreator.setDefault 即可实现对ConvenientBanner初始化
 */
public class BannerCreator {

    public static void setDefault(ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> banners,
                                  OnItemClickListener clickListener) {
        convenientBanner
                .setPages(new HolderCreator(), banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                //翻页效果
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000) //3秒换一张图
                .setCanLoop(true); //允许循环

    }
}