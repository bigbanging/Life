package com.gwl.life.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gwl.life.R;
import com.gwl.life.adapter.LogisticInformationAdapter;
import com.gwl.life.bean.LogisticData;
import com.gwl.life.utils.L;
import com.gwl.life.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogisticInquiryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_express_company,et_tracking_number;
    private Button btn_query_logistic_information;
    private ListView lv_logistic_information;

    //数据源
    private List<LogisticData> dataList = new ArrayList<>();
    //适配器
    private LogisticInformationAdapter adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_inquiry);
        initView();
    }

    private void initView() {
        et_express_company = (EditText) findViewById(R.id.et_express_company);
        et_tracking_number = (EditText) findViewById(R.id.et_tracking_number);
        btn_query_logistic_information = (Button) findViewById(R.id.btn_query_logistic_information);
        lv_logistic_information = (ListView) findViewById(R.id.lv_logistic_information);
        btn_query_logistic_information.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query_logistic_information:
                /*
                1、获取输入框的内容
                2、判断是否为空
                3、拿到数据取请求数据JSON
                4、解析JSON
                5、ListView适配器
                6、实体类+item
                7、设置数据显示效果
                 */
                String logistic_company = et_express_company.getText().toString().trim();
                String tracking_number = et_tracking_number.getText().toString().trim();
                if (!TextUtils.isEmpty(logistic_company) & !TextUtils.isEmpty(tracking_number)) {
                    String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.JUHE_LOGISTIC_APP_KEY +
                            "&com=" + logistic_company + "&no=" + tracking_number + "";
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
//                            Toast.makeText(LogisticInquiryActivity.this, t, Toast.LENGTH_SHORT).show();
                            L.i("返回的物流JSON数据为：" + t);
                            //解析JSON数据呈现在view上
                            parseJson(t);
                        }
                    });
                } else {
                    Toast.makeText(this, "请填写完整要查询的信息", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void parseJson(String logisticData) {
        try {
            JSONObject jsonObject = new JSONObject(logisticData);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = (JSONObject) jsonArray.get(i);
                LogisticData data = new LogisticData();
                data.setDatetime(jsonData.getString("datetime"));
                data.setRemark(jsonData.getString("remark"));
                data.setZone(jsonData.getString("zone"));
                dataList.add(data);
            }
            //物流信息倒叙的实现
            Collections.reverse(dataList);
            adapter = new LogisticInformationAdapter(this, dataList);
            lv_logistic_information.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
