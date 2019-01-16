package com.example.dh.hichat.ui.fragment;

import android.widget.TextView;

import com.example.dh.hichat.base.BaseFragment;

/**
 * Created by DH on 2017/8/10.
 */

public class ContactFragment extends BaseFragment {
    @Override
    public void setDefaultTitle(TextView tvTitle) {
        tvTitle.setText("通讯录");
    }

//    @Override
//    public void setEmptyView(ImageView ivEmpty, TextView tvInfo) {
//        ivEmpty.setImageResource(R.drawable.ic_guest_contact_empty);
//        tvInfo.setText("可以让附近的人互动");
//    }
}
