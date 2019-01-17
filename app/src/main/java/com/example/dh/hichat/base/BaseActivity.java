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

import com.example.dh.hichat.weight.NetLoadingDialog;
import com.example.dh.hichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  Created by DH on 2017/8/10.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);
        View view = addContent(getLayoutInflater(),content);
//        addContent(view);
        initContent(view);
        ibBack.setOnClickListener(this);
    }

    //设置标题的内容
    public void setTitle(String title) {

        tvTitle.setText(title);
    }

    //
    public RelativeLayout getRlRoot() {
        return rlRoot;
    }
    //
    public abstract View addContent(LayoutInflater mInflater, FrameLayout frameLayout);
    //初始化内容
    public abstract void initContent(View view);

   /* @OnClick(R.id.ib_back)
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:

                break;
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_back:
                finish();
                break;
        }
    }
    private NetLoadingDialog loadingDialog;
    public NetLoadingDialog getLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new NetLoadingDialog(this);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setCancelable(false);
        }
        return loadingDialog;
    }

    public void showLoading() {
        if (!getLoadingDialog().isShowing()) {
            getLoadingDialog().show();
        }
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
