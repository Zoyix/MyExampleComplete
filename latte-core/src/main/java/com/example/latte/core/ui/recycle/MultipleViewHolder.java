package com.example.latte.core.ui.recycle;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/12/20.
 */

/**
 * MultipleRecyclerAdapter的辅助类
 * BaseViewHolder用来存放一些视图信息，如getView方法得到视图，BaseMultiItemQuickAdapter的其中一个泛型
 * 有这个类可以在这里保存一下东西
 */
public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }

}
