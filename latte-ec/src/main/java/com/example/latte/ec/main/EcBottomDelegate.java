package com.example.latte.ec.main;

import android.graphics.Color;

import com.example.latte.core.bottom.BaseBottomDelegate;
import com.example.latte.core.bottom.BottomItemDelegate;
import com.example.latte.core.bottom.BottomTabBean;
import com.example.latte.core.bottom.ItemBuilder;
import com.example.latte.ec.main.index.IndexDelegate;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/13.
 */

/**
 * 最大的fragment包括底部导航栏的
 */
public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
