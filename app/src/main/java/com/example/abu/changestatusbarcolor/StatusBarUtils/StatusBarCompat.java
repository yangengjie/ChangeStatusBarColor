package com.example.abu.changestatusbarcolor.StatusBarUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
         * 所以可以根据这个原理，实现状态栏颜色的修改，其实Android5.0上也可以通过addView来实现修改状态栏的颜色
         * 但是在View上依然有个蒙版，我们在5.0上可以通过getWindow().setStatusBarColor()解决
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            View child = viewGroup.getChildAt(0);
            if (child != null)
                child.setFitsSystemWindows(true);
            View view = new View(activity);
            view.setBackgroundColor(statusBarColor);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            viewGroup.addView(view, layoutParams);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //由于WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS和getWindow().setStatusBarColor冲突
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(statusBarColor);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resouceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resouceId > 0)
            statusBarHeight = context.getResources().getDimensionPixelOffset(resouceId);
        return statusBarHeight;
    }

    public static void setTranslucentForImageView(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
