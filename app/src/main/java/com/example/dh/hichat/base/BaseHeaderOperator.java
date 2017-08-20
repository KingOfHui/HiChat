package com.example.dh.hichat.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DH on 2017/8/10.
 */

public interface BaseHeaderOperator {
    View addLeftHeader(ViewGroup llLeftHeader);
    View addRightHeader(ViewGroup llRightHeader);
    void setDefaultLeftHeader();
}
