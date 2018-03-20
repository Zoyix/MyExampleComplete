package com.example.latte.core.net.interceptors;

import android.support.annotation.RawRes;


import com.example.latte.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zhouyixin on 2017/11/27.
 */

public class DebugIntercepter extends BaseInterceptor {
    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugIntercepter(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    /**
     *  拦截若url中包含debugUrl（要拦截的）关键字的，返回一个想要返回的json
     *  否则原样返回
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)){
            return debugResponse(chain,DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }

    /**
     * 返回拦截后我们要返回的Response
     * @param chain
     * @param json 我们要返回的json字符串
     * @return
     */
    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("ok")
                .request(chain.request()) //使用原来的request
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    /**
     * 返回拦截后我们要返回的Response（debug的） getResponse是被该方法调用的
     * @param chain
     * @param rawId @RawRes注解表示这个int型的数据必须是在R下生成的具有唯一ID的资源
     * @return
     */
    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }


}
