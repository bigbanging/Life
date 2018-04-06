package com.gwl.life.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwl.life.R;
import com.gwl.life.bean.PictureData;
import com.gwl.life.utils.PicassoUtils;

import java.util.List;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.adapter
 * 文件名： PictureAdapter
 * 创建者： GWL
 * 创建时间：2018/4/5 11:34
 * 描述  ： 图片的适配器
 */
public class PictureAdapter extends BaseAdapter {
    private Context context;
    private List<PictureData> pictureDataList;
    private PictureData pictureData;
    private LayoutInflater inflater;
    private WindowManager windowManager;
    private int width;

    public PictureAdapter(Context context, List<PictureData> pictureDataList) {
        this.context = context;
        this.pictureDataList = pictureDataList;
        inflater = LayoutInflater.from(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return pictureDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return pictureDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.picture_item, null);
            holder.iv_picture = convertView.findViewById(R.id.iv_picture);
            holder.tv_public_time = convertView.findViewById(R.id.tv_picture_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        pictureData = pictureDataList.get(position);
        //设置发布时间
        holder.tv_public_time.setText(pictureData.getPublishedAt());//设置图片发布时间
        //设置图片
        PicassoUtils.loadImageViewSize(context,pictureData.getPictureUrl(),width/2,300,holder.iv_picture);
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_picture;
        private TextView tv_public_time;
    }
}
