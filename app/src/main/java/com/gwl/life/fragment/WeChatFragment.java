package com.gwl.life.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gwl.life.R;
import com.gwl.life.adapter.WeChatArticleAdapter;
import com.gwl.life.bean.WeChatArticleBean;
import com.gwl.life.ui.ShowWechatArticleActivity;
import com.gwl.life.utils.L;
import com.gwl.life.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeChatFragment extends Fragment {

    //控件
    private ListView lv_weChat_article;
    //数据源
    private List<WeChatArticleBean> articles = new ArrayList<>();
    //适配器
    private WeChatArticleAdapter adapter;

    private List<String> titles = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    public WeChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_we_chat, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lv_weChat_article = view.findViewById(R.id.lv_weChat_article);
        String url = "http://v.juhe.cn/weixin/query?key="+ StaticClass.JUHE_WECHAT_ARTICLE_APP_KEY+"&ps=50";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                L.i("微信精选的内容"+t);
                parseJson(t);
            }
        });
        //点击item的事件
        lv_weChat_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowWechatArticleActivity.class);
                intent.putExtra("title", titles.get(position));
                intent.putExtra("url", urls.get(position));
                startActivity(intent);
            }
        });
    }

    private void parseJson(String article) {
        try {
            JSONObject jsonObject = new JSONObject(article);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray jsonArray = result.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String title = json.getString("title");
                String source = json.getString("source");
                String firstImg = json.getString("firstImg");
                String url = json.getString("url");
                WeChatArticleBean weChatArticleBean = new WeChatArticleBean();
                weChatArticleBean.setTitle(title);
                weChatArticleBean.setSource(source);
                weChatArticleBean.setFirstImg(firstImg);
                articles.add(weChatArticleBean);
                titles.add(title);
                urls.add(url);
            }
            adapter = new WeChatArticleAdapter(getActivity(), articles);
            lv_weChat_article.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
