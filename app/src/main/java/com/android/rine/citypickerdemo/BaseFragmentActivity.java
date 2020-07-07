package com.android.rine.citypickerdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Calendar;

/**
 * @author wu
 * @version 1.0(2018/4/24)
 * Acivity  基类
 */
public abstract class BaseFragmentActivity extends AppCompatActivity implements View.OnClickListener {
    //标题信息
    /**状态栏占位view**/
    protected View viewStatusBar;
    /**标题外层**/
    protected RelativeLayout relTitle;
    /**标题**/
    protected TextView tvTitle;
    /**右边字**/
    protected TextView tvRight;
    /**下划线**/
    protected View linTitleUnderline;
    /**收藏图标**/
    protected ImageView ivCollection;
    /**分享图标**/
    protected ImageView ivShare;
    /**返回图片**/
    protected ImageView ivBack;



    /**
     * 来自哪个 页面
     */
    protected String fromWhere;
    /**
     * 页面布局的 根view
     */
    protected View mContentView;


    //暴力点击时间点
    public static final int MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime = 0;
    protected Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置不能横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        int layout = setOnCreateView( savedInstanceState);
        setContentView(layout);
        //Activity管理
//        ActivityPageManager.getInstance().addActivity(this);
        mContext = this;
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mContentView = view;
        //初始化页面
        init();
    }

    /**
     * 初始化页面
     */
    public void init() {
        //标题初始化
        viewStatusBar = (View) findViewById(R.id.view_statusBar);
        ivCollection = (ImageView) findViewById(R.id.iv_collection);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        relTitle = (RelativeLayout) findViewById(R.id.rel_title);
        linTitleUnderline = (View) findViewById(R.id.lin_title_underline);
        tvRight = (TextView) findViewById(R.id.tv_right);
        if (ivBack!=null){
            ivBack.setOnClickListener(this);
        }
        initView();
        initValue();
        bindEvent();
        initFromWhere();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(null);
    }


    //响应按钮的点击事件
    public void SendBack(View view){
        backFinsh();
    }

    /**
     * 获取layout
     **/
    public abstract int setOnCreateView(Bundle savedInstanceState);

    /**
     * 防止暴力点击
     * @param v
     */
    public abstract void onNoDoubleClick(View v);

    public abstract void initValue();

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(view);
        }
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }




    /**
     *  背景， 标题字， 右边信息,是否隐藏(分次为标题,收藏,分享,右边字,下划线)
     */
    protected void changTitle(Context context, String title, String rightTxt
            , boolean titleHide, boolean collHide, boolean shareHide
            , boolean rightHide, boolean linUntitleHide){
        //背景颜色
//        relTitle.setBackground(context.getDrawable(R.color.lin_org));
        //标题
        tvTitle.setText(title);
        tvRight.setText(rightTxt);
        //标题颜色
//        titView.setTextColor(context.getResources().getColor(R.color.white));
//         标题
        if (titleHide){
            tvTitle.setVisibility(View.GONE);
        }else{
            tvTitle.setVisibility(View.VISIBLE);
        }
//       收藏
        if (collHide){
            ivCollection.setVisibility(View.GONE);
        }else{
            ivCollection.setVisibility(View.VISIBLE);
        }
//        分享
        if (shareHide){
            ivShare.setVisibility(View.INVISIBLE);
            ivShare.setPadding(0,0,0,0);
        }else{
            ivShare.setVisibility(View.VISIBLE);
        }
//        右边字
        if (rightHide){
            tvRight.setVisibility(View.GONE);
        }else{
            tvRight.setVisibility(View.VISIBLE);
        }
//        下划线
        if (linUntitleHide){
            linTitleUnderline.setVisibility(View.GONE);
        }else{
            linTitleUnderline.setVisibility(View.VISIBLE);
        }
    }

    protected void rightTitle(String title){
        tvRight.setText(title);
    }

    /**
     * 初始化view
     */
    public abstract void initView();


    /**
     * 绑定事件
     */
    public abstract void backFinsh();

    /**
     * 绑定事件
     */
    public abstract void bindEvent();

    /**
     * 清除事件，防止内存溢出
     */
    public abstract void clear();



    protected void initFromWhere() {
        if (null != getIntent().getExtras()) {
            if (getIntent().getExtras().containsKey("fromWhere")) {
                fromWhere = getIntent().getExtras().getString("fromWhere").toString();
            }
        }
    }

    public String getFromWhere() {
        return fromWhere;
    }


    /**
     * 半透明状态栏
     */
    public void setStatusBarColor(  View statusBarView) {
        Window window =  getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) statusBarView.getLayoutParams();
        linearParams.height = getStatusBarHeight();        // 当控件的高强制设成120象素
        statusBarView.setLayoutParams(linearParams);
        FlymeSetStatusBarLightMode1(true,statusBarView);
    }

    private int getStatusBarHeight( ) {
        int statusBarHeight = 0;
        Resources res = getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public void FlymeSetStatusBarLightMode1(  boolean dark,View view) {
        boolean result = false;
        Window window =  getWindow();
        if (dark){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else {
                view.setBackgroundColor(getResources().getColor(R.color.translucent));
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clear();
    }
}
