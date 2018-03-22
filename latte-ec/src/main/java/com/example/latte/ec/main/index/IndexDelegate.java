package com.example.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.latte.core.app.Latte;
import com.example.latte.core.bottom.BottomItemDelegate;
import com.example.latte.core.ui.recycle.decoration.BaseDecoration;
import com.example.latte.core.ui.refresh.RefreshHandler;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/13.
 */

public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener, SwipeRefreshLayout.OnRefreshListener {
    //TODO 有bug，为什么无网的时候不能下拉刷新？

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;

    private RefreshHandler mRefreshHandler = null;


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //设置下拉刷新
        mRefreshLayout.setOnRefreshListener(this);

        mRefreshHandler = RefreshHandler.create(mRecyclerView, new IndexDataConverter());

        //输入框焦点改变时触发
        mSearchView.setOnFocusChangeListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecycleView();
        mRefreshHandler.firstPage("index.php");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //输入框焦点改变时触发
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        //设置下拉刷新开始了
        mRefreshLayout.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO 进行一些网络请求
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    /**
     * 以下是辅助方法和一些测试方法
     */


    /**
     * 初始化下拉刷新的旋转动画
     */
    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        //第一个参数是会由大变小由小变大，第二个是初始位置第三个是结束位置
        mRefreshLayout.setProgressViewOffset(true, 50, 200);
    }

    /**
     * 初始化布局
     */
    private void initRecycleView() {
        //TODO 网格布局什么样的？
        //4应该是每行共几份对应数据中的spanSize，即如果spanSize=4就代表占据整行，spanCount不能小于spanSize的最大值
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        //设置分割线
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));

    }


}
