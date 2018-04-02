package com.gwl.life.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gwl.life.MainActivity;
import com.gwl.life.R;
import com.gwl.life.utils.SPUtil;
import com.gwl.life.utils.StaticClass;
import com.gwl.life.utils.UtilTools;

/**
 * 闪屏页
 * 1、闪屏页全屏 styles设置
 * 2、延时2000ms 检查是否是第一次打开
 * 3、第一次打开 进入引导页
 * 4、否在直接进入主页面
 */
public class SplashActivity extends AppCompatActivity {

    private TextView tv_splash;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case StaticClass.HANDLER_WHAT:
                    //判断是否是第一次 ，是第一次跳转到引导页
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        //否则跳转主页面
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        tv_splash = (TextView) findViewById(R.id.tv_splash);
        UtilTools.setText(this,tv_splash);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_WHAT, 2000);
    }

    /**
     * 判断是否是第一运行
     * @return
     */
    private boolean isFirst() {
        boolean isFirst = SPUtil.getBoolean(this, StaticClass.IS_FIRST, true);
        if (isFirst) {
            //是第一运行
            SPUtil.putBoolean(this,StaticClass.IS_FIRST,false);
            return true;
        } else {
            return false;
        }
    }

    //取消系统返回按键功能
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
