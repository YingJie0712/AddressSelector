package com.chenyingjie.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yingjie.addressselector.api.CYJAdSelector;
import com.yingjie.addressselector.api.OnSelectorListener;

public class MainActivity extends AppCompatActivity {

    private TextView tvAddress;

    private int mType;// 可能是新增地址，可能是编辑地址。
    private String selectedProvince;
    private String selectedCity;
    private String selectedArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAddress = findViewById(R.id.tvAddress);

        findViewById(R.id.btnSelector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkType();
                selector();
            }
        });
    }

    private void selector() {
        CYJAdSelector.showSelector(this, mType, selectedProvince, selectedCity, selectedArea, new OnSelectorListener() {
            @Override
            public void onSelector(String province, String city, String area) {
                // 选择完回调结果赋值给当前
                selectedProvince = province;
                selectedCity = city;
                selectedArea = area;

                tvAddress.setText(selectedProvince + " " + selectedCity + " " + selectedArea);
            }
        });
    }

    private void checkType() {
        if (TextUtils.isEmpty(selectedProvince) && TextUtils.isEmpty(selectedCity) && TextUtils.isEmpty(selectedArea)) {
            mType = 0;// add
        } else {
            mType = 1;// edit
        }
    }
}
