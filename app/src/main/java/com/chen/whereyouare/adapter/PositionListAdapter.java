package com.chen.whereyouare.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.whereyouare.MyApplication;
import com.chen.whereyouare.R;
import com.chen.whereyouare.bean.Position;
import com.chen.whereyouare.utils.MyUtil;
import com.chen.whereyouare.utils.ToastUtil;

import java.util.List;

/**
 * Created by ChenHui on 2016/11/11.
 */

public class PositionListAdapter extends RecyclerView.Adapter<PositionListAdapter.ViewHolder> {


    private Context mContext;
    private List<Position> list;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public PositionListAdapter(Context mContext, List<Position> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), getItem_telnum(), null);
        return new ViewHolder(view);
    }

    protected int getItem_telnum() {
        return R.layout.item_position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.initData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Position> telList) {
        if (telList != null) {
            this.list.clear();
            this.list.addAll(telList);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPositionIndex;
        TextView tvPositionTime;
        TextView btnPositionDetail;
        TextView tvPositionLocaldescribe;
        TextView tv_position_detail;
        TextView btn_position_openmap;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPositionIndex = (TextView) itemView.findViewById(R.id.tv_position_index);
            tvPositionTime = (TextView) itemView.findViewById(R.id.tv_position_time);
            btnPositionDetail = (TextView) itemView.findViewById(R.id.btn_position_detail);
            tvPositionLocaldescribe = (TextView) itemView.findViewById(R.id.tv_position_localdescribe);
            tv_position_detail = (TextView) itemView.findViewById(R.id.tv_position_detail);
            btn_position_openmap = (TextView) itemView.findViewById(R.id.btn_position_openmap);
        }

        public void initData(final Position p, final int position) {
            tvPositionIndex.setText("" + (position + 1));
            tvPositionTime.setText(MyUtil.timeSpan2Str(p.getTimeSpan()));
            btnPositionDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int visible = tv_position_detail.getVisibility();
                    if (visible == View.VISIBLE) {
                        p.setLocal_isDetailExpand(false);
                        tv_position_detail.setVisibility(View.GONE);
                    } else {
                        p.setLocal_isDetailExpand(true);
                        tv_position_detail.setVisibility(View.VISIBLE);
                    }
                }
            });
            btn_position_openmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtil.show("Open");
                    try {
                        Intent i1 = new Intent();
                        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // 展示地图
//                        i1.setData(Uri.parse("baidumap://map/show?center=40.057406655722,116.29644071728&zoom=14"));
                        String uri = String.format("baidumap://map/marker?location=%s,%s&title=%s&content=%s",
                                p.getLatitude(),
                                p.getLongtitude(),
                                "我在：" + p.getLocationdescribe(),
                                "定位时间：" + MyUtil.timeSpan2Str(p.getTimeSpan()));
                        i1.setData(Uri.parse(uri));
                        MyApplication.getContext().startActivity(i1);
                    } catch (Exception e) {
                        ToastUtil.show("请安装百度地图");
                    }
                }
            });
            tvPositionLocaldescribe.setText(p.getLocationdescribe());
            String detail = "" + p.getPoilist();
            detail = detail.replace(";", "\n");
            tv_position_detail.setText("设备：" + p.getDevice() + "\n" + detail.trim());


            if (p.isLocal_isDetailExpand()) {
                tv_position_detail.setVisibility(View.VISIBLE);
            } else {
                tv_position_detail.setVisibility(View.GONE);
            }
        }
    }
}
