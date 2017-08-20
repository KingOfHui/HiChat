package com.example.dh.hichat.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dh.hichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DH on 2017/8/10.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.content)
    public FrameLayout content;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);
        View view = getView(getLayoutInflater());
        addContent(view);
        initContent(view);
    }

    //设置标题的内容
    public void setTitle(String title) {
        tvTitle.setText(title);
    }
    //
    public abstract View getView(LayoutInflater mInflater);
    //添加内容
    public void addContent(View view){
        content.addView(view);
    };
    //初始化内容
    public abstract void initContent(View view);


    @OnClick(R.id.ib_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
