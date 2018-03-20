package com.example.latte.core.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by zhouyixin on 2017/11/27.
 */

/**
 * 基础拦截器类 用来获取参数
 * get方法要从url中获取参数
 * 如果说post的话要从它的请求体里获取参数
 * TODO 以后解决：为什么？
 */
public abstract class BaseInterceptor implements Interceptor {

    /**
     * 通过url获取所有的参数对
     * @param chain 拦截器里的内部类，存一些请求信息
     * @return
     */
    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }

        return params;
    }

    /**
     * 通过传入的key值获取参数
     * @param chain
     * @param key
     * @return
     */
    protected String getUrlParameters(Chain chain, String key) {
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    /**
     * 通过FormBody获取所有的参数对
     * @param chain
     * @return
     */
    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        final FormBody formBody = (FormBody) chain.request().body();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        int size = formBody.size();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }

    /**
     * 通过传入的key值获取参数
     * @param chain
     * @param key
     * @return
     */
    protected String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }

}
