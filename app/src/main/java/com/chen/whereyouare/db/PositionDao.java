package com.chen.whereyouare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chen.whereyouare.bean.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenHui on 2016/11/11.
 */

public class PositionDao {
    private Context mContext;

    public PositionDao(Context mContext) {
        this.mContext = mContext;
    }

    public void insert(Position item) {
        PositionDBHelper helper = new PositionDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("timeSpan", item.getTimeSpan());
        values.put("mapType", item.getMapType());
        values.put("device", item.getDevice());
        values.put("imei", item.getImei());
        values.put("longtitude", item.getLongtitude());
        values.put("latitude", item.getLatitude());
        values.put("addrStr", item.getAddrStr());
        values.put("locationdescribe", item.getLocationdescribe());
        values.put("poilist", item.getPoilist());
        db.insert(PositionDBHelper.TABLE_NAME, null, values);
        db.close();
    }

    public List<Position> selectList(int count) {
        List<Position> list = new ArrayList<>();
        PositionDBHelper helper = new PositionDBHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        /*
        * db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
        * 第一个参数：表名
        * 第二个参数：返回的列 null表示返回所有列
        * 第三个参数：选择条件，没有选择条件写null
        * 第四个参数：选择条件的参数，没有选择条件参数写null
        * 第五个参数：分组条件，没有写null
        * 第六个参数：分组条件，没有写null
        * 第七个参数:分组条件，没有写null
        * 返回值：查询结果的记录的条数
        */
        Cursor cursor = db.query(PositionDBHelper.TABLE_NAME,
                null, null, null, null, null, "_id desc", "" + count);
        while (cursor.moveToNext()) {
            long timeSpan = cursor.getLong(cursor.getColumnIndex("timeSpan"));
            String mapType = cursor.getString(cursor.getColumnIndex("mapType"));
            String device = cursor.getString(cursor.getColumnIndex("device"));
            String imei = cursor.getString(cursor.getColumnIndex("imei"));
            double longtitude = cursor.getDouble(cursor.getColumnIndex("longtitude"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            String addrStr = cursor.getString(cursor.getColumnIndex("addrStr"));
            String locationdescribe = cursor.getString(cursor.getColumnIndex("locationdescribe"));
            String poilist = cursor.getString(cursor.getColumnIndex("poilist"));
            Position item = new Position(timeSpan, mapType, device, imei, longtitude, latitude, addrStr, locationdescribe, poilist);
            list.add(item);
//            Logger.object("结果：", item);
        }
        //释放资源
        cursor.close();
        db.close();
        return list;
    }

    public void drop(int remainCount) {
    }

    public void delete(Position item1) {
    }
}
