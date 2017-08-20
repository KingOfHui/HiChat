package com.example.dh.hichat.ui.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseFragment;

/**
 * Created by DH on 2017/8/10.
 */

public class ConversationFragment extends BaseFragment {
    @Override
    public void setDefaultTitle(TextView tvTitle) {
        tvTitle.setText("会话");
    }

    @Override
    public void setEmptyView(ImageView ivEmpty, TextView tvInfo) {
        ivEmpty.setImageResource(R.drawable.ic_guest_messag_empty);
        tvInfo.setText("可以让附近的人收发消息");
    }
}
