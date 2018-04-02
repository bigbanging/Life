package com.gwl.life.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gwl.life.R;
import com.gwl.life.bean.User;
import com.gwl.life.utils.MD5Util;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {

    private EditText et_register_username;
    private EditText et_register_age;
    private EditText et_register_intro;
    private RadioGroup rg_register;
    private RadioButton rb_man;
    private RadioButton rb_woman;
    private EditText et_register_password;
    private EditText et_register_confirm_password;
    private EditText et_register_email;
    private Button btn_register_register;

    //判断注册用户的性别 默认为true 标识男
    private boolean sex = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();
    }

    private void initView() {
        et_register_username= (EditText) findViewById(R.id.et_register_username);
        et_register_age= (EditText) findViewById(R.id.et_register_age);
        et_register_intro= (EditText) findViewById(R.id.et_register_intro);
        rg_register = (RadioGroup) findViewById(R.id.rg_register);
        et_register_password= (EditText) findViewById(R.id.et_register_password);
        et_register_confirm_password= (EditText) findViewById(R.id.et_register_confirm_password);
        et_register_email= (EditText) findViewById(R.id.et_register_email);
        btn_register_register= (Button) findViewById(R.id.btn_register_register);
    }

    private void setListener() {

        btn_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框的值
                String username = et_register_username.getText().toString().trim();
                String age = et_register_age.getText().toString().trim();
                String intro = et_register_intro.getText().toString().trim();
//                String password = MD5Util.encryption(et_register_password.getText().toString().trim());
                String password = et_register_password.getText().toString().trim();
                String confirm_password = et_register_confirm_password.getText().toString().trim();
//                String confirm_password = MD5Util.encryption(et_register_confirm_password.getText().toString().trim());
                String email = et_register_email.getText().toString().trim();
                //输入框不能为空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password) &
                        !TextUtils.isEmpty(age)& !TextUtils.isEmpty(confirm_password) & !TextUtils.isEmpty(email)) {
                    if (TextUtils.isEmpty(intro)) {
//                        et_register_intro.setText("这个人很懒，什么也没留下！");
                        intro = "这个人很懒，什么也没留下！";
                    }
                    //再判断性别
                    rg_register.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                            if (checkedId == R.id.rb_register_man) {
                                sex = true;
                            }else if (checkedId == R.id.rb_register_woman){
                                sex = false;
                            }
                        }
                    });
                    //再判断两次输入的密码是否一致
                    if (password.equals(confirm_password)) {
                        User user = new User();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setIntro(intro);
                        user.setSex(sex);
                        user.signUp(new SaveListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this, "注册成功，请返回登录", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "注册失败："+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(RegisterActivity.this, "请确认密码输入是否一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "请填写完整用户信息！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
