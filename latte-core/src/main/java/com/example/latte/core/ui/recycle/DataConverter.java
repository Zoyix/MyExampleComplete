package com.example.latte.core.ui.recycle;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/19.
 */

/**
 * 数据处理接口（数据转换的约束）
 * 使用方法
 * 1.建一个类集成本接口，并实现convert方法，把解析后的数据放入ENTITIES中并返回。
 * 2.使用时new出该类的实例，先掉setJsonData将json数据设置进去
 * 3.调用covert即能解析，并返回解析后的数据
 *
 * 关于banners数据怎么解析，请参考IndexDataConverter类
 */
public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    /**
     * 处理传进来的json数据转化为ArrayList<MultipleItemEntity>返回出来
     * @return
     */
    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

}
