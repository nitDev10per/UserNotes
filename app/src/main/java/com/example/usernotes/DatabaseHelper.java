package com.example.usernotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "your_database_name";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String createTableQuery = "CREATE TABLE IF NOT EXISTS your_table_name ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "img INTEGER,"
                    + "txt TEXT,"
                    + "Note TEXT"
                    + ")";
            db.execSQL(createTableQuery);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Handle database upgrades if needed
        }
}


