package com.gwl.life.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gwl.life.R;
import com.gwl.life.bean.LogisticData;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.adapter
 * 文件名： LogisticInformationAdapter
 * 创建者： GWL
 * 创建时间：2018/4/3 13:28
 * 描述  ： TODO
 */
public class LogisticInformationAdapter extends BaseAdapter {
    //上下文
    private Context context;
    //数据源
    private List<LogisticData> dataList = new ArrayList<>();
    //布局解析器
    private LayoutInflater inflater;
    private LogisticData data;

    public LogisticInformationAdapter(Context context, List<LogisticData> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override

    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //第一次加载
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.logistic_data_item, null);
            holder.tv_dateTime = convertView.findViewById(R.id.tv_dataTime);
            holder.tv_remark = convertView.findViewById(R.id.tv_remark);
            holder.tv_zone = convertView.findViewById(R.id.tv_zone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        data = dataList.get(position);
        holder.tv_remark.setText(data.getRemark());
        holder.tv_zone.setText(data.getZone());
        holder.tv_dateTime.setText(data.getDatetime());

        return convertView;
    }
    class ViewHolder{
        TextView tv_remark;
        TextView tv_zone;
        TextView tv_dateTime;
    }
}
