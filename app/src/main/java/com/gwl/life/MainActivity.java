package com.gwl.life;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.gwl.life.fragment.ButlerFragment;
import com.gwl.life.fragment.PictureFragment;
import com.gwl.life.fragment.UserFragment;
import com.gwl.life.fragment.WeChatFragment;
import com.gwl.life.ui.SettingActivity;
import com.gwl.life.utils.L;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //TableLayout
    private TabLayout tabLayout;
    //ViewPager
    private ViewPager viewPager;
    //悬浮按钮
    private FloatingActionButton fab_setting;
    //顶部文字
    private List<String> titleText;
    //Fragments
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去掉阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
//        CrashReport.testJavaCrash();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);
        //第一页默认隐藏悬浮窗
        fab_setting.setVisibility(View.GONE);
        fab_setting.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                L.i("onPage Selected: ViewPager的位置"+position);
                if (position == 0) {
                    fab_setting.setVisibility(View.GONE);
                } else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //预加载
        viewPager.setOffscreenPageLimit(fragments.size());
        //设置适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            //返回Item的个数
            @Override
            public int getCount() {
                return fragments.size();
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return titleText.get(position);
            }
        });
        //将TableLayout和ViewPager绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        titleText = new ArrayList<>();
        titleText.add("管家服务");
        titleText.add("微信精选");
        titleText.add("美女如云");
        titleText.add("个人中心");

        fragments = new ArrayList<>();
        fragments.add(new ButlerFragment());
        fragments.add(new WeChatFragment());
        fragments.add(new PictureFragment());
        fragments.add(new UserFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            default:
                break;
        }
    }
}
