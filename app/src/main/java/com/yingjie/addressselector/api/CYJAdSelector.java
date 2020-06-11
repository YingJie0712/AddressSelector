package com.yingjie.addressselector.api;

import android.content.Context;

import com.yingjie.addressselector.inner.SelectorFactory;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public class CYJAdSelector {

    public static void showSelector(Context context, int mType, String province, String city, String area, OnSelectorListener onSelectorListener) {
        SelectorFactory.create().showSelector(context, mType, province, city, area, onSelectorListener);
    }
}
