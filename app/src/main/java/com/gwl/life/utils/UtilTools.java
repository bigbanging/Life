package com.gwl.life.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by litte on 2018/3/30.
 */

public class UtilTools {

    //设置字体
    public static void setText(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }
}
