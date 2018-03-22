package com.example.latte.core.bottom;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/12.
 */

/**
 * 底部导航栏和对应fragment的建造者
 */
public final class ItemBuilder {
    /**
     * 返回有序的导航栏和对应fragment的list
     */
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean,delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build(){
        return ITEMS;
    }
}
