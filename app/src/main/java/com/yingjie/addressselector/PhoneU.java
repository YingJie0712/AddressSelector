package com.yingjie.addressselector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class PhoneU {

    /**
     * 获取手机大小（分辨率）
     *
     * @param context
     * @return
     */
    @NonNull
    public static DisplayMetrics getScreenPix(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        //获取手机窗口的Display 来初始化DisplayMetrics 对象
        //getManager()获取显示定制窗口的管理器。
        //获取默认显示Display对象
        //通过Display 对象的数据来初始化一个DisplayMetrics 对象
        windowManager.getDefaultDisplay().getMetrics(displaysMetrics);
        return displaysMetrics;
    }
}
