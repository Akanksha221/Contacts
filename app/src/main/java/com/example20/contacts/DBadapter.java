package com.example20.contacts;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
import android.view.View;

public class DBadapter {
        Context c;
        SQLiteDatabase db;
        DBHelper helper;
        public DBadapter(Context ctx)
        {
            this.c=ctx;
            helper=new DBHelper(c);
        }
        //OPEN DB
        public DBadapter writeopenDB()
        {
            try
            {
                db=helper.getWritableDatabase();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
            return this;
        }
        public DBadapter readopenDB()
        {
            try
            {
                db=helper.getReadableDatabase();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
            return this;
        }
        //CLOSE
        public void close()
        {
            try
            {
                helper.close();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        //INSERT DATA TO DB
        public void add(String name,String number)
        {
            try
            {    writeopenDB();
                ContentValues cv=new ContentValues();
                cv.put(Comment.NAME,name);
                cv.put(Comment.NUMBER, number);
                long status = db.insertWithOnConflict(Comment.TB_NAME,null,cv, SQLiteDatabase.CONFLICT_REPLACE);
                ItemClickListener i = (ItemClickListener)c;
                i.onItemClick((int) status);
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        public Cursor getAll()
        {     readopenDB();
            String[] columns={Comment.NAME,Comment.NUMBER};
            return db.query(true,Comment.TB_NAME,columns,null,null,null,null,null, null);
        }
        //UPDATE
        public long UPDATE(int id,String name,String pos)
        {    //db=helper.getReadableDatabase();
            try
            {   writeopenDB();
                ContentValues cv=new ContentValues();
                cv.put(Comment.NAME,name);
                cv.put(Comment.NUMBER, pos);
                return db.update(Comment.TB_NAME,cv,null,new String[]{String.valueOf(id)});
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
            return 0;
        }
        //DELETE
        public long Delete(int id)
        {
            try
            {   writeopenDB();
                return db.delete(Comment.TB_NAME,null,new String[]{String.valueOf(id)});
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
            return 0;
        }
    }

