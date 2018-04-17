package com.example.latte.core.net.rx;

import android.content.Context;


import com.example.latte.core.net.HttpMethod;
import com.example.latte.core.net.RestCreator;
import com.example.latte.core.ui.loader.LatteLoader;
import com.example.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/11/14.
 */

public class RxRestClient {

    private final WeakHashMap<String, Object> PARAMS;
    private final String URL;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;


    public RxRestClient(String url,
                        WeakHashMap<String, Object> params,
                        RequestBody body,
                        File file,
                        Context context,
                        LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestServise servise = RestCreator.getRxRestService();
        Observable<String> observable = null;


        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = servise.get(URL, PARAMS);
                break;
            case POST:
                observable = servise.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = servise.postRaw(URL, BODY);
                break;
            case PUT:
                observable = servise.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = servise.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = servise.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                //为什么要重新取一次不用service
                observable = servise.upload(URL, body);
                break;
            default:
                break;
        }

        return observable;
    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }


}
