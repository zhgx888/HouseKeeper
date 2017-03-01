package edu.feicui.app.phone.base.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/7 0007.
 */

public class PhoneBookUtil {
    public static final String DATA_BASE_NAME = "dbtest";
    public static final int VERSION = 1;
    SQLiteDatabase sqlDB;
    Context mCtx;
    Cursor mCtxNums;

    public PhoneBookUtil(Context context) {
        mCtx = context;
    }

    public List<Map<String, String>> readDirectorys() {
        List<Map<String, String>> directorys = new ArrayList<>();
        sqlDB = SQLiteDatabase.openOrCreateDatabase(mCtx.getFilesDir() + File.separator + "database" + File
                .separator + "commonnum.db", null);
        Cursor cursor = sqlDB.rawQuery("select * from classlist;", null);
        while (cursor.moveToNext()) {
            Map<String, String> list = new HashMap<String, String>();
            String name = cursor.getString(0);
            list.put("name", name);
            directorys.add(list);
        }
        return directorys;
    }

    public List<Map<String, String>> readDirectorys(int position) {
        List<Map<String, String>> directorys = new ArrayList<>();
        sqlDB = SQLiteDatabase.openOrCreateDatabase(mCtx.getFilesDir() + File.separator + "database" + File
                .separator + "commonnum.db", null);
        mCtxNums = sqlDB.rawQuery("select * from table" + (position + 1) + ";", null);
        while (mCtxNums.moveToNext()) {
            Map<String, String> list = new HashMap<String, String>();
            String number = mCtxNums.getString(1);
            String name = mCtxNums.getString(2);
            list.put("number", number);
            list.put("name", name);
            directorys.add(list);
        }
        return directorys;
    }
}