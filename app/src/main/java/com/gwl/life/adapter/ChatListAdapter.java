package com.gwl.life.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gwl.life.R;
import com.gwl.life.bean.ChatDataBean;

import java.util.List;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.adapter
 * 文件名： ChatListAdapter
 * 创建者： GWL
 * 创建时间：2018/4/4 9:45
 * 描述  ： 聊天适配器
 */
public class ChatListAdapter extends BaseAdapter {
    public static final int CHAT_LEFT_CONTENT = 1;
    public static final int CHAT_RIGHT_CONTENT = 2;

    private Context context;
    private List<ChatDataBean> chatDataBeanList;
    //布局解析器
    private LayoutInflater inflater;

    public ChatListAdapter(Context context, List<ChatDataBean> chatDataBeanList) {
        this.context = context;
        this.chatDataBeanList = chatDataBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override

    public int getCount() {
        return chatDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLeft holderLeft = null;
        ViewHolderRight holderRight = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            holderLeft = new ViewHolderLeft();
            holderRight = new ViewHolderRight();
            switch (type) {
                case CHAT_LEFT_CONTENT:
                    convertView = inflater.inflate(R.layout.chat_left, null);
                    holderLeft.tv_chat_left_content = convertView.findViewById(R.id.tv_chat_left_content);
                    convertView.setTag(holderLeft);
                    break;
                case CHAT_RIGHT_CONTENT:
                    convertView = inflater.inflate(R.layout.chat_right, null);
                    holderRight.tv_chat_right_content = convertView.findViewById(R.id.tv_chat_right_content);
                    convertView.setTag(holderRight);
                    break;
                default:
                    break;
            }
        } else switch (type) {
            case CHAT_LEFT_CONTENT:
                holderLeft = (ViewHolderLeft) convertView.getTag();
                break;
            case CHAT_RIGHT_CONTENT:
                holderRight = (ViewHolderRight) convertView.getTag();
                break;
            default:
                break;
        }
        //赋值
        ChatDataBean chatDataBean = chatDataBeanList.get(position);
        switch (type) {
            case CHAT_LEFT_CONTENT:
                holderLeft.tv_chat_left_content.setText(chatDataBean.getContent());
                break;
            case CHAT_RIGHT_CONTENT:
                holderRight.tv_chat_right_content.setText(chatDataBean.getContent());
                break;
            default:
                break;
        }
        return convertView;
    }
    //根据数据源的positiion来返回要显示的item

    @Override
    public int getItemViewType(int position) {
        ChatDataBean chatDataBean = chatDataBeanList.get(position);
        int type = chatDataBean.getType();
        return type;
    }

    //返回所有的layout数据
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    private class ViewHolderLeft{
        private TextView tv_chat_left_content;
    }
    private class ViewHolderRight{
        private TextView tv_chat_right_content;
    }
}
