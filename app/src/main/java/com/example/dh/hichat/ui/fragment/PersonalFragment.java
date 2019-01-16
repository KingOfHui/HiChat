package com.example.dh.hichat.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseFragment;

/**
 * Created by DH on 2017/8/10.
 */

public class PersonalFragment extends BaseFragment {
    @Override
    public void setDefaultTitle(TextView tvTitle) {
        tvTitle.setText("关于彩神争霸");
    }

    @Override
    public void showBodyContent() {
        super.showBodyContent();
        View view = View.inflate(getContext(), R.layout.fragment_personal, null);
        content.addView(view);
    }
}
