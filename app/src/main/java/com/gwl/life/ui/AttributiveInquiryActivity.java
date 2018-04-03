package com.gwl.life.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwl.life.R;
import com.gwl.life.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 归属地查询
 */
public class AttributiveInquiryActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_phone_number;
    private ImageView iv_china;
    private TextView tv_phone_detail;
    private Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_del,btn_query;
    private boolean flag;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributive_inquiry);
        initView();
    }

    private void initView() {
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        iv_china = (ImageView) findViewById(R.id.iv_china);
        tv_phone_detail = (TextView) findViewById(R.id.tv_phone_detail);

        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_phone_number.setText("");
                return false;
            }
        });
        btn_query = (Button) findViewById(R.id.btn_query_phone);
        btn_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone_number = et_phone_number.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if (flag) {
                    flag = false;
                    phone_number = "";
                    et_phone_number.setText("");
                }
                et_phone_number.setText(phone_number + ((Button)v).getText());
                //移动光标
                et_phone_number.setSelection(phone_number.length()+1);
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(phone_number) && et_phone_number.length() > 0) {
                    et_phone_number.setText(phone_number.substring(0,phone_number.length()-1));
                    //移动光标
                    et_phone_number.setSelection(phone_number.length()-1);
                }
                break;
            case R.id.btn_query_phone:
                if (!TextUtils.isEmpty(phone_number)) {
                    getPhoneDetail(phone_number);
                }
                break;
            default:
                break;
        }
    }

    private void getPhoneDetail(String phoneNumber) {
        String url = "http://apis.juhe.cn/mobile/get?phone="+phoneNumber+"&key="+ StaticClass.JUHE_PHONE_APP_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(AttributiveInquiryActivity.this, t, Toast.LENGTH_SHORT).show();
                parseJson(t);
            }
        });
    }

    private void parseJson(String phone) {
        try {
            JSONObject jsonObject = new JSONObject(phone);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            //区号
            String areacode = jsonResult.getString("areacode");
            //邮编
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");
            /*if (company.equals("中国移动")){
                iv_china.setImageResource(R.drawable.china_mobile);
            } else if (company.equals("中国联通")) {
                iv_china.setImageResource(R.drawable.china_unicom);
            } else if (company.equals("中国电信")) {
                iv_china.setImageResource(R.drawable.china_telecom);
            }*/
            switch (company) {
                case "中国移动":
                    iv_china.setBackgroundResource(R.drawable.china_mobile);
                    iv_china.setVisibility(View.VISIBLE);
                    break;
                case "中国联通":
                    iv_china.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "中国电信":
                    iv_china.setBackgroundResource(R.drawable.china_telecom);
                    break;
                default:
                    break;
            }
            tv_phone_detail.setText("省份："+province+"\n"+
                                    "城市："+city+"\n"+
                                    "区号："+areacode+"\n"+
                                    "邮编："+zip+"\n"+
                                    "类型："+card+"\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
