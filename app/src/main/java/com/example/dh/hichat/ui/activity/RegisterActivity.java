package com.example.dh.hichat.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseActivity;
import com.example.dh.hichat.utils.MyToast;
import com.example.dh.hichat.utils.ValidateUtils;
import com.example.dh.hichat.wrap.SimpleTextWatcher;

/**
 * Created by DH on 2017/8/21.
 */

public class RegisterActivity extends BaseActivity {
    private android.support.design.widget.TextInputEditText etusername;
    private android.support.design.widget.TextInputEditText etpwd;
    private android.support.design.widget.TextInputEditText etconfirmpwd;
    private android.widget.Button btregister;
    private TextView tvinfo;
    private android.widget.RelativeLayout rlloading;

    @Override
    public View addContent(LayoutInflater mInflater, FrameLayout frameLayout) {
        View view = mInflater.inflate(R.layout.activity_register, frameLayout, true);
        btregister = (Button) view.findViewById(R.id.bt_register);
        etconfirmpwd = (TextInputEditText) view.findViewById(R.id.et_confirm_pwd);
        etpwd = (TextInputEditText) view.findViewById(R.id.et_pwd);
        etusername = (TextInputEditText) view.findViewById(R.id.et_username);
        return view;
    }

    @Override
    public void initContent(View view) {
        btregister.setOnClickListener(this);
        //设置TextInputEditText输入内容改变的监听
        final SimpleTextWatcher textWatcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                //用户名,密码,确认密码是否为空
                String username = etusername.getText().toString().trim();
                String pwd = etpwd.getText().toString().trim();
                String confirmPwd = etconfirmpwd.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)) {
                    if (btregister.isEnabled()) {
                        btregister.setEnabled(false);
                    }
                } else {
                    if (!btregister.isEnabled()) {
                        btregister.setEnabled(true);
                    }
                }
            }
        };
        etusername.addTextChangedListener(textWatcher);
        etpwd.addTextChangedListener(textWatcher);
        etconfirmpwd.addTextChangedListener(textWatcher);

        //监听软键盘的操作
        etusername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (TextUtils.isEmpty(etusername.getText().toString())) {
                        MyToast.show(RegisterActivity.this, "用户名不能为空");
                    } else {
                        MyToast.show(RegisterActivity.this, etusername.getText().toString());
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_register:
                register();
                break;
            case R.id.ib_back:
                if (rlloading != null && rlloading.getVisibility() == View.VISIBLE) {
                    //点击后退图标和按钮不执行任何操作
                } else {
                    super.onClick(view);
                }
                break;
            default:

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (rlloading != null && rlloading.getVisibility() == View.VISIBLE) {
                return true;//中断事件
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void register() {
        //校验用户名和密码是否合法,密码和确认密码是否一致
        boolean isOK = validateData();
        if (isOK) {
            //显示加载界面
            addLoadingUI();
        }
    }

    private void addLoadingUI() {
        View view = getLayoutInflater().inflate(R.layout.loading, getRlRoot(), true);
        //初始化控件
        rlloading = (RelativeLayout) view.findViewById(R.id.rl_loading);
        tvinfo = (TextView) view.findViewById(R.id.tv_info);
        tvinfo.setText("正在注册");
    }

    private boolean validateData() {
        //用户名
        String username = etusername.getText().toString().trim();
        boolean validateUserName = ValidateUtils.validateUserName(username);
        if (!validateUserName) {
            MyToast.show(RegisterActivity.this, "用户名不合法");
            etusername.setFocusable(true);
            return false;
        }
        //密码
        String pwd = etpwd.getText().toString().trim();
        boolean validatePwd = ValidateUtils.validatePassword(pwd);
        if (!validatePwd) {
            MyToast.show(RegisterActivity.this, "密码不合法");
            etpwd.getText().clear();
            etconfirmpwd.getText().clear();
            etpwd.setFocusable(true);
            return false;
        }
        //确认密码
        String confirmPwd = etconfirmpwd.getText().toString().trim();
        if (!confirmPwd.equals(pwd)) {
            MyToast.show(RegisterActivity.this, "密码和确认密码不一致");
            etconfirmpwd.getText().clear();
            etconfirmpwd.setFocusable(true);
            return false;
        }
        return true;
    }
}
