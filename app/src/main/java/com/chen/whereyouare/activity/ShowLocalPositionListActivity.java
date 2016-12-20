package com.chen.whereyouare.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chen.whereyouare.R;
import com.chen.whereyouare.adapter.PositionListAdapter;
import com.chen.whereyouare.bean.Position;
import com.chen.whereyouare.db.PositionDao;
import com.chen.whereyouare.utils.Logger;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ChenHui on 2016/11/11.
 */

public class ShowLocalPositionListActivity extends BaseActivity {


    @BindView(R.id.rv_position_list)
    RecyclerView rvPositionList;

    @Override
    protected void initData() {
        setTitle("本地位置列表");
        PositionDao dao = new PositionDao(this);
        List<Position> list = dao.selectList(100);
        Collections.sort(list);
        PositionListAdapter positionListAdapter = new PositionListAdapter(this, list);
        rvPositionList.setLayoutManager(new LinearLayoutManager(this));
        rvPositionList.setAdapter(positionListAdapter);
//        Logger.d("p size===" + list.size());
//        for (Position p : list) {
//            Logger.d("p3===" + p.getLocationdescribe());
//        }
    }

    protected int getContentLayout() {
        return R.layout.activity_show_position_list;
    }

}
