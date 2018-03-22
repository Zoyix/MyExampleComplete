package com.example.latte.core.ui.refresh;

import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.latte.core.app.Latte;
import com.example.latte.core.net.RestClient;
import com.example.latte.core.net.callBack.ISuccess;
import com.example.latte.core.ui.recycle.DataConverter;
import com.example.latte.core.ui.recycle.MultipleRecyclerAdapter;

/**
 * Created by zhouyixin on 2017/12/17.
 */

/**
 * 刷新助手（需要实现分页的使用），实现下拉刷新和上拉加载的方法和分页，和具体页面的请求方法
 * TODO 这个我觉着多余的，把事情搞复杂了
 *
 * 该类其实就是为IndexDelegate服务的
 * 注意：下拉刷新是给SwipeRefreshLayout设置的，上拉加载是给MultipleRecyclerAdapter设置的
 */
public class RefreshHandler implements  BaseQuickAdapter.RequestLoadMoreListener {
    private final PagingBean BEAN;
    private final RecyclerView RECYCLEVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    /**
     * @param recyclerView       要被设置适配器的RecyclerView
     * @param converter
     * @param bean               //TODO 这个类用来干嘛？？没有被使用？
     */
    private RefreshHandler( RecyclerView recyclerView, DataConverter converter, PagingBean bean) {
        this.RECYCLEVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;

    }

    public static RefreshHandler create( RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler( recyclerView, converter, new PagingBean());
    }


    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        paging("refresh.php?index=");
    }


    /**
     * 显示第一页数据 TODO 第二页呢？
     * @param url
     */
    public void firstPage(String url) {
        //TODO 可以解决：设置了有什么用？
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        //设置上拉加载
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLEVIEW);
                        RECYCLEVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }


    /**
     * 以下是辅助方法
     */


    private void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();

        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        }else {
            Latte.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //postDelayed 是在主线程里运行的
                    RestClient.builder()
                            //TODO 可以解决，为什么有些是在url后面拼接参数，有些是以参数的形式传入params
                            .url(url+index)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                    //累加数量
                                    BEAN.setCurrentCount(mAdapter.getData().size());
                                    //上拉加载完成
                                    mAdapter.loadMoreComplete();
                                    BEAN.addIndex();
                                }
                            })
                            .build()
                            .get();
                }
            },1000);
        }
    }


}
