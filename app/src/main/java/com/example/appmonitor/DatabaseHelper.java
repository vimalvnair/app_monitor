package com.example.appmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by multunus on 16/01/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    public static final String DATABASE_NAME="app_monitor.db";
    private static final int DATABASE_VERSION =1;

    public static final String TABLE_NAME="app_status";
    public static final String COLUMN_ID = "_id";
    public static final String APP_NAME = "name";
    public static final String PACKAGE_NAME = "package_name";

    public static final String USAGE="usage";
    public static final String REPORT_DATE ="report_date";

    private static final String TABLE_CREATE="create table " +TABLE_NAME + " (" + COLUMN_ID + " integer primary key autoincrement, " +
            APP_NAME + " text, " +
            PACKAGE_NAME + " text, " +
            USAGE + " integer, " +
            REPORT_DATE + " date unique not null)";

    public DatabaseHelper(Context context) throws SQLiteException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        System.out.println("the db  --------------------------------");
        //System.out.println("the db  "+ this.getWritableDatabase());
        this.sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("the create ");
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public void seedTheDB(){
//        System.out.println("seeding..........");
//        JSONArray jsonArray = null;
//
//        try {
//            jsonArray = new JSONArray(loadJSONFromAsset());
//
//            for(int i=0; i<jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(DatabaseHelper.NAME, jsonObject.getString("name"));
//                contentValues.put(DatabaseHelper.CATEGORY, jsonObject.getString("category"));
//                this.sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

}