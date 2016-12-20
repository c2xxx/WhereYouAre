package com.chen.whereyouare.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ChenHui on 2016/11/11.
 */

public class PositionDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "position";

    public PositionDBHelper(Context context) {
        super(context, TABLE_NAME + ".db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        private long timeSpan;//时间戳
//        private String mapType;//baidu,gaode,tengxun
//        private double longtitude;//经度
//        private double latitude;//纬度
//        private String addrStr;//大概地址，哪条路
//        private String locationdescribe;//具体地址描述
//        private String poilist;//具体地址，可能的列表

        String sql = "CREATE  TABLE " + TABLE_NAME +
                " (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
                "timeSpan VARCHAR, " +
                "mapType VARCHAR, " +
                "device VARCHAR, " +
                "imei VARCHAR, " +
                "longtitude DOUBLE, " +
                "latitude latitude, " +
                "addrStr VARCHAR, " +
                "locationdescribe VARCHAR, " +
                "poilist VARCHAR)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
