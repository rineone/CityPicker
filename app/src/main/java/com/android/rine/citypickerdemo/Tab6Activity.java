package com.android.rine.citypickerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rine.citypicker.CityPicker;
import com.rine.citypicker.adapter.OnPickListener;
import com.rine.citypicker.model.City;
import com.rine.citypicker.model.HisCity;
import com.rine.citypicker.model.HotCity;
import com.rine.citypicker.model.LocateState;
import com.rine.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RINE
 * @version 1.0(2019 / 1 / 15)
 * 城市选择
 */
public class Tab6Activity extends BaseFragmentActivity  implements CompoundButton.OnCheckedChangeListener{

    private TextView currentTV;
    private CheckBox hotCB;
    private CheckBox hisCB;
    private CheckBox animCB;
    private CheckBox enableCB;
    private Button themeBtn;
    private Button btPicker;

    private static final String KEY = "current_theme";

    private boolean isShowHisCity;
    private List<HotCity> hotCities;
    private List<HisCity> hisCities;
    private int anim;
    private int theme;
    private boolean enable;

    @Override
    public int setOnCreateView(Bundle savedInstanceState) {
        return R.layout.activity_tab6;
    }

    @Override
    public void initView() {
        currentTV = findViewById(R.id.tv_tab6_current);
        hotCB = findViewById(R.id.cb_tab6_hot);
        hisCB = findViewById(R.id.cb_tab6_his);
        animCB = findViewById(R.id.cb_tab6_anim);
        enableCB = findViewById(R.id.cb_tab6_enable_anim);
        themeBtn = findViewById(R.id.btn_tab6_style);
        btPicker = findViewById(R.id.btn_tab6_pick);
        theme = R.style.CustomCityPickerTheme;
        setTheme(theme);
        if (theme == R.style.DefaultCityPickerTheme){
            themeBtn.setText(getString(R.string.tab6_style_defaule));
        }else if (theme == R.style.CustomCityPickerTheme){
            themeBtn.setText(getString(R.string.tab6_style_cus));
        }
    }

    @Override
    public void backFinsh() {

    }

    @Override
    public void initValue() {
        setStatusBarColor(viewStatusBar);
        changTitle(mContext, getResources().getString(R.string.tab6_title), ""
                , true, true, true, true, false);
    }

    @Override
    public void bindEvent() {
        hotCB.setOnCheckedChangeListener(this);
        hisCB.setOnCheckedChangeListener(this);
        animCB.setOnCheckedChangeListener(this);
        enableCB.setOnCheckedChangeListener(this);
        themeBtn.setOnClickListener(this);
        btPicker.setOnClickListener(this);
    }

    @Override
    public void onNoDoubleClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_tab6_style:
                try {
                    if (themeBtn.getText().toString().startsWith("自定义")){
                        themeBtn.setText( getString(R.string.tab6_style_defaule));
                        theme = R.style.DefaultCityPickerTheme;
                    }else if (themeBtn.getText().toString().startsWith("默认")){
                        themeBtn.setText(getString(R.string.tab6_style_cus));
                        theme = R.style.CustomCityPickerTheme;
                    }
//                    setTheme(theme);
//                    recreate();
                }catch (Exception e){
                    Log.e("错误","错误："+e.toString());
                }


                break;
            case R.id.btn_tab6_pick:
                CityPicker.from(Tab6Activity.this)
                        .enableAnimation(enable)
                        .setAnimationStyle(anim)
                        .setCityStyle(theme)
                        .setSearchStyle(2)
                        .setLocatedCity(null)
                        .isShowHisCity(isShowHisCity)
                        .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                currentTV.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                                Toast.makeText(
                                        getApplicationContext(),
                                        String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CityPicker.from(Tab6Activity.this).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cb_tab6_hot:
                if (isChecked){
                    hotCities = new ArrayList<>();
                    hotCities.add(new HotCity("北京", "", "110000"));
                    hotCities.add(new HotCity("杭州", "", "330000"));
                }else {
                    hotCities = null;
                }
                break;
            case R.id.cb_tab6_his:
                if (isChecked){
                    isShowHisCity = true;
                }else {
                    isShowHisCity = false;
                }
                break;
            case R.id.cb_tab6_anim:
                anim = isChecked ? R.style.CustomAnim : R.style.DefaultCityPickerAnimation;
                break;
            case R.id.cb_tab6_enable_anim:
                enable = isChecked;
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY, theme);
    }

    @Override
    public void clear() {
    }

}
