package com.example.latte.core.ui.recycle;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/19.
 */

/**
 *  MultipleItemEntity的建造者
 *  可以写在静态内部类 TODO 可以解决：如果放在内部，一定要用静态的？
 */
public class MultipleEntityBuilder {
    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {
        //先清除之前的数据,因为FIELDS是静态内部类，所以要清一下
        //TODO 真的有用？
        FIELDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?, ?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build() {
        return new MultipleItemEntity(FIELDS);
    }
}
