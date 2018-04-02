package com.gwl.life.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.gwl.life.R;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.view
 * 文件名： MyDialog
 * 创建者： GWL
 * 创建时间：2018/4/2 10:33
 * 描述  ： 自定义Dialog
 */
public class MyDialog extends Dialog {
    //定义模板
    public MyDialog(Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                layout,style, Gravity.CENTER);
    }

    //定义属性
    public MyDialog(Context context, int width, int height, int layout, int style, int gravity, int anim) {
        super(context, style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    //实例
    public MyDialog(Context context, int width, int height, int layout, int style, int gravity) {
        this(context,width,height,layout,style,gravity, R.style.dialog_anim_style);
    }

}
