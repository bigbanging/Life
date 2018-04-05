package com.gwl.life.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwl.life.R;
import com.gwl.life.bean.WeChatArticleBean;
import com.gwl.life.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.adapter
 * 文件名： WeChatArticleAdapter
 * 创建者： GWL
 * 创建时间：2018/4/4 17:08
 * 描述  ： TODO
 */
public class WeChatArticleAdapter extends BaseAdapter {
    private Context context;
    private List<WeChatArticleBean> weChatArticleBeanList;
    private LayoutInflater inflater;
    private WeChatArticleBean weChatArticle;

    public WeChatArticleAdapter(Context context, List<WeChatArticleBean> weChatArticleBeanList) {
        this.context = context;
        this.weChatArticleBeanList = weChatArticleBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override

    public int getCount() {
        return weChatArticleBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return weChatArticleBeanList.get(position);
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
            convertView = inflater.inflate(R.layout.wechat_article_item, null);
            holder.iv_weChat_article_img = convertView.findViewById(R.id.iv_weChat_article_img);
            holder.tv_weChat_article_title = convertView.findViewById(R.id.tv_weChat_article_title);
            holder.tv_weChat_article_source = convertView.findViewById(R.id.tv_weChat_article_source);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        weChatArticle = weChatArticleBeanList.get(position);
        //设置图片
//        Picasso.with(context).load(weChatArticle.getFirstImg()).into(holder.iv_weChat_article_img);
        PicassoUtils.loadImageViewSize(context,weChatArticle.getFirstImg(),300,160,holder.iv_weChat_article_img);
        holder.tv_weChat_article_title.setText(weChatArticle.getTitle());
        holder.tv_weChat_article_source.setText(weChatArticle.getSource());
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_weChat_article_img;
        private TextView tv_weChat_article_title;
        private TextView tv_weChat_article_source;
    }
}
