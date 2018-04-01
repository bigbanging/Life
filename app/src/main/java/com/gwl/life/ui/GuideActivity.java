package com.gwl.life.ui;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gwl.life.MainActivity;
import com.gwl.life.R;
import com.gwl.life.utils.L;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager guide_viewPage;
    private List<View> views = new ArrayList<>();
    private View view1,view2, view3;
    //小圆点
    private ImageView iv_point1,iv_point2, iv_point3;
    //跳过
    private ImageView iv_back;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        iv_point1 = (ImageView) findViewById(R.id.iv_point1);
        iv_point2 = (ImageView) findViewById(R.id.iv_point2);
        iv_point3 = (ImageView) findViewById(R.id.iv_point3);
        guide_viewPage = (ViewPager) findViewById(R.id.guide_viewPager);
        view1 = View.inflate(this, R.layout.guide_item_one, null);
        view2 = View.inflate(this, R.layout.guide_item_two, null);
        view3 = View.inflate(this, R.layout.guide_item_three, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        //默认设置图片
        setPointImage(true,false,false);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        iv_back.setOnClickListener(this);
        guide_viewPage.setAdapter(new GuideAdapter());
        guide_viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                L.i("position:"+position);
                switch (position) {
                    case 0:
                        iv_back.setVisibility(View.VISIBLE);
                        setPointImage(true,false,false);
                        break;
                    case 1:
                        setPointImage(false,true,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImage(false,false,true);
                        iv_back.setVisibility(View.GONE);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.btn_start:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
//            super.destroyItem(container, position, object);
        }
    }

    //小圆点的切换效果设置
    private void setPointImage(boolean isChecked1, boolean isChecked2, boolean isChecked3) {
        if (isChecked1) {
            iv_point1.setBackgroundResource(R.drawable.point_on);
        } else {
            iv_point1.setBackgroundResource(R.drawable.point_off);
        }

        if (isChecked2) {
            iv_point2.setBackgroundResource(R.drawable.point_on);
        } else {
            iv_point2.setBackgroundResource(R.drawable.point_off);
        }

        if (isChecked3) {
            iv_point3.setBackgroundResource(R.drawable.point_on);
        } else {
            iv_point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
