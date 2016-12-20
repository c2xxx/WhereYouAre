package com.chen.whereyouare.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chen.whereyouare.other.AnyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by ChenHui on 2016/11/11.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        ButterKnife.bind(this);
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 布局文件
     *
     * @return
     */
    protected abstract int getContentLayout();


    @Subscribe
    public void onEventMainThread(AnyEvent event) {

    }
}
