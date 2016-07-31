package com.gamik.pastify.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Store extends SQLiteOpenHelper {

    public Store(Context context) {
        super(context, "Paste", null, 1);
    }

    /**
     * called the first time the database is to be created
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + "DATA" + "(" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PlaceHolder" + " VARCHAR, " + "Value" + " VARCHAR, " + "Date" + " VARCHAR, " + "Usage" + " INTEGER);");
    }

    /**
     * called to upgrade the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long saveData(String placeHolder, String value, String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("PlaceHolder", placeHolder);
        cv.put("Value", value);
        cv.put("Usage", 0);
        cv.put("Date", date);
        long status = sqLiteDatabase.insert("DATA", null, cv);
        sqLiteDatabase.close();
        return status;
    }

    public long updateDataById(String placeHolder, String value, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("PlaceHolder", placeHolder);
        cv.put("Value", value);
        long status = sqLiteDatabase.update("DATA", cv, "_id = " + id, null);
        return status;
    }

    public long updateDataByPlaceHolder(String placeHolder, int usage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String selection = "PlaceHolder LIKE ?";
        String args[] = {placeHolder};
        cv.put("Usage", usage);
        long status = sqLiteDatabase.update("DATA", cv, selection, args);
        return status;
    }

    public Cursor getAllData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.query("DATA", null, null, null, null, null, null);
    }

    public long deleteData(String placeHolder) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] whereArgs = {placeHolder};
        return sqLiteDatabase.delete("DATA", "PlaceHolder" + "=?", whereArgs);
    }

    public Cursor getData(String placeHolder) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.query("DATA", null, "PlaceHolder = \'" + placeHolder + "\'", null, null, null, null);
    }

}
