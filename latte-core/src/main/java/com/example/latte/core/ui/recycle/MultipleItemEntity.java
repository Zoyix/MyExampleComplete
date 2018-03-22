package com.example.latte.core.ui.recycle;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/19.
 */

/**
 *  每个内容的Entity，并返回要相应视图的type,用来存放数据的。
 *  MultiItemEntity是三方库里提供的
 */
public class MultipleItemEntity implements MultiItemEntity {

//    ReferenceQueue中保存的对象是已经失去了它所软引用的对象的Reference对象  http://blog.51cto.com/alinazh/1276173

    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUENE = new ReferenceQueue<>();
    //MULTIPLE_FIELDS是通过他的软引用来存取数据，用来存放每一个Entity的数据（仅数据无视图）
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS, ITEM_QUENE);

    /**
     * 返回的数据是哪个类型的，选择相应的视图渲染(在开源库里已经封装)
     * @return
     */
    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    /**
     * 给builder用的
     * @param fields
     */
    MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        //是不是应该清一下MULTIPLE_FIELDS？
        //不用，每次MultipleItemEntity都是new出来新的实例，为什么要清？
        FIELDS_REFERENCE.get().putAll(fields);
    }



    /**
     * 以下就一些set 和 get 方法
     */


    /**
     * 根据key返回域
     * @param key  TODO 研究一下 泛型的好处
     * @param <T> 泛型，泛型不但可以提高代码的准确度，可以把很多错误扼杀在编译期，而且可以真正提高代码效率
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key){
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    //TODO ?代表啥 代表并不知道类型？
    public final  LinkedHashMap<?,?> getFields(){
        return FIELDS_REFERENCE.get();
    }

    //TODO 优化：其实应该不返回，是void以免和对应的builder里的方法混淆
    //TODO 可以解决：putll方法会不会覆盖键一样的值？
    /**
     * 用来重设Entity里的值
     * 其实和MultipleEntityBuilder中的setField其实是一样的，主要当要新加或重设数据的时候方便点
     */
    public final MultiItemEntity setField(Object key, Object value){
        FIELDS_REFERENCE.get().put(key,value);
        return this;
    }


    public static MultipleEntityBuilder builder(){
        return new MultipleEntityBuilder();
    }
}
