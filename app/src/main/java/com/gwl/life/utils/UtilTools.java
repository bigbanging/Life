package com.gwl.life.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by litte on 2018/3/30.
 */

public class UtilTools {

    //设置字体
    public static void setText(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }

    //保存设置的用户头像到SharedUtils
    public static void putImgToShare(Context context, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步 将Bitmap，压缩成字节数组的输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二部 利用base64 将字节数组输出流壮话成String
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        String imgString = Base64.encodeToString(toByteArray, Base64.DEFAULT);
        //第三部：将imgString保存在shareUtils
        SPUtil.putString(context, "image_header", imgString);
    }

    //取出保存在SharedPreferences中的用户头像
    public static void getImgFromShare(Context context, ImageView imageView) {
        String imgSting = SPUtil.getString(context, "image_header", "");
        if (!imgSting.equals("")) {
            //利用Base64将imgString转换
            byte[] decode = Base64.decode(imgSting, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decode);
            //生成Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            imageView.setImageBitmap(bitmap);
        }
    }
}
