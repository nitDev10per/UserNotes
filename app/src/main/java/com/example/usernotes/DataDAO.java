package com.example.usernotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DataDAO {

    private DatabaseHelper dbHelper;

    public DataDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insertData(dataClass data) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("img", data.img);
        values.put("txt", data.txt);
        values.put("Note", data.Note);
        db.insert("your_table_name", null, values);
        db.close();
    }

    public ArrayList<dataClass> getAllData() {
        ArrayList<dataClass> dataList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM your_table_name", null);

        if (cursor.moveToFirst()) {
            do {
                int img = cursor.getInt(cursor.getColumnIndex("img"));
                String txt = cursor.getString(cursor.getColumnIndex("txt"));
                String note = cursor.getString(cursor.getColumnIndex("Note"));
                dataList.add(new dataClass(img, txt, note));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dataList;
    }


    public void updateDataAtIndex(int position, dataClass newData) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("img", newData.img);
        values.put("txt", newData.txt);
        values.put("Note", newData.Note);

        Cursor cursor = db.rawQuery("SELECT id FROM your_table_name", null);

            if (cursor.moveToPosition(position)) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String[] whereArgs = {String.valueOf(id)};
                db.update("your_table_name", values, "id=?", whereArgs);

            }

            cursor.close();
            db.close();

    }


    public void deleteDataAtPosition(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM your_table_name", null);

        if (cursor.moveToPosition(position)) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String[] whereArgs = {String.valueOf(id)};
            db.delete("your_table_name", "id=?", whereArgs);
        }

        cursor.close();
        db.close();
    }

    // Other methods for update, delete, etc.
}
