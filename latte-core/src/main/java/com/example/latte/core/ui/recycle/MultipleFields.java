package com.example.latte.core.ui.recycle;

/**
 * Created by Administrator on 2017/12/19.
 */

/**
 * MultipleItemEntity存放的数据类型枚举
 *
 * 科普：在安卓里枚举类并不是很好的选择，public static final 占用更少的内存
 * 但是枚举类可以使我们的代码更加工整
 */
public enum MultipleFields {
    ITEM_TYPE,
    TITLE,
    TEXT,
    IMAGE_URL,
    BANNERS,
    SPAN_SIZE,
    ID,
    NAME,
    TAG
}
