package com.shenghaiyang.wenews.view.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenghaiyang.wenews.R;

import java.util.List;

/**
 * RecyclerView的简单adapter
 * <p/>
 * Created by shenghaiyang
 *
 * @param <T> 数据List中的泛型类
 */
public abstract class QuickRecyclerAdapter<T> extends RecyclerView.Adapter<QuickRecyclerAdapter.QuickViewHolder> {

    /**
     * Context实例
     */
    private Context mContext;
    /**
     * 数据List
     */
    private List<T> mList;
    /**
     * item布局的资源id
     */
    private int mLayoutResId;

    /**
     * 构造方法
     *
     * @param context     Context实例
     * @param list        数据List
     * @param layoutResId item的布局资源id
     */
    public QuickRecyclerAdapter(Context context, List<T> list, @LayoutRes int layoutResId) {
        mContext = context;
        mList = list;
        mLayoutResId = layoutResId;
    }

    @Override
    public QuickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutResId, parent, false);
        return new QuickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuickRecyclerAdapter.QuickViewHolder holder, int position) {
        T item = mList.get(position);
        bindViews(holder, item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 抽象此方法用于给子类提供holder以及item实例
     *
     * @param holder QuickViewHolder的实例
     * @param item   item，T类型
     */
    protected abstract void bindViews(QuickViewHolder holder, T item);

    /**
     * 获取Context
     *
     * @return
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * QuickRecyclerAdapter的ViewHolder
     */
    public class QuickViewHolder extends RecyclerView.ViewHolder {

        /**
         * itemView
         */
        private View parent;
        /**
         * 统一用于管理view
         */
        private SparseArray<View> viewSparseArray = new SparseArray<>();

        /**
         * 构造方法
         *
         * @param itemView itemView
         */
        QuickViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
        }

        /**
         * 获取View，替代findViewById
         *
         * @param resId item中控件的资源id
         * @return View实例
         */
        public View getView(@IdRes int resId) {
            View view = viewSparseArray.get(resId);
            if (view == null) {
                view = parent.findViewById(resId);
                viewSparseArray.put(resId, view);
            }
            return view;
        }

        /**
         * 设置TextView内容
         *
         * @param resId TextView资源id
         * @param text  TextView设置的文本
         * @return 该QuickViewHolder的对象，便于保持链式
         */
        public QuickViewHolder setText(@IdRes int resId, String text) {
            TextView textView = (TextView) getView(resId);
            textView.setText(text);
            return this;
        }

        /**
         * 设置ImageView内容
         *
         * @param resId       ImageView资源id
         * @param drawableRes drawable资源id
         * @return 该QuickViewHolder的对象，便于保持链式
         */
        public QuickViewHolder setImageResource(@IdRes int resId, @DrawableRes int drawableRes) {
            ImageView imageView = (ImageView) getView(resId);
            imageView.setImageResource(drawableRes);
            return this;
        }

        /**
         * 加载网络图片资源到ImageView
         *
         * @param resId ImageView资源id
         * @param url   网络图片资源的URL
         * @return 该QuickViewHolder的对象，便于保持链式
         */
        public QuickViewHolder loadImage(@IdRes int resId, String url) {
            if (url != null && url.length() > 0) {
                ImageView imageView = (ImageView) getView(resId);
                Glide.with(mContext)
                        .load(url)
                        .placeholder(android.R.color.transparent)
                        .error(android.R.color.transparent)
                        .centerCrop()
                        .into(imageView);
            }
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param resId    View的资源id
         * @param listener View的点击事件
         * @return 该QuickViewHolder的对象，便于保持链式
         */
        public QuickViewHolder setOnClickListener(@IdRes int resId, View.OnClickListener listener) {
            getView(resId).setOnClickListener(listener);
            return this;
        }
    }
}
