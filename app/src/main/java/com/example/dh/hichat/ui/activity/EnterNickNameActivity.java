package com.example.dh.hichat.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseActivity;
import com.example.dh.hichat.wrap.SimpleTextWatcher;

/**
 * Created by DH on 2017/8/10.
 */

public class EnterNickNameActivity extends BaseActivity {
    private android.support.design.widget.TextInputEditText etnickname;
    private android.widget.Button btnext;
    private android.widget.LinearLayout activityregister;

    @Override
    public View addContent(LayoutInflater mInflater, FrameLayout content) {
        View view = mInflater.inflate(R.layout.activity_enter_nick_name, content, true);
        activityregister = (LinearLayout) view.findViewById(R.id.activity_register);
        btnext = (Button) view.findViewById(R.id.bt_next);
        etnickname = (TextInputEditText) view.findViewById(R.id.et_nickname);
        return view;
    }

    @Override
    public void initContent(View view) {
        //设置监听
        btnext.setOnClickListener(this);
        etnickname.addTextChangedListener(new SimpleTextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (TextUtils.isEmpty(s.toString())) {
                    btnext.setEnabled(false);
                }else {
                    btnext.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                Intent intent=new Intent(this,PersonalInfoActivity.class);
                String nickName = etnickname.getText().toString().trim();
                intent.putExtra("nickName", nickName);
                startActivity(intent);
                break;
            case R.id.ib_back:
                showExitDialog();
                break;
            default :

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示:")
                .setMessage("确定要放弃注册吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }
}
