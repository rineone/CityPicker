package com.rine.citypicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rine.citypicker.R;
import com.rine.citypicker.model.City;

import java.util.List;

/**
 * 搜索
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {
    private List<City> mList;//数据源
    private Context mContext;
    private String searchTxt;
    private InnerListener mInnerListener;
    public SearchAdapter(Context context,List<City> list) {
        this.mContext = context;
        mList = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.cp_list_item_search, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        String title = mList.get(position).getName();
        int index = title.indexOf(getSearchTxt());
        int len = getSearchTxt().length();
        if (index!=-1){
            TypedValue typedValue = new TypedValue();
            //1标签
            mContext.getTheme().resolveAttribute(R.attr.cpSearchColor, typedValue, true);
            int mSearchColor = mContext.getResources().getColor(typedValue.resourceId);
            //搜索找到关键词
            SpannableStringBuilder ssb = new SpannableStringBuilder(title);
            ssb.setSpan(new ForegroundColorSpan(mSearchColor),index,index+len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvSearch.setText(ssb);
        }else {
            holder.tvSearch.setText(title);
        }

        holder.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final City data = mList.get(position);
                if (mInnerListener != null){
                    mInnerListener.dismiss(position, data);
                }
            }
        });

    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 自定义的ViewHolder
     */
    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvSearch;

        public MyHolder(View itemView) {
            super(itemView);
            tvSearch = itemView.findViewById(R.id.tv_list_item_search);
        }
    }

    public String getSearchTxt() {
        return searchTxt;
    }

    public void setSearchTxt(String searchTxt) {
        this.searchTxt = searchTxt;
    }
    public void setInnerListener(InnerListener listener){
        this.mInnerListener = listener;
    }
}
