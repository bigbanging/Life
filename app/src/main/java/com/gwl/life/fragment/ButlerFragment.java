package com.gwl.life.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gwl.life.R;
import com.gwl.life.adapter.ChatListAdapter;
import com.gwl.life.bean.ChatDataBean;
import com.gwl.life.utils.L;
import com.gwl.life.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {

    //控件
    private ListView lv_chat_content;
    private EditText et_chat_content;
    private Button btn_chat_send;
    //数据源
    private List<ChatDataBean> chatDataBeanList = new ArrayList<>();
    //适配器
    ChatListAdapter adapter = null;

    public ButlerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_butler, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lv_chat_content = view.findViewById(R.id.lv_chat_content);
        et_chat_content = view.findViewById(R.id.et_chat_content);
        btn_chat_send = view.findViewById(R.id.btn_chat_send);
        btn_chat_send.setOnClickListener(this);
        adapter = new ChatListAdapter(getActivity(), chatDataBeanList);
        lv_chat_content.setAdapter(adapter);
        addLeftChatItem("你好，我是图灵机器人");
    }

    @Override
    public void onClick(View v) {
        /**
         * 逻辑
         * 1.获取输入框的内容
         * 2.判断是否为空
         * 3.判断长度不能大于150
         * 4.清空当前的输入框
         * 5.添加你输入的内容到right item
         * 6.发送给机器人请求返回内容
         * 7.拿到机器人的返回值之后添加在left item
         */
        //获取输入框的内容
        String chat_content = et_chat_content.getText().toString();
        if (!TextUtils.isEmpty(chat_content)) {
            if (chat_content.length() > 150) {
                Toast.makeText(getActivity(), "输入文本长度不能超过150", Toast.LENGTH_SHORT).show();
            }else {
                et_chat_content.setText("");
                addRightChatItem(chat_content);
                //发送给机器人请求返回数据
                String url = "http://www.tuling123.com/openapi/api";
                HttpParams params = new HttpParams();
                params.put("key", StaticClass.TURING_ROBOT_APP_KEY);
                params.put("info", chat_content);
                RxVolley.post(url, params, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        L.i("机器人响应"+t);
                        parseJson(t);
                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), "内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseJson(String robotRely) {
        try {
            JSONObject jsonObject = new JSONObject(robotRely);
            String text = jsonObject.getString("text");
            addLeftChatItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //机器人应答
    private void addLeftChatItem(String leftContent) {
        ChatDataBean chatData = new ChatDataBean();
        chatData.setType(ChatListAdapter.CHAT_LEFT_CONTENT);
        chatData.setContent(leftContent);
        chatDataBeanList.add(chatData);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滑动到底部
        lv_chat_content.setSelection(lv_chat_content.getBottom());
    }

    //用户发送内容
    private void addRightChatItem(String rightContent) {
        ChatDataBean chatData = new ChatDataBean();
        chatData.setType(ChatListAdapter.CHAT_RIGHT_CONTENT);
        chatData.setContent(rightContent);
        chatDataBeanList.add(chatData);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滑动到底部
        lv_chat_content.setSelection(lv_chat_content.getBottom());
    }

}
