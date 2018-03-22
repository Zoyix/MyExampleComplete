package com.example.latte.core.ui.recycle;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.latte.core.R;
import com.example.latte.core.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

/**
 * MultipleItemEntity对应的Adapter，继承BaseMultiItemQuickAdapter（三方的）
 * 不同的数据类型对应不同的视图，因此要在构造方法中加入对应的视图
 *
 * 其实是做了比较通用的Adapter，有TEXT、IMAGE、TEXT_IMAGE、BANNER
 * 很多情况下都是要自己封装过的，可做为父类再该基础上继续封装
 * 注意int值不要和已有的itemType重叠
 */
public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

    /**
     * 确保初始化一次Banner，防止重复Item加载
     */
    protected boolean mIsInitBanner = false;

    /**
     * 设置图片加载策略
     */
    private static final RequestOptions RECYCLER_OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    //TODO 可以解决：protected是不是只能在同一个包名下能访问到？
    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }



    /**
     * 将我们自己的BaseViewHolder传进去
     * TODO 问老师：已经有泛型了为啥还要传？
     * @param view
     * @return
     */
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;

        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_single));
                break;
            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_multiple));
                holder.setText(R.id.tv_multiple, text);
                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }

                break;
            default:
                break;
        }

    }

    /**
     * SpanSize是啥? 表示占据的大小，不能大于gridLayoutManager中设置的spanCount，否则报错
     * 是BaseQuickAdapter.SpanSizeLookup里的方法
     */
    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }


    /**
     * 以下是辅助方法
     */


    //哪里去选择这个布局？
    //实现了MultiItemEntity接口的类，有个getItemType（）方法，会返回该数据所需的视图类型，在框架内部
    //会自动匹配，并将视图信息存入BaseViewHolder
    /**
     * 初始化布局，将布局都加进去（通过addItemType方法）
     */
    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        //TODO 试试：高度改成match_parent试试
        addItemType(ItemType.BANNER, R.layout.item_multiple_bannner);
        //设置宽度监听
        //有啥用？ 看本类中的getSpanSize方法
        setSpanSizeLookup(this);

        //打开动画效果
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);

    }
}
