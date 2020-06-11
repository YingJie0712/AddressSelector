package com.yingjie.addressselector.core;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yingjie.addressselector.R;
import com.yingjie.addressselector.api.OnSelectorListener;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class PopupU {

    /**
     *
     * @param context
     * @param mType
     * @param province
     * @param city
     * @param area
     * @return
     */
    public static Dialog showRegionView(Context context, int mType, final String province, final String city, final String area, final OnSelectorListener onSelectorListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogCommonStyle);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(R.layout.layout_region);
        RegionPopupWindow regionPopupWindow = window.findViewById(R.id.regionPpw);
        // 设置历史记录
        regionPopupWindow.setHistory(mType, province, city, area);
        // 设置右上角叉号监听
        regionPopupWindow.setOnForkClickListener(new RegionPopupWindow.OnForkClickListener() {
            @Override
            public void onForkClick() {
                dialog.dismiss();
            }
        });
        // 设置item监听，回调传回结果
        regionPopupWindow.setOnRpwItemClickListener(new RegionPopupWindow.OnRpwItemClickListener() {
            @Override
            public void onRpwItemClick(String selectedProvince, String selectedCity, String selectedArea) {
                onSelectorListener.onSelector(selectedProvince, selectedCity, selectedArea);
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = PhoneU.getScreenPix(context).widthPixels;// 宽为手机屏幕宽
        attributes.height = PhoneU.getScreenPix(context).heightPixels * 4/5;// 高为手机屏幕高的4/5
        window.setBackgroundDrawableResource(R.color.white);
        window.setAttributes(attributes);
        window.setWindowAnimations(R.style.AnimBottom);
        dialog.show();
        return dialog;
    }
}
