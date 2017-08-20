package com.example.dh.hichat.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dh.hichat.R;
import com.example.dh.hichat.ui.activity.EnterNickNameActivity;
import com.example.dh.hichat.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *  Created by DH on 2017/8/10.
 */

public abstract class BaseFragment extends Fragment {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.header_left)
    LinearLayout headerLeft;
    @BindView(R.id.header_right)
    LinearLayout headerRight;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.empty_ll)
    LinearLayout emptyLl;
    @BindView(R.id.content)
    FrameLayout content;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        //隐藏后退图标
        ibBack.setVisibility(View.GONE);
        setEmptyView(ivEmpty,tvInfo);
        setDefaultTitle(tvTitle);
        changeUI();
    }

    private void changeUI() {
        //标题
        showHeaderContent();
        //内容
        showBodyContent();
    }

    //显示Body的内容
    private void showBodyContent() {

    }

    //显示标题的内容
    private void showHeaderContent() {

    }

    //设置缺省的标题
    public abstract void setDefaultTitle(TextView tvTitle);


    public void hideBack() {
        ibBack.setVisibility(View.GONE);
    }

    //设置空布局的图片和文字
    public abstract void setEmptyView(ImageView ivEmpty, TextView tvInfo);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ib_back, R.id.bt_register, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                break;
            case R.id.bt_register:
                Intent intent=new Intent(getContext(),EnterNickNameActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_login:
                Intent intent1=new Intent(getContext(),LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
