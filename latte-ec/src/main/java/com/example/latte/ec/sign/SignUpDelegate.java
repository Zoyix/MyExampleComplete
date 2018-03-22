package com.example.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;


import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.net.RestClient;
import com.example.latte.core.net.callBack.ISuccess;
import com.example.latte.core.util.log.LatteLogger;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhouyixin on 2017/12/3.
 */

/**
 * 注册页面
 * <p>
 * 该fragment依赖的Activity必须实现ISignListener，做一些启动其它页面的操作
 */
public class SignUpDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;

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
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkFrom()) {
            RestClient.builder()
                    .url("http://10.41.69.60:8080/RestServer/api/User_profile.php")
                    .params("name", mName.toString())
                    .params("email", mEmail.toString())
                    .params("phone", mPhone.toString())
                    .params("password", mPassword.toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignUp(response, mISignListener);
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        getSupportDelegate().start(new SignInDelegate());
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
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        //TODO 检查电话号码是否正确，用正则补全
        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }


}
