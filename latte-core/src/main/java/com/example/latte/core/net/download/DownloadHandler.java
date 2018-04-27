package com.example.latte.core.net.download;

import android.os.AsyncTask;


import com.example.latte.core.net.RestCreator;
import com.example.latte.core.net.callBack.IError;
import com.example.latte.core.net.callBack.IFailure;
import com.example.latte.core.net.callBack.IProgress;
import com.example.latte.core.net.callBack.IRequest;
import com.example.latte.core.net.callBack.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/23.
 */

/**
 * 下载助手类  构建出来后直接调用handleDownload方法启动下载
 * EXTENSION和NAME传一个就行。都传用NAME。
 */
public class DownloadHandler {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IProgress PROGRESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String url,
                           WeakHashMap<String, Object> params,
                           IRequest request,
                           String download_dir,
                           String extension,
                           String name,
                           ISuccess success,
                           IProgress progress,
                           IFailure failure,
                           IError error) {
        this.URL = url;
        this.REQUEST = request;
        this.PARAMS = params;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.PROGRESS = progress;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            //@Streaming 是一边下载一边写入，所以一定要用异步方法，否则报异常。不加@Streaming可能会内存溢出
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS, PROGRESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //这里一定要注意判断，否则文件下载不全
                            //TODO 问老师或者以后解决：还是不清楚为什么会出现文件下载不全？
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
