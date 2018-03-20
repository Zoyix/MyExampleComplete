//package com.example.latte.core.net.rx;
//
//import com.dianbin.latte.util.storage.LattePreference;
//
//import java.io.IOException;
//
//import io.reactivex.Observable;
//import io.reactivex.functions.Consumer;
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * Created by Administrator on 2017/12/29.
// */
////TODO 这个拦截器为什么放这里？
//public class AddCookieInterceptor implements Interceptor {
//
//    @Override
//    public Response  intercept(Chain chain) throws IOException {
//
//        final Request.Builder builder = chain.request().newBuilder();
//        //TODO 问老师：这里不需要改变线程，为什么要用Rxjava2？  老师回答但还是不理解：https://coding.imooc.com/learn/questiondetail/37435.html
//        //使用just( )，将创建一个Observabl并自动调用onNext( )发射数据：
//        Observable
//                .just(LattePreference.getCustomAppProfile("cookie"))
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String cookie) throws Exception {
//                        //给原生API请求附带上WebView拦截下来的Cookie
//                        builder.addHeader("Cookie",cookie);
//                    }
//                });
//
////        String cookie = LattePreference.getCustomAppProfile("cookie");
////        builder.addHeader("Cookie",cookie);
//
//        return chain.proceed(builder.build());
//    }
//}
