package com.rine.citypicker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rine.citypicker.adapter.CityListAdapter;
import com.rine.citypicker.adapter.InnerListener;
import com.rine.citypicker.adapter.OnPickListener;
import com.rine.citypicker.adapter.decoration.DividerItemDecoration;
import com.rine.citypicker.adapter.decoration.SectionItemDecoration;
import com.rine.citypicker.db.DBManager;
import com.rine.citypicker.model.City;
import com.rine.citypicker.model.HisCity;
import com.rine.citypicker.model.HotCity;
import com.rine.citypicker.model.LocateState;
import com.rine.citypicker.model.LocatedCity;
import com.rine.citypicker.util.ScreenUtil;
import com.rine.citypicker.util.SharedListUtils;
import com.rine.citypicker.view.SideIndexBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CityPickerDialogFragment extends DialogFragment implements TextWatcher,
        View.OnClickListener, SideIndexBar.OnIndexTouchedChangedListener, InnerListener {
    private View mContentView;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private TextView mOverlayTextView;
    private SideIndexBar mIndexBar;
    private EditText mSearchBox;
    private TextView mCancelBtn;
    private ImageView mClearAllBtn;

    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private CityListAdapter mAdapter;
    private boolean mIsShowHisCity;
    private List<City> mAllCities;
    private List<HotCity> mHotCities;
    private List<HisCity> mHisCities;
    private List<City> mResults;

    private DBManager dbManager;

    private int height;
    private int width;

    private boolean enableAnim = false;
    private int mAnimStyle = R.style.DefaultCityPickerAnimation;
    private LocatedCity mLocatedCity;
    private int locateState;
    private OnPickListener mOnPickListener;

    /**
     * 获取实例
     * @param enable 是否启用动画效果
     * @return
     */
    public static CityPickerDialogFragment newInstance(boolean enable){
        final CityPickerDialogFragment fragment = new CityPickerDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("cp_enable_anim", enable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CityPickerStyle);
    }

    public void setLocatedCity(LocatedCity location){
        mLocatedCity = location;
    }

    public void isShowHisCity(boolean isShowHisCity){
        mIsShowHisCity = isShowHisCity;
    }

    public void setHotCities(List<HotCity> data){
        if (data != null && !data.isEmpty()){
            this.mHotCities = data;
        }
    }

    public void setHisCities(List<HisCity> data){
        if (data != null && !data.isEmpty()){
            this.mHisCities = data;
        }
    }

    @SuppressLint("ResourceType")
    public void setAnimationStyle(@StyleRes int resId){
        this.mAnimStyle = resId <= 0 ? mAnimStyle : resId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.cp_dialog_city_picker, container, false);
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initViews();
    }

    private void initView(){
        mRecyclerView = mContentView.findViewById(R.id.cp_city_recyclerview);
        mEmptyView = mContentView.findViewById(R.id.cp_empty_view);
        mOverlayTextView = mContentView.findViewById(R.id.cp_overlay);
        mIndexBar = mContentView.findViewById(R.id.cp_side_index_bar);
        mSearchBox = mContentView.findViewById(R.id.cp_search_box);
        mCancelBtn = mContentView.findViewById(R.id.cp_cancel);
        mClearAllBtn = mContentView.findViewById(R.id.cp_clear_all);

    }
    private void initViews() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SectionItemDecoration(getActivity(), mAllCities), 0);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()), 1);
        mAdapter = new CityListAdapter(getActivity(), mAllCities, mHisCities, mHotCities, locateState);
        mAdapter.autoLocate(true);
        mAdapter.setInnerListener(this);
        mAdapter.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //确保定位城市能正常刷新
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    mAdapter.refreshLocationItem();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });


        mIndexBar.setNavigationBarHeight(ScreenUtil.getNavigationBarHeight(getActivity()));
        mIndexBar.setOverlayTextView(mOverlayTextView)
                .setOnIndexChangedListener(this);
        mSearchBox.addTextChangedListener(this);
        mCancelBtn.setOnClickListener(this);
        mClearAllBtn.setOnClickListener(this);
    }

    private void initData() {
        mContext = getContext();
        Bundle args = getArguments();
        if (args != null) {
            enableAnim = args.getBoolean("cp_enable_anim");
        }
        //初始化热门城市
//        if (mHotCities == null || mHotCities.isEmpty()) {
//            mHotCities = new ArrayList<>();
//            mHotCities.add(new HotCity("杭州", "", "330000")); //province暂未空，没有这个需求
//        }
        //初始化定位城市，默认为空时会自动回调定位
        if (mLocatedCity == null){
            mLocatedCity = new LocatedCity(getString(R.string.cp_locating), getString(R.string.unknow), "0");
            locateState = LocateState.LOCATING;
        }else{
            locateState = LocateState.SUCCESS;
        }

        dbManager = new DBManager(getActivity());
        mAllCities = dbManager.getAllCities();
        mAllCities.add(0, mLocatedCity);
        if (mHotCities!=null && mHotCities.size()>0){
            mAllCities.add(1, new HotCity(getString(R.string.hot_city_nav), getString(R.string.unknow), "0"));
            mIndexBar.mIndexItems.add(1,getString(R.string.hot_city_left_nav));
        }

        if (mIsShowHisCity){
            //显示
            mHisCities = new ArrayList<>();
            mHisCities.addAll(getHisDate());
            if (mHisCities!=null && mHisCities.size()>0){
                mAllCities.add(1, new HisCity(getString(R.string.his_city_nav), getString(R.string.unknow), "0"));
                mIndexBar.mIndexItems.add(1,getString(R.string.his_city_left_nav));
            }
        } else{
            //不显示时，删除数据
            deleteHisDate();
        }

        mResults = mAllCities;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    if (mOnPickListener != null){
                        mOnPickListener.onCancel();
                    }
                }
                return false;
            }
        });

        measure();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(width, height - ScreenUtil.getStatusBarHeight(getActivity()));
            if (enableAnim) {
                window.setWindowAnimations(mAnimStyle);
            }
        }
    }

    //测量宽高
    private void measure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            height = dm.heightPixels;
            width = dm.widthPixels;
        }else{
            DisplayMetrics dm = getResources().getDisplayMetrics();
            height = dm.heightPixels;
            width = dm.widthPixels;
        }
    }

    /** 搜索框监听 */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)){
            mClearAllBtn.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mResults = mAllCities;
            ((SectionItemDecoration)(mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            mAdapter.updateData(mResults);
        }else {
            mClearAllBtn.setVisibility(View.VISIBLE);
            //开始数据库查找
            mResults = dbManager.searchCity(keyword);
            ((SectionItemDecoration)(mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            if (mResults == null || mResults.isEmpty()){
                mEmptyView.setVisibility(View.VISIBLE);
            }else {
                mEmptyView.setVisibility(View.GONE);
                mAdapter.updateData(mResults);
            }
        }
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cp_cancel) {
            dismiss();
            if (mOnPickListener != null){
                mOnPickListener.onCancel();
            }
        }else if(id == R.id.cp_clear_all){
            mSearchBox.setText("");
        }
    }


    @Override
    public void onIndexChanged(String index, int position) {
        //滚动RecyclerView到索引位置
        mAdapter.scrollToSection(index);
    }

    public void locationChanged(LocatedCity location, int state){
        mAdapter.updateLocateState(location, state);
    }

    @Override
    public void dismiss(int position, City data) {
        dismiss();
        if (mOnPickListener != null){
            mOnPickListener.onPick(position, data);
            saveHisData(data);
        }
    }

    @Override
    public void locate(){
        if (mOnPickListener != null){
            mOnPickListener.onLocate();
        }
    }

    public void setOnPickListener(OnPickListener listener){
        this.mOnPickListener = listener;
    }

    /**
     * 存储历史数据
     * @param data
     */
    private void saveHisData(City data){
        List<HisCity> hisCitys = new ArrayList<>();
        hisCitys.addAll(getHisDate());
        HisCity hisCity = new HisCity(data.getName(),data.getProvince(),data.getCode());
        //排除相等的数据
        for(int i = 0;i<hisCitys.size();i++){
            if (data.getName().equals(hisCitys.get(i).getName())){
                hisCitys.remove(i);
                break;
            }
        }
        //保证每次添加的数据都在第一个
        hisCitys.add(0,hisCity);
        SharedListUtils.save(mContext,SharedListUtils.HIS_FILE_KEY,hisCitys);
    }

    /**
     * 获取历史数据
     * @return
     */
    private List<HisCity> getHisDate(){
        List<HisCity> hisCity = new ArrayList<>();
        Object object = SharedListUtils.get(mContext,SharedListUtils.HIS_FILE_KEY);
        if (object!=null){
            hisCity.addAll((Collection<? extends HisCity>) object);
        }
        if (hisCity.size()>=4){
            //如果意外出现5条数据呢
            List<HisCity> hisCity2 = new ArrayList<>();
            hisCity2.add(hisCity.get(0));
            hisCity2.add(hisCity.get(1));
            hisCity2.add(hisCity.get(2));
            return hisCity2;
        }
        return hisCity;
    }

    public void deleteHisDate(){
        SharedListUtils.deleteData(mContext,SharedListUtils.HIS_FILE_KEY);
    }
}
