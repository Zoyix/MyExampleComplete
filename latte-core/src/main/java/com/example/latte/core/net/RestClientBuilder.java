package com.example.latte.core.net;

import android.content.Context;


import com.example.latte.core.net.callBack.IError;
import com.example.latte.core.net.callBack.IFailure;
import com.example.latte.core.net.callBack.IProgress;
import com.example.latte.core.net.callBack.IRequest;
import com.example.latte.core.net.callBack.ISuccess;
import com.example.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/11/15.
 */

/**
 * RestClient的建造者
 */
public class RestClientBuilder {
    private String mUrl = null;
    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IProgress mIProgress = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mIBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;

    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;

    public RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    /**
     * 下载后文件存放的目录
     *
     * @param dir
     * @return
     */
    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    /**
     * 后缀名
     *
     * @param extension
     * @return
     */
    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    /**
     * 传入原始数据 不知道干嘛的
     *
     * @param raw
     * @return
     */
    public final RestClientBuilder raw(String raw) {
        //TODO 有时间看：原始数据系列  有什么用？
        this.mIBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder progress(IProgress iProgress) {
        this.mIProgress = iProgress;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    /**
     * 添加指定样式的loader
     * 因为RestClient已经封装好了loader，网络请求时若要改变样式，传入就行
     *
     * @param context
     * @return
     */
    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    /**
     * 添加默认样式的loader
     *
     * @param context
     * @return
     */
    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotateIndicator;
        return this;
    }


    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mDownloadDir, mExtension, mName, mIRequest, mISuccess, mIProgress,mIFailure, mIError, mIBody, mFile, mContext, mLoaderStyle);
    }


}
