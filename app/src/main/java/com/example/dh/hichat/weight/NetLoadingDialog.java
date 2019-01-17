package com.example.dh.hichat.weight;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dh.hichat.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

/**
 * Created by $ myp on 16/1/19.
 */
public class NetLoadingDialog extends ProgressDialog {
    private Context mContext;
    private TextView mTitleName;
    private String msg = "";
    private View view;

    public NetLoadingDialog(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public NetLoadingDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        initView();
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private void initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.view_loading_dialog, null);
//        setContentView(view);
        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        mTitleName = (TextView) view.findViewById(R.id.txtTitleName);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        initValue();
    }

    @Override
    public void show() {
        super.show();
        setContentView(view);
    }

    private void initValue() {
        if (mTitleName != null) {
            if (msg != null && !"".equals(msg)) {
                mTitleName.setText(msg);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }
}
