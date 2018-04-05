package com.gwl.life.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.gwl.life.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.utils
 * 文件名： PicassoUtils
 * 创建者： GWL
 * 创建时间：2018/4/4 22:13
 * 描述  ： Picasso封装
 */
public class PicassoUtils {
    //默认加载图片
    public static void loadImageView(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

    //加载默认大小的图片
    public static void loadImageViewSize(Context context,String url,int width,int height,ImageView imageView) {
        Picasso.with(context).load(url).resize(width, height).centerCrop().into(imageView);
    }

    //加载有默认图片的图片
    public static void loadImageViewholder(Context context, String url,int loaderImg,int errorImg,ImageView imageView) {
        Picasso.with(context).load(url).placeholder(loaderImg).error(errorImg).into(imageView);
    }

    //图片裁剪
    public class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "square()"; }
    }
}
