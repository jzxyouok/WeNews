package com.shenghaiyang.wenews.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextSwitcher;

import com.shenghaiyang.wenews.R;
import com.shenghaiyang.wenews.constant.IntentConstant;
import com.shenghaiyang.wenews.entity.Info;
import com.shenghaiyang.wenews.net.Client;
import com.shenghaiyang.wenews.view.adapter.InfoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.textSwitcher)
    TextSwitcher textSwitcher;
    @Bind(R.id.mainRecyclerView)
    RecyclerView mainRecyclerView;
    @Bind(R.id.mainRefresh)
    SwipeRefreshLayout mainRefresh;

    private InfoAdapter mAdapter;
    private List<Info.ResultEntity.ListEntity> mList;
    private int mCurrentPage = 1;
    private boolean mIsFirstTouchBottom = true;
    private final int PRELOAD_SIZE = 6;
    private final String GITHUB_LOGIN_URL = "https://github.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        textSwitcher.setVisibility(View.GONE);
        mList = new ArrayList<>();
        mAdapter = new InfoAdapter(this, mList, R.layout.item_main);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mainRecyclerView.setLayoutManager(manager);
        mainRecyclerView.addOnScrollListener(getOnBottomListener(manager));
        mainRecyclerView.setAdapter(mAdapter);
        refresh();
        mainRefresh.setColorSchemeResources(R.color.colorAccent, R.color.teal, R.color.brown);
        mainRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                mainRefresh.setRefreshing(false);
            }
        });
    }

    private void refresh() {
        Call<Info> call = Client.getRest().getData(1, 20);
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Response<Info> response) {
                List<Info.ResultEntity.ListEntity> list = response.body().getResult().getList();
                if (list != null) {
                    mList.clear();
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager manager) {
        return new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isBottom = manager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
                        mAdapter.getItemCount() - PRELOAD_SIZE;
                if (!mainRefresh.isRefreshing() && isBottom) {
                    if (mIsFirstTouchBottom) {
                        mainRefresh.setRefreshing(true);
                        mCurrentPage++;
                        loadData();
                        mainRefresh.setRefreshing(false);
                        mIsFirstTouchBottom = false;
                    } else {
                        mIsFirstTouchBottom = true;
                    }
                }
            }
        };
    }

    private void loadData() {
        Call<Info> call = Client.getRest().getData(mCurrentPage, 20);
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Response<Info> response) {
                List<Info.ResultEntity.ListEntity> list = response.body().getResult().getList();
                if (list != null) {
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_about_us:
                intent.setClass(this, AboutActivity.class);
                break;
            case R.id.action_github_login:
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(IntentConstant.KEY_URL, GITHUB_LOGIN_URL);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


}
