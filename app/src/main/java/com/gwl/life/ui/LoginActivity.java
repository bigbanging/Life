package com.gwl.life.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gwl.life.MainActivity;
import com.gwl.life.R;
import com.gwl.life.bean.User;
import com.gwl.life.utils.MD5Util;
import com.gwl.life.utils.SPUtil;
import com.gwl.life.view.MyDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_login_username;
    private EditText et_login_password;
    private CheckBox cb_login_remember;
    private TextView tv_login_find_password;
    private Button btn_login_login;
    private Button btn_login_register;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
    }

    private void initView() {
        et_login_username = (EditText) findViewById(R.id.et_login_username);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        cb_login_remember = (CheckBox) findViewById(R.id.cb_login_remember);
        tv_login_find_password = (TextView) findViewById(R.id.tv_login_find_password);
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
        btn_login_register = (Button) findViewById(R.id.btn_login_register);
        //设置进度条
        dialog = new MyDialog(this, 100, 100, R.layout.login_dialog, R.style.Theme_dialog, Gravity.CENTER);
        //设置选中状态
        boolean isChecked = SPUtil.getBoolean(this, "cb_remember", false);
        cb_login_remember.setChecked(isChecked);
        if (isChecked) {
            et_login_username.setText(SPUtil.getString(this, "username", ""));
            et_login_password.setText(SPUtil.getString(this, "password", ""));
        }
    }
    private void setListener() {
        tv_login_find_password.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
        btn_login_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                /**
                 * 获取用户输入
                 * 与数据库相同 则登录成功
                 */
                String username = et_login_username.getText().toString().trim();
//                String password = MD5Util.encryption(et_login_password.getText().toString().trim());
                String password = et_login_password.getText().toString().trim();

                //显示进度条
                dialog.show();
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            dialog.dismiss();
                            if (e == null) {
                                //判断邮箱是否验证
                                if (user.getEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "登录失败，请稍后再试"+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "请填写用户信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_login_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tv_login_find_password:
                startActivity(new Intent(this,ModifyPasswordActivity.class));
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //先保存checkBox的选中状态
        SPUtil.putBoolean(this,"cb_remember",cb_login_remember.isChecked());
        //记住密码功能
        if (cb_login_remember.isChecked()) {
            SPUtil.putString(this, "username", et_login_username.getText().toString().trim());
            SPUtil.putString(this, "password", et_login_password.getText().toString().trim());
        } else {
            SPUtil.delete(this, "username");
            SPUtil.delete(this, "password");
        }
    }
}
