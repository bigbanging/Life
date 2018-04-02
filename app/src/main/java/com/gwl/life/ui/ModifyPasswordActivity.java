package com.gwl.life.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gwl.life.R;
import com.gwl.life.bean.User;
import com.gwl.life.utils.L;
import com.gwl.life.utils.MD5Util;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_find_password_by_email;
    private Button btn_send_email,btn_reset_password;
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_new_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        initView();
        setListener();
    }

    private void initView() {
        et_find_password_by_email = (EditText) findViewById(R.id.et_find_password_by_email);
        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_new_confirm_password = (EditText) findViewById(R.id.et_new_confirm_password);
        btn_send_email = (Button) findViewById(R.id.btn_send_email);
        btn_reset_password = (Button) findViewById(R.id.btn_reset_password);
    }
    private void setListener() {
        btn_send_email.setOnClickListener(this);
        btn_reset_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //忘记密码
            case R.id.btn_send_email:
                //获取输入框内容
                final String email = et_find_password_by_email.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    User.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ModifyPasswordActivity.this, "重置密码请求成功，请到" + email + "邮箱进行密码重置操作", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ModifyPasswordActivity.this, "重置密码请求失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "你不输入邮箱怎么找回密码", Toast.LENGTH_SHORT).show();
                }
                break;
            //重置密码
            case R.id.btn_reset_password:
//                String old_password = MD5Util.encryption(et_old_password.getText().toString().trim());
//                String new_password = MD5Util.encryption(et_new_password.getText().toString().trim());
                String old_password = et_old_password.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();
//                String new_confirm_password = MD5Util.encryption(et_new_confirm_password.getText().toString().trim());
                String new_confirm_password = et_new_confirm_password.getText().toString().trim();
                if (!TextUtils.isEmpty(old_password) & !TextUtils.isEmpty(new_password) & !TextUtils.isEmpty(new_confirm_password)) {
                    if (new_password.equals(new_confirm_password)) {
                        User.updateCurrentUserPassword(old_password, new_password, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ModifyPasswordActivity.this, "密码修改成功，可以用新密码登录啦", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ModifyPasswordActivity.this, "密码修改失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    L.i("失败信息"+e.getMessage());
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "请确认密码是否输入无误", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
