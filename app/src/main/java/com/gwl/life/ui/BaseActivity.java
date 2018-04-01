package com.gwl.life.ui;

/**
 *  项目名：  Life
 *  包名：    com.gwl.life.ui
 *  文件名:   BaseActivity
 *  创建者:   GWL
 *  创建时间:  2018/03/30 22:18
 *  描述：    Activity基类
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 主要做的事情是
 * 1、统一 的属性
 * 2、统一的接口
 * 3、统一的方法
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //菜单栏操作

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
