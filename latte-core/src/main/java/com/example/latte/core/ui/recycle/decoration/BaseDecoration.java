package com.example.latte.core.ui.recycle.decoration;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * BaseDecoration class
 * 基本分割线
 * 使用方法：RecyclerView.addItemDecoration(BaseDecoration.create())
 * 在RecyclerView中直接调用方法设置进去就行
 *
 * @author zoyix
 * @date 2017/12/21
 */
public class BaseDecoration extends DividerItemDecoration {
    private BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color, int size) {
        return new BaseDecoration(color, size);
    }
}
