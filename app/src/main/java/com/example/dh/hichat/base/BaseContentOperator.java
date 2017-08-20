package com.example.dh.hichat.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by DH on 2017/8/10.
 */

public interface BaseContentOperator {
    View addContentView(LayoutInflater mInflater, FrameLayout container);

    void initAddContentView(View view);
}
