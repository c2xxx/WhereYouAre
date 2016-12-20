package com.chen.whereyouare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.whereyouare.R;

import java.util.List;

/**
 * Created by ChenHui on 2016/11/11.
 */

public class StrAdapter extends RecyclerView.Adapter<StrAdapter.ViewHolder> {


    private Context mContext;
    private List<String> list;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public StrAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), getItem_telnum(), null);
        return new ViewHolder(view);
    }

    protected int getItem_telnum() {
        return R.layout.item_str;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.initData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<String> telList) {
        if (telList != null) {
            this.list.clear();
            this.list.addAll(telList);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_str_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_str_content = (TextView) itemView.findViewById(R.id.tv_str_content);
        }

        public void initData(String p, final int position) {
            tv_str_content.setText(p);
            tv_str_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.onItemClick(position);
                    }
                }
            });
        }
    }
}
