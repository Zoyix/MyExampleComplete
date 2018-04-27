package com.example.latte.core.net;

import android.content.Context;

import com.example.latte.core.net.callBack.IError;
import com.example.latte.core.net.callBack.IFailure;
import com.example.latte.core.net.callBack.IProgress;
import com.example.latte.core.net.callBack.IRequest;
import com.example.latte.core.net.callBack.ISuccess;
import com.example.latte.core.net.callBack.RequestCallbacks;
import com.example.latte.core.net.download.DownloadHandler;
import com.example.latte.core.net.download.DownloadWithHeaderHandler;
import com.example.latte.core.ui.loader.LatteLoader;
import com.example.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2017/11/14.
 */

/**
 * 发起网络请求类
 * 注意：正式用的时候，去掉所有loader的延迟关闭（因为测试的时候为了能看到，加了延迟）
 * 查找谁调用了stopLoading就能找到延迟
 */
public class RestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final IRequest REQUEST;
    //TODO 为什么RxJava下载不需要下面三个？
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final File FILE;
    private final ISuccess SUCCESS;
    private final IProgress PROGRESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    //dialog要用
    private final Context CONTEXT;


    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      String downloadDir,
                      String extension,
                      String name,
                      IRequest request,
                      ISuccess success,
                      IProgress progress,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.PROGRESS = progress;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestServise servise = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        //等待旋转框Loader开始
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = servise.get(URL, PARAMS);
                break;
            case POST:
                call = servise.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = servise.postRaw(URL, BODY);
                break;
            case PUT:
                call = servise.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = servise.putRaw(URL, BODY);
                break;
            case DELETE:
                call = servise.delete(URL, PARAMS);
                break;
            case UPLOAD:
//                首先我们需要明白在html中的enctype属性，
//                enctype：规定了form表单在发送到服务器时候编码方式。他有如下的三个值。
//                ①application/x-www-form-urlencoded。默认的编码方式。但是在用文本的传输和MP3等大型文件的时候，使用这种编码就显得 效率低下。
//                ②multipart/form-data 。 指定传输数据为二进制类型，比如图片、mp3、文件。
//                ③text/plain。纯文体的传输。空格转换为 “+” 加号，但不对特殊字符编码。
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                //不知道参数？看下源码就知道了
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                //TODO 可以解决：为什么要重新取一次？不用service？
                call = RestCreator.getRestService().upload(URL, body);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }

    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            //TODO 原始数据系列 为什么传入原始数据，params一定得为空？
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        //TODO 原始数据系列  put也能传原始数据系列
        //TODO 可以解决： put和post的区别？
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    /**
     * 调用download时，要么指定name 要么指定EXTENSION根据时间生成
     */
    public final void download() {
        new DownloadHandler(URL, PARAMS, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME, FILE, SUCCESS, PROGRESS, FAILURE, ERROR)
                .handleDownload();
    }

    /**
     * TODO 可以解决，断点续传，为什么这样进度显示会延缓，变得似乎是下载好了，才有进度
     */
    public final void downloadWithHeader() {
        new DownloadWithHeaderHandler(URL, PARAMS, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME, FILE, SUCCESS, PROGRESS, FAILURE, ERROR)
                .handleDownload();
    }

}
