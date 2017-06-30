package com.example20.contacts;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AKANKSHA on 29-06-2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Comment.DB_NAME, null, Comment.DB_VERSION);
    }
    //WHEN TB IS CREATED
    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(Comment.CREATE_TB);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    //UPGRADE TB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ Comment.TB_NAME);
        onCreate(db);
    }
}

