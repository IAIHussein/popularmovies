package com.iaihussein.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "FavoriteTable", COLUMN_ID = "Id",
            COLUMN_Contact = "Contact", COLUMN_TYPE = "Type",
            COLUMN_FOV_ID = "FavoriteID";

    private static final String DATABASE_NAME = "Numbers.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TYPE + " text not null, " + COLUMN_FOV_ID + " text not null, " + COLUMN_Contact
            + " text not null);";

    public DBSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
