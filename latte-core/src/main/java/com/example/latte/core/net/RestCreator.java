package com.example.latte.core.net;

import com.example.latte.core.app.ConfigKeys;
import com.example.latte.core.app.Latte;
import com.example.latte.core.net.rx.RxRestServise;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by zhouyixin on 2017/11/14.
 */

public class RestCreator {
    /**
     *  内部类构建好Retrofit
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 对okHttpClient的处理，一些设置，如连接超时时间，拦截器等
     */
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigKeys.INTERCEPTOR);

        //将拦截器添加进来
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Service接口
     */
    private static final class RestServiceHolder {
        private static final RestServise REST_SERVISE = RetrofitHolder.RETROFIT_CLIENT.create(RestServise.class);
    }

    public static RestServise getRestService() {
        return RestServiceHolder.REST_SERVISE;
    }

    /**
     * RxService接口
     */
    private static final class RxRestServiceHolder {
        private static final RxRestServise REST_SERVISE = RetrofitHolder.RETROFIT_CLIENT.create(RxRestServise.class);
    }

    public static RxRestServise getRxRestService() {
        return RxRestServiceHolder.REST_SERVISE;
    }

}
