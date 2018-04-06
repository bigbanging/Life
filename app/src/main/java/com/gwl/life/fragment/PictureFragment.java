package com.gwl.life.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gwl.life.R;
import com.gwl.life.adapter.PictureAdapter;
import com.gwl.life.bean.PictureData;
import com.gwl.life.utils.L;
import com.gwl.life.utils.PicassoUtils;
import com.gwl.life.utils.StaticClass;
import com.gwl.life.view.MyDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureFragment extends Fragment {
    private GridView gv_picture;
    //数据源
    private List<PictureData> pictureDataList = new ArrayList<>();
    //适配器
    PictureAdapter adapter;
    private List<String> listUrls = new ArrayList<>();
    //点击图片
    private MyDialog dialog;
    private ImageView iv_photo_view_img;
    private PhotoViewAttacher photoViewAttacher;
    public PictureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        gv_picture = view.findViewById(R.id.gv_picture);
        dialog = new MyDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.picture_photo_view,
                R.style.Theme_dialog, Gravity.CENTER);
        iv_photo_view_img = dialog.findViewById(R.id.iv_photo_view_img);
        RxVolley.get(StaticClass.GANK_FULI_API, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                L.i(t);
                parseJson(t);
            }
        });
        gv_picture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicassoUtils.loadImageView(getActivity(), listUrls.get(position), iv_photo_view_img);
                photoViewAttacher = new PhotoViewAttacher(iv_photo_view_img);
                photoViewAttacher.update();
                dialog.show();
            }
        });
    }

    private void parseJson(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray result = jsonObject.getJSONArray("results");
            for (int i = 0; i < result.length(); i++) {
                JSONObject object = (JSONObject) result.get(i);
                String imgUrl = object.getString("url");
                String publicAt = object.getString("publishedAt");
                listUrls.add(imgUrl);
                PictureData pictureData = new PictureData();
                pictureData.setPictureUrl(imgUrl);
                pictureData.setPublishedAt(publicAt);
                pictureDataList.add(pictureData);
            }
            adapter = new PictureAdapter(getActivity(), pictureDataList);
            gv_picture.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
