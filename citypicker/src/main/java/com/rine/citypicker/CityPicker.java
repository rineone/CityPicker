package com.rine.citypicker;

import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rine.citypicker.adapter.OnPickListener;
import com.rine.citypicker.model.HisCity;
import com.rine.citypicker.model.HotCity;
import com.rine.citypicker.model.LocateState;
import com.rine.citypicker.model.LocatedCity;

import java.lang.ref.WeakReference;
import java.util.List;


public class CityPicker {
    private static final String TAG = "CityPicker";

    private WeakReference<FragmentActivity> mContext;
    private WeakReference<Fragment> mFragment;
    private WeakReference<FragmentManager> mFragmentManager;

    private boolean enableAnim;
    private int mAnimStyle;
    private int mCityStyle;
    private int mSearchStyle;
    /**是否显示历史城市**/
    private boolean isShowHisCity;
    private LocatedCity mLocation;
    private List<HotCity> mHotCities;
    private OnPickListener mOnPickListener;

    private CityPicker(){}

    private CityPicker(Fragment fragment){
        this(fragment.getActivity(), fragment);
        mFragmentManager = new WeakReference<>(fragment.getChildFragmentManager());
    }

    private CityPicker(FragmentActivity activity){
        this(activity, null);
        mFragmentManager = new WeakReference<>(activity.getSupportFragmentManager());
    }

    private CityPicker(FragmentActivity activity, Fragment fragment){
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    public static CityPicker from(Fragment fragment){
        return new CityPicker(fragment);
    }

    public static CityPicker from(FragmentActivity activity){
        return new CityPicker(activity);
    }

    /**
     * 设置动画效果
     * @param animStyle
     * @return
     */
    public CityPicker setAnimationStyle(@StyleRes int animStyle) {
        this.mAnimStyle = animStyle;
        return this;
    }

    /**
     * 设置动画效果
     * @param isShowHisCity
     * @return
     */
    public CityPicker isShowHisCity(boolean isShowHisCity) {
        this.isShowHisCity = isShowHisCity;
        return this;
    }

    /**
     * 设置动画效果
     * @param cityStyle
     * @return
     */
    public CityPicker setCityStyle(@StyleRes int cityStyle) {
        this.mCityStyle = cityStyle;
        return this;
    }

    /**
     * 设置搜索类型
     * @param searchStyle
     * @return
     */
    public CityPicker setSearchStyle( int searchStyle) {
        mSearchStyle = searchStyle;
        return this;
    }

    /**
     * 设置当前已经定位的城市
     * @param location
     * @return
     */
    public CityPicker setLocatedCity(LocatedCity location) {
        this.mLocation = location;
        return this;
    }

    /**
     * 添加热门城市
     * @param data
     * @return
     */
    public CityPicker setHotCities(List<HotCity> data){
        this.mHotCities = data;
        return this;
    }


    /**
     * 启用动画效果，默认为false
     * @param enable
     * @return
     */
    public CityPicker enableAnimation(boolean enable){
        this.enableAnim = enable;
        return this;
    }

    /**
     * 设置选择结果的监听器
     * @param listener
     * @return
     */
    public CityPicker setOnPickListener(OnPickListener listener){
        this.mOnPickListener = listener;
        return this;
    }

    public void show(){
        FragmentTransaction ft = mFragmentManager.get().beginTransaction();
        final Fragment prev = mFragmentManager.get().findFragmentByTag(TAG);
        if (prev != null){
            ft.remove(prev).commit();
            ft = mFragmentManager.get().beginTransaction();
        }
        ft.addToBackStack(null);
        final CityPickerDialogFragment cityPickerFragment =
                CityPickerDialogFragment.newInstance(enableAnim);
        cityPickerFragment.setLocatedCity(mLocation);
        cityPickerFragment.setHotCities(mHotCities);
        cityPickerFragment.setCityStyle(mCityStyle);
        cityPickerFragment.setAnimationStyle(mAnimStyle);
        cityPickerFragment.setOnPickListener(mOnPickListener);
        cityPickerFragment.setSearchStyle(mSearchStyle);
        cityPickerFragment.isShowHisCity(isShowHisCity);
        cityPickerFragment.show(ft, TAG);
    }

    /**
     * 定位完成
     * @param location
     * @param state
     */
    public void locateComplete(LocatedCity location, @LocateState.State int state){
        CityPickerDialogFragment fragment = (CityPickerDialogFragment) mFragmentManager.get().findFragmentByTag(TAG);
        if (fragment != null){
            fragment.locationChanged(location, state);
        }
    }
}
