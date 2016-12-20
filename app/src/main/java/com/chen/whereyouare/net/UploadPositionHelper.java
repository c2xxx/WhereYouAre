package com.chen.whereyouare.net;

import com.chen.whereyouare.MyApplication;
import com.chen.whereyouare.bean.Position;
import com.chen.whereyouare.db.PositionDao;
import com.chen.whereyouare.qiniu.UpdateContentToQiniu;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ChenHui on 2016/11/11.
 */

public class UploadPositionHelper {

    /*
    * 存储逻辑：
    * 每小时一个文件存储整个小时的定位信息（小时文件），每天一个文件存（天文件）储每天有多少小时文件
    * */
    private static UploadPositionHelper instance;

    private UploadPositionHelper() {
    }

    public static UploadPositionHelper getInstance() {
        if (instance == null) {
            instance = new UploadPositionHelper();
        }
        return instance;
    }

    public void savePosition(Position position) {
        PositionDao positionDao = new PositionDao(MyApplication.getContext());
        positionDao.insert(position);
        Date date = new Date(position.getTimeSpan());
        String dayFileName = getDayFileList(date);
        String hourFileName = getHourFileName(date);
        String json = position.toJson();

        UpdateContentToQiniu.appendContent(dayFileName, hourFileName);
        UpdateContentToQiniu.appendContent(hourFileName, json);
    }

    /**
     * 根据日期获取整天的文件列表
     *
     * @param date
     * @return
     */
    public String getDayFileList(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        return "list_" + sdf.format(date) + ".txt";
    }

    /**
     * 根据时间获取当前小时的文件列表
     *
     * @param date
     * @return
     */
    public String getHourFileName(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH");
        return "position_" + sdf.format(date) + ".txt";
    }

}
