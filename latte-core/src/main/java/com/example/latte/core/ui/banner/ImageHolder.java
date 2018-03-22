package com.example.latte.core.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by Administrator on 2017/12/20.
 */

/**
 * 构造每一幅图的ImageView,和每转动一次更新ImageView
 */
public class ImageHolder implements Holder<String> {

    private AppCompatImageView mImageView = null;

    /**
     * 设置图片加载策略
     */
    private static final RequestOptions RECYCLER_OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    /**
     * banner 每转动一次要更新的东西
     * @param context
     * @param position
     * @param data
     */
    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(context)
                .load(data)
                .apply(RECYCLER_OPTIONS)
                .into(mImageView);
    }
}
