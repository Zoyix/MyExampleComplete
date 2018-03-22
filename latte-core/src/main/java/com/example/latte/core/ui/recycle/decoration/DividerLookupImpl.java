package com.example.latte.core.ui.recycle.decoration;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * Created by Administrator on 2017/12/21.
 */


/**
 * 分割线样式设置（如：垂直和水平分别怎样实现）
 */
public class DividerLookupImpl implements DividerItemDecoration.DividerLookup {

    private final int COLOR;
    private final int SIZE;

    public DividerLookupImpl(int color, int size) {
        this.COLOR = color;
        this.SIZE = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }
}
