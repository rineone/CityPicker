package com.rine.citypicker.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;

import com.rine.citypicker.R;
import com.rine.citypicker.model.City;

import java.util.List;

public class SectionItemDecoration extends RecyclerView.ItemDecoration {
    private List<City> mData;
    private Paint mBgPaint1;
    private Paint mBgPaint2;
    //为了区分字母和热门Tab不一样
    private TextPaint mTextPaint1;
    private TextPaint mTextPaint2;
    private Rect mBounds;

    private int mSectionHeight1;
    private int mBgColor1;
    private int mTextColor1;
    private int mTextSize1;

    private int mSectionHeight2;
    private int mBgColor2;
    private int mTextColor2;
    private int mTextSize2;


    public SectionItemDecoration(Context context, List<City> data) {
        this.mData = data;
        TypedValue typedValue = new TypedValue();

        //1标签
        context.getTheme().resolveAttribute(R.attr.cpSectionBackground1, typedValue, true);
        mBgColor1 = context.getResources().getColor(typedValue.resourceId);

        context.getTheme().resolveAttribute(R.attr.cpSectionHeight1, typedValue, true);
        mSectionHeight1 = context.getResources().getDimensionPixelSize(typedValue.resourceId);

        context.getTheme().resolveAttribute(R.attr.cpSectionTextSize1, typedValue, true);
        mTextSize1 = context.getResources().getDimensionPixelSize(typedValue.resourceId);

        context.getTheme().resolveAttribute(R.attr.cpSectionTextColor1, typedValue, true);
        mTextColor1 = context.getResources().getColor(typedValue.resourceId);

        mBgPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint1.setColor(mBgColor1);



        mTextPaint1 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint1.setTextSize(mTextSize1);
        mTextPaint1.setColor(mTextColor1);

        //2标签
        context.getTheme().resolveAttribute(R.attr.cpSectionBackground2, typedValue, true);
        mBgColor2 = context.getResources().getColor(typedValue.resourceId);

        context.getTheme().resolveAttribute(R.attr.cpSectionHeight2, typedValue, true);
        mSectionHeight2 = context.getResources().getDimensionPixelSize(typedValue.resourceId);

        context.getTheme().resolveAttribute(R.attr.cpSectionTextSize2, typedValue, true);
        mTextSize2 = context.getResources().getDimensionPixelSize(typedValue.resourceId);

        context.getTheme().resolveAttribute(R.attr.cpSectionTextColor2, typedValue, true);
        mTextColor2 = context.getResources().getColor(typedValue.resourceId);

        mBgPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint2.setColor(mBgColor2);

        mTextPaint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint2.setTextSize(mTextSize2);
        mTextPaint2.setColor(mTextColor2);


        mBounds = new Rect();
    }

    public void setData(List<City> data) {
        this.mData = data;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (mData != null && !mData.isEmpty() && position <= mData.size() - 1 && position > -1) {
                if (position == 0) {
                    drawSection(c, left, right, child, params, position);
                } else {
                    if (null != mData.get(position).getSection()
                            && !mData.get(position).getSection().equals(mData.get(position - 1).getSection())) {
                        drawSection(c, left, right, child, params, position);
                    }
                }
            }
        }
    }

    private void drawSection(Canvas c, int left, int right, View child,
                             RecyclerView.LayoutParams params, int position) {
        if (mData.get(position).getSection().contains("定")
                ||mData.get(position).getSection().contains("历")
                ||mData.get(position).getSection().contains("热")){
            c.drawRect(left,
                    child.getTop() - params.topMargin - mSectionHeight1,
                    right,
                    child.getTop() - params.topMargin, mBgPaint1);
            mTextPaint1.getTextBounds(mData.get(position).getSection(),
                    0,
                    mData.get(position).getSection().length(),
                    mBounds);
            c.drawText(mData.get(position).getSection(),
                    child.getPaddingLeft(),
                    child.getTop() - params.topMargin - (mSectionHeight1 / 2 - mBounds.height() / 2),
                    mTextPaint1);
        }else {
            c.drawRect(left,
                    child.getTop() - params.topMargin - mSectionHeight2,
                    right,
                    child.getTop() - params.topMargin, mBgPaint2);
            mTextPaint1.getTextBounds(mData.get(position).getSection(),
                    0,
                    mData.get(position).getSection().length(),
                    mBounds);
            c.drawText(mData.get(position).getSection(),
                    child.getPaddingLeft(),
                    child.getTop() - params.topMargin - (mSectionHeight2 / 2 - mBounds.height() / 2),
                    mTextPaint2);
        }

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        if (pos < 0) return;
        if (mData == null || mData.isEmpty()) return;
        String section = mData.get(pos).getSection();
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;

        boolean flag = false;
        if ((pos + 1) < mData.size()) {
            if (null != section && !section.equals(mData.get(pos + 1).getSection())) {
                int height = mSectionHeight2;
                if (mData.get(pos + 1).getSection().contains("定")
                        ||mData.get(pos + 1).getSection().contains("历")
                        ||mData.get(pos + 1).getSection().contains("热")){
                    height = mSectionHeight1;
                }
                if (child.getHeight() + child.getTop() < height) {
                    c.save();
                    flag = true;
                    c.translate(0, child.getHeight() + child.getTop() - height);
                }
            }
        }

        if (mData.get(pos).getSection().contains("定")
                ||mData.get(pos).getSection().contains("历")
                ||mData.get(pos).getSection().contains("热")){
            c.drawRect(parent.getPaddingLeft(),
                    parent.getPaddingTop(),
                    parent.getRight() - parent.getPaddingRight(),
                    parent.getPaddingTop() + mSectionHeight1, mBgPaint1);
            mTextPaint1.getTextBounds(section, 0, section.length(), mBounds);
            c.drawText(section,
                    child.getPaddingLeft(),
                    parent.getPaddingTop() + mSectionHeight1 - (mSectionHeight1 / 2 - mBounds.height() / 2),
                    mTextPaint1);
        }else {
            c.drawRect(parent.getPaddingLeft(),
                    parent.getPaddingTop(),
                    parent.getRight() - parent.getPaddingRight(),
                    parent.getPaddingTop() + mSectionHeight2, mBgPaint2);
            mTextPaint2.getTextBounds(section, 0, section.length(), mBounds);
            c.drawText(section,
                    child.getPaddingLeft(),
                    parent.getPaddingTop() + mSectionHeight2 - (mSectionHeight2 / 2 - mBounds.height() / 2),
                    mTextPaint2);
        }

        if (flag)
            c.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        if (mData != null && !mData.isEmpty() && position <= mData.size() - 1 && position > -1) {
            int height = mSectionHeight2;
            if (mData.get(position).getSection().contains("定")
                    ||mData.get(position).getSection().contains("历")
                    ||mData.get(position).getSection().contains("热")){
                height = mSectionHeight1;
            }
            if (position == 0) {
                outRect.set(0, height, 0, 0);
            } else {
                if (null != mData.get(position).getSection()
                        && !mData.get(position).getSection().equals(mData.get(position - 1).getSection())) {
                    outRect.set(0, height, 0, 0);
                }
            }
        }
    }

}
