package com.example.latte.core.net.download;

import android.os.AsyncTask;

import com.example.latte.core.net.RestCreator;
import com.example.latte.core.net.callBack.IError;
import com.example.latte.core.net.callBack.IFailure;
import com.example.latte.core.net.callBack.IProgress;
import com.example.latte.core.net.callBack.IRequest;
import com.example.latte.core.net.callBack.ISuccess;
import com.example.latte.core.util.file.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.WeakHashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/23.
 */

/**
 * 下载助手类  构建出来后直接调用handleDownload方法启动下载
 * 该类用作断点续传
 */
public class DownloadWithHeaderHandler {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IProgress PROGRESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final File FILE;

    public DownloadWithHeaderHandler(String url,
                                     WeakHashMap<String, Object> params,
                                     IRequest request,
                                     String downloadDir,
                                     String extension,
                                     String name,
                                     File file,
                                     ISuccess success,
                                     IProgress progress,
                                     IFailure failure,
                                     IError error) {
        this.URL = url;
        this.REQUEST = request;
        this.PARAMS = params;
        this.SUCCESS = success;
        this.PROGRESS = progress;
        this.FAILURE = failure;
        this.ERROR = error;

        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        //这里先不做处理，有需要再加（即更改extension默认值）
        if (extension == null || extension.equals("")) {
            extension = "";
        }

        if (file == null) {
            //即file优先，name其次
            if (name == null) {
                file = FileUtil.createFileByTime(downloadDir, extension.toUpperCase(), extension);
            } else {
                file = FileUtil.createFile(downloadDir, name);
            }
        }
        this.FILE = file;
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
                            long contentLength = response.body().contentLength();
                            long downloadedLength = 0;
                            if (FILE.exists()) {
                                downloadedLength = FILE.length();
                            }
                            if (downloadedLength >= contentLength) {
                                //已下载的大于要下载的，删掉已下载的
                                FILE.delete();
                                downloadedLength = FILE.length();
                            }

                            final String range = "bytes=" + downloadedLength + "-";
                            download(range);
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

    private void download(String range) {
        RestCreator.getRestService().downloadWithHeader(range, URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            //@Streaming 是一边下载一边写入，所以一定要用异步方法，否则报异常。不加@Streaming可能会内存溢出
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS, PROGRESS);
                            //不用execute执行，因为这是队列的形式，executeOnExecutor表示以线程池的方式，即不用等到前面的都结束了再执行
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, FILE, responseBody);

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
