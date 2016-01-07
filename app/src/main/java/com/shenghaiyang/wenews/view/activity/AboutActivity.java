package com.shenghaiyang.wenews.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shenghaiyang.wenews.R;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by shenghaiyang on 2016/1/6.
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.aboutToolbar)
    Toolbar aboutToolbar;
    @Bind(R.id.aboutCollapsing)
    CollapsingToolbarLayout aboutCollapsing;
    @Bind(R.id.aboutAppBar)
    AppBarLayout aboutAppBar;
    @BindString(R.string.app_name)
    String app_name;
    @BindString(R.string.share_content)
    String shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        aboutCollapsing.setTitle(app_name);
        setSupportActionBar(aboutToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, shareContent);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
