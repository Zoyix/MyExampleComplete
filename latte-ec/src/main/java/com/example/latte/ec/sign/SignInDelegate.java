package com.example.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.example.latte.core.app.ConfigKeys;
import com.example.latte.core.app.Latte;
import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.net.RestClient;
import com.example.latte.core.net.callBack.ISuccess;
import com.example.latte.core.util.log.LatteLogger;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4.
 */

/**
 * 登录页面
 * <p>
 * 该fragment依赖的Activity必须实现ISignListener接口，做一些启动其它页面的操作
 */
public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    /**
     * 表示Activity必须实现ISignListener接口
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        } else {
            throw new IllegalStateException("The root activity must implement an interface that name of 'ISignListener'.");
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkFrom()) {
            String baseUrl = (String) Latte.getConfigurations().get(ConfigKeys.API_HOST);
            RestClient.builder()
                    .url(baseUrl + "user_profile.php")
                    .params("email", mEmail.toString())
                    .params("password", mPassword.toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignIn(response, mISignListener);
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat() {
        //TODO 微信登录暂时不集成
//        LatteWeChat.getInstance().onSignSuccess(new IWeChatSignInCallback() {
//            @Override
//            public void onSignInSuccess(String userInfo) {
//                Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
//            }
//        }).signIn();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }


    /**
     * 以下是辅助方法
     */


    /**
     * 本地检查输入正确与否
     *
     * @return
     */
    private boolean checkFrom() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }


}
