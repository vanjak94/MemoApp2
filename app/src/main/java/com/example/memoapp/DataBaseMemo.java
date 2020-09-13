package com.example.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseMemo extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="memo.db";
    public static final String TABLE_NAME="memodb";
    public static final String COL_1="ID";
    public static final String COL_2="mName";
    public static final String COL_3="mText";

    public DataBaseMemo(Context context){super(context, DATABASE_NAME ,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memodb(ID INTEGER PRIMARY KEY AUTOINCREMENT,mName TEXT,mText TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public int deletePost(int memoID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("memodb",COL_1 + " = ?", new String[]{String.valueOf(memoID)} );
    }
    public long addMemo(String mName,String mText){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mName",mName);
        contentValues.put("mText",mText);

        long res = db.insert("memodb",null,contentValues);
        db.close();
        return res;

    }
    public long editMemo(int memoID,String mName,String mText){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mName",mName);
        contentValues.put("mText",mText);

        long res = db.update("memodb",contentValues,COL_1 + " = ?", new String[]{String.valueOf(memoID)});
        db.close();
        return res;

    }

    final class Memos{
        private int memoID;
        private String mName;
        private String mText;

        public Memos(int memoID,String mName,String mText){
            this.memoID = memoID;
            this.mName = mName;
            this.mText = mText;
        }

        public int getMemoID() {
            return memoID;
        }

        public void setMemoID(int memoID) {
            this.memoID = memoID;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmText() {
            return mText;
        }

        public void setmText(String mText) {
            this.mText = mText;
        }


        @Override
        public String toString() {
            return "Memos{" +
                    "memoID=" + memoID +
                    ", mName='" + mName + '\'' +
                    ", mText='" + mText + '\'' +
                    '}';
        }
    }

    public Memos getMemoById(int memoID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = String.format("SELECT * FROM %s WHERE %S = %s", TABLE_NAME, COL_1, memoID);
        Cursor res = db.rawQuery(SQL, null);
        if (res.moveToFirst()) {
            String mName = res.getString(res.getColumnIndex(COL_2));
            String mText = res.getString(res.getColumnIndex(COL_3));
            return new Memos(memoID,mName,mText);

        }else {
            return null;
        }
    }

    public List<Memos> getAllMemos(){
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = String.format("SELECT * FROM %s", TABLE_NAME);
        Cursor res = db.rawQuery(SQL,null);
        res.moveToFirst();
        List<Memos> list = new ArrayList<>(res.getCount());
        while (res.isAfterLast() == false){
            int memoID = res.getInt(res.getColumnIndex(COL_1));
            String mName = res.getString(res.getColumnIndex(COL_2));
            String mText = res.getString(res.getColumnIndex(COL_3));
            list.add(new Memos(memoID,mName,mText));
            res.moveToNext();
        }
        return  list;
    }


}
