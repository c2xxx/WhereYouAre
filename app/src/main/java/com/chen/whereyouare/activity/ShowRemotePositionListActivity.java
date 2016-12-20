package com.chen.whereyouare.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chen.whereyouare.R;
import com.chen.whereyouare.adapter.OnItemClick;
import com.chen.whereyouare.adapter.PositionListAdapter;
import com.chen.whereyouare.adapter.StrAdapter;
import com.chen.whereyouare.bean.Position;
import com.chen.whereyouare.net.QiniuApi;
import com.chen.whereyouare.net.QiniuApiHelper;
import com.chen.whereyouare.net.UploadPositionHelper;
import com.chen.whereyouare.utils.Logger;
import com.chen.whereyouare.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ChenHui on 2016/11/16.
 */
public class ShowRemotePositionListActivity extends BaseActivity {
    @BindView(R.id.rv_position_list)
    RecyclerView rvPositionList;

    @BindView(R.id.btn_remotelist_choose_date)
    TextView btnChooseDate;

    @OnClick(R.id.btn_remotelist_choose_date)
    public void chooseDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowRemotePositionListActivity.this);
        builder.setTitle("选择一个日期");
        long time = System.currentTimeMillis();
        final List<String> timeList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String timeStr = UploadPositionHelper.getInstance().getDayFileList(new Date(time - i * 24 * 60 * 60 * 1000));
            Logger.d("time=" + time + " == " + timeStr);
            timeList.add(timeStr);
        }
        timeList.toArray();
        //    指定下拉列表的显示数据
        String[] cities = timeList.toArray(new String[]{});//{"广州", "上海", "北京", "香港", "澳门"};
        if (cities.length > 1) {
            cities[0] = cities[0] + "(今天)";
        }
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String day = timeList.get(which);
                ToastUtil.show(day);
                loadContentByDayFileName(day);
                btnChooseDate.setText(day);
            }
        });
        builder.show();
    }

    /**
     * 根据日期文件名获取内容
     *
     * @param dayFileName
     */
    private void loadContentByDayFileName(String dayFileName) {
        if (this.isFinishing()) {
            return;
        }
        QiniuApi service = QiniuApiHelper.getInstanceMyPosition();

        Call<ResponseBody> call = service.loadContent(dayFileName, System.currentTimeMillis() + "");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Logger.d("获取信息结果：" + response.isSuccessful());
                    if (response.isSuccessful()) {
                        Set<String> hours = new HashSet<>();
                        String result = response.body().source().readUtf8();
                        String[] hoursStr = result.split(",");
                        for (String x : hoursStr) {
                            hours.add(x);
                        }

                        final List<String> list = new ArrayList<>();
                        for (String x : hours) {
                            list.add(x);
                        }
                        StrAdapter positionListAdapter = new StrAdapter(ShowRemotePositionListActivity.this, list);
                        positionListAdapter.setOnItemClick(new OnItemClick() {
                            @Override
                            public void onItemClick(int position) {
                                if (list != null && list.size() > position) {
                                    String hour = list.get(position);
                                    ToastUtil.show(hour);
                                    btnChooseDate.setText(hour);
                                    loadDataByTime(hour);
                                }
                            }
                        });
                        rvPositionList.setLayoutManager(new LinearLayoutManager(ShowRemotePositionListActivity.this));
                        rvPositionList.setAdapter(positionListAdapter);
                    } else {
                        ToastUtil.show("ERROR：" + response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("失败" + t.getMessage());
            }
        });
    }

    private void loadDataByTime(String hourFile) {
        if (this.isFinishing()) {
            return;
        }
        QiniuApi service = QiniuApiHelper.getInstanceMyPosition();
        Call<ResponseBody> call = service.loadContent(hourFile, System.currentTimeMillis() + "");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Logger.d("获取信息结果：" + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String result = response.body().source().readUtf8();
                        Gson gson = new Gson();
                        List<Position> list = gson.fromJson("[" + result + "]",
                                new TypeToken<List<Position>>() {
                                }.getType());
                        Collections.sort(list);
//                        list.sort(new Comparator<Position>() {
//                            @Override
//                            public int compare(Position o1, Position o2) {
//                                return 0;
//                            }
//                        });
                        PositionListAdapter positionListAdapter = new PositionListAdapter(ShowRemotePositionListActivity.this, list);
                        rvPositionList.setLayoutManager(new LinearLayoutManager(ShowRemotePositionListActivity.this));
                        rvPositionList.setAdapter(positionListAdapter);
                    } else {
                        ToastUtil.show("ERROR：" + response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("失败" + t.getMessage());
            }
        });
    }

    @Override
    protected void initData() {
        setTitle("远程位置列表");
    }

    protected int getContentLayout() {
        return R.layout.activity_show_remote_position_list;
    }
}