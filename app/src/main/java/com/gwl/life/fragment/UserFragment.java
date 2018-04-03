package com.gwl.life.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwl.life.R;
import com.gwl.life.bean.User;
import com.gwl.life.ui.AttributiveInquiryActivity;
import com.gwl.life.ui.LoginActivity;
import com.gwl.life.ui.LogisticInquiryActivity;
import com.gwl.life.utils.L;
import com.gwl.life.utils.SPUtil;
import com.gwl.life.utils.UtilTools;
import com.gwl.life.view.MyDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private CircleImageView civ_user_header;
    private TextView tv_edit_user_information;
    private EditText et_user_nick,et_user_gender,et_user_age,et_user_intro;
    private Button btn_user_exit, btn_user_information_modify_submit,
    btn_user_query_logistic_information,btn_user_attributive_inquiry;

    //点击头像选择用户头像时 弹出一个提示框 包含了 拍照，取图库照片，取消
    private MyDialog dialog;
    private Button btn_take_photo,btn_get_picture, btn_cancel;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        civ_user_header = view.findViewById(R.id.civ_user_header);
        tv_edit_user_information = view.findViewById(R.id.tv_edit_user_information);
        et_user_nick = view.findViewById(R.id.et_user_nick);
        et_user_gender = view.findViewById(R.id.et_user_gender);
        et_user_age = view.findViewById(R.id.et_user_age);
        et_user_intro = view.findViewById(R.id.et_user_intro);
        btn_user_query_logistic_information = view.findViewById(R.id.btn_user_query_logistic_information);
        btn_user_query_logistic_information.setOnClickListener(this);
        btn_user_attributive_inquiry = view.findViewById(R.id.btn_user_attributive_inquiry);
        btn_user_attributive_inquiry.setOnClickListener(this);
        btn_user_exit = view.findViewById(R.id.btn_user_exit);
        btn_user_information_modify_submit = view.findViewById(R.id.btn_user_information_modify_submit);
        civ_user_header.setOnClickListener(this);
        tv_edit_user_information.setOnClickListener(this);
        btn_user_information_modify_submit.setOnClickListener(this);
        btn_user_exit.setOnClickListener(this);
        //默认状态下 EditText不可点击 显示的内容为登录用户的信息
        setEditTextEnable(false);
        btn_user_information_modify_submit.setVisibility(View.GONE);
        //设置具体的值
        User userInfo = BmobUser.getCurrentUser(User.class);
        et_user_nick.setText(userInfo.getUsername());
        et_user_age.setText(userInfo.getAge()+"");
        et_user_gender.setText(userInfo.getSex()?getString(R.string.man):getString(R.string.woman));
        et_user_intro.setText(userInfo.getIntro());
        //初始化Dialog
        dialog = new MyDialog(getActivity(), 100, 100, R.layout.dialog_user_header, R.style.Theme_dialog,
                Gravity.BOTTOM, R.style.dialog_anim_style);
        //框外点击取消
        dialog.setCancelable(false);
        btn_take_photo = dialog.findViewById(R.id.btn_take_photo);
        btn_take_photo.setOnClickListener(this);
        btn_get_picture = dialog.findViewById(R.id.btn_get_picture);
        btn_get_picture.setOnClickListener(this);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        /*
         页面创建时 取出保存用户设置的头像图片
         */
        UtilTools.getImgFromShare(getActivity(),civ_user_header);
    }

    private void setEditTextEnable(boolean enable) {
        et_user_nick.setEnabled(enable);
        et_user_gender.setEnabled(enable);
        et_user_age.setEnabled(enable);
        et_user_intro.setEnabled(enable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_user_header:
                dialog.show();
                break;
            case R.id.tv_edit_user_information:
                //编辑用户信息
                btn_user_information_modify_submit.setVisibility(View.VISIBLE);
                setEditTextEnable(true);
                break;
            case R.id.btn_user_information_modify_submit:
                //获取用户输入
                String nick = et_user_nick.getText().toString();
                String age = et_user_age.getText().toString();
                String gender = et_user_gender.getText().toString();
                String intro = et_user_intro.getText().toString();
                //判断用户输入不能为空
                if (!TextUtils.isEmpty(nick) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(gender)) {
                    //更新修改的属性
                    User user = new User();
                    user.setUsername(nick);
                    user.setAge(Integer.parseInt(age));
                    //判断性别
                    if (gender.equals(getString(R.string.man))) {
                        user.setSex(true);
                    }else {
                        user.setSex(false);
                    }
                    //再判断用户简介是否为空
                    if (!TextUtils.isEmpty(intro)) {
                        user.setIntro(intro);
                    }else {
                        user.setIntro("这个用户很懒，什么也没有留下");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //修改成功
                                setEditTextEnable(false);
                                btn_user_information_modify_submit.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "用户信息不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_user_exit:
                //用户退出登录
                //清除缓存用户对象
                User.logOut();
                //现在的currentUser 是null了
                BmobUser currentUser = User.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_take_photo:
                //拍照片
                turnToCamera();
                break;
            case R.id.btn_get_picture:
                //从图库中去照片
                turnToPicture();
                break;
            //归属地查询
            case R.id.btn_user_attributive_inquiry:
                startActivity(new Intent(getActivity(), AttributiveInquiryActivity.class));
                break;
            //物流信息查询
            case R.id.btn_user_query_logistic_information:
                startActivity(new Intent(getActivity(), LogisticInquiryActivity.class));
                break;
            default:
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int PICTURE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;
    //拍照取头像
    private void turnToCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用,可用的话就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }
    //跳转图库选取图片
    private void turnToPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICTURE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case PICTURE_REQUEST_CODE:
//                    Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'android.net.Uri android.content.Intent.getData()' on a null object reference
//                    at com.gwl.life.fragment.UserFragment.onActivityResult(UserFragment.java:214)
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片进行设置
                        setImageView(data);
                        //既然已经设置了图片，就将原先的删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                default:
                    break;
            }
        }
    }
    /**
     * 设置图片
     * @param data
     */
    private void setImageView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            civ_user_header.setImageBitmap(bitmap);
        }
    }
    /**
     * 剪裁图片
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("Uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置剪裁
        intent.putExtra("crop", "true");
        //设置图片剪裁的宽高比
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置图片剪裁的质量 像素
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*
        页面销毁时要保存用户设置的图片
         */
        UtilTools.putImgToShare(getActivity(),civ_user_header);
    }
}
