package com.example.myexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.net.RestClient;
import com.example.latte.core.net.RestCreator;
import com.example.latte.core.net.callBack.IError;
import com.example.latte.core.net.callBack.IFailure;
import com.example.latte.core.net.callBack.ISuccess;
import com.example.latte.core.net.rx.RxRestClient;
import com.example.latte.core.ui.loader.LatteLoader;
import com.example.latte.core.ui.loader.LoaderStyle;
import com.example.latte.ec.database.DatabaseManager;
import com.example.latte.ec.database.UserProfile;
import com.example.latte.ec.main.EcBottomDelegate;
import com.example.latte.ec.sign.SignUpDelegate;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/11/14.
 */

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //测试Loader
//        LatteLoader.showLoading(getContext(), LoaderStyle.PacmanIndicator);

        //测试数据库
//        final UserProfile profile = new UserProfile(123456,"aaa","aaa","aaa","aaa");
//        DatabaseManager.getInstance().getDao().insert(profile);
//        Toast.makeText(_mActivity, "成功", Toast.LENGTH_SHORT).show();

        //登录页测试和多fragement框架测试
//         getSupportDelegate().start(new SignUpDelegate());

        //测试网络框架
//        onCallRxRxRestClient();
//        testRestClient();

        // EcBottomDelegate测试
         getSupportDelegate().start(new EcBottomDelegate());

    }

    private void testRestClient() {
        RestClient.builder()
                .url("index.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("HAHAHAHAHA", response);
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();

    }

    //TODO 测试方法，没啥软用
    void onCallRxGet() {
        final String url = "index.php";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();

        final Observable<String> observable = RestCreator.getRxRestService().get(url, params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //TODO 测试方法，没啥软用X2
    void onCallRxRxRestClient() {
        final String url = "index.php";
        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
