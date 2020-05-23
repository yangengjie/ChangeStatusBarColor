package com.example.abu.changestatusbarcolor.StatusBarUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by ygj on 2020/5/23.
 */

public class StatusBarCompat {

    public static void setStatusBarColor(Activity activity, int statusBarColor) {
        /**
         * 从Android4.4开始通过如下方法将statusbar改成透明的，此时我们也就能在操作状态栏的区域了，
         * 所以可以根据这个原理，实现状态栏颜色的修改
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View child = viewGroup.getChildAt(0);
        if(child!=null)
            child.setFitsSystemWindows(true); //设置fitSystemWindows=true表示调整当前设置这个属性的View的padding去为我们的status_bar流出空间
        View view = new View(activity);
        view.setBackgroundColor(activity.getResources().getColor(statusBarColor));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        viewGroup.addView(view, layoutParams);
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resouceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resouceId > 0)
            statusBarHeight = context.getResources().getDimensionPixelOffset(resouceId);
        return statusBarHeight;
    }
}
