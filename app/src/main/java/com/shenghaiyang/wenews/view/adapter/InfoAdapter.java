package com.shenghaiyang.wenews.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.shenghaiyang.wenews.R;
import com.shenghaiyang.wenews.constant.IntentConstant;
import com.shenghaiyang.wenews.entity.Info;
import com.shenghaiyang.wenews.view.activity.WebViewActivity;

import java.util.List;

/**
 * Created by shenghaiyang on 2016/1/6.
 */
public class InfoAdapter extends QuickRecyclerAdapter<Info.ResultEntity.ListEntity> {

    /**
     * 构造方法
     *
     * @param context     Context实例
     * @param list        数据List
     * @param layoutResId item的布局资源id
     */
    public InfoAdapter(Context context, List<Info.ResultEntity.ListEntity> list, @LayoutRes int layoutResId) {
        super(context, list, layoutResId);
    }

    @Override
    protected void bindViews(QuickViewHolder holder, final Info.ResultEntity.ListEntity item) {
        holder.setText(R.id.infoTitle, item.getTitle())
                .loadImage(R.id.infoImg, item.getFirstImg())
                .setOnClickListener(R.id.infoTitle, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), WebViewActivity.class);
                        intent.putExtra(IntentConstant.KEY_URL, item.getUrl());
                        getContext().startActivity(intent);
                    }
                });
    }
}
