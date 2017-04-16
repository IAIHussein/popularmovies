package com.iaihussein.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.iaihussein.popularmovies.api.Result;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBDataSource {
    // Database fields
    private SQLiteDatabase database;
    private DBSQLiteHelper dbHelper;
    private String[] allColumns = {DBSQLiteHelper.COLUMN_ID,
            DBSQLiteHelper.COLUMN_FOV_ID, DBSQLiteHelper.COLUMN_Contact,
            DBSQLiteHelper.COLUMN_TYPE};
    private String mMovieType = "movie", mReviewType = "review", mTrailerType = "trailer";

    public DBDataSource(Context context) {
        dbHelper = new DBSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createMovie(String contact, String id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues values = new ContentValues();
        values.put(DBSQLiteHelper.COLUMN_Contact, contact);
        values.put(DBSQLiteHelper.COLUMN_FOV_ID, id);
        values.put(DBSQLiteHelper.COLUMN_TYPE, mMovieType);
        database
                .insert(DBSQLiteHelper.TABLE_NAME, null, values);
        close();
    }

    public void createReview(String contact, String id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues values = new ContentValues();
        values.put(DBSQLiteHelper.COLUMN_Contact, contact);
        values.put(DBSQLiteHelper.COLUMN_FOV_ID, id);
        values.put(DBSQLiteHelper.COLUMN_TYPE, mReviewType);
        database
                .insert(DBSQLiteHelper.TABLE_NAME, null, values);
        close();
    }

    public void createTrailer(String contact, String id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues values = new ContentValues();
        values.put(DBSQLiteHelper.COLUMN_Contact, contact);
        values.put(DBSQLiteHelper.COLUMN_FOV_ID, id);
        values.put(DBSQLiteHelper.COLUMN_TYPE, mTrailerType);
         database
                .insert(DBSQLiteHelper.TABLE_NAME, null, values);
        close();
    }

    public void deleteFavoriteById(String id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        database.delete(DBSQLiteHelper.TABLE_NAME,
                DBSQLiteHelper.COLUMN_FOV_ID + " = ?",
                new String[]{id});
        close();
    }

    public Boolean isExist(String id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = database.query(DBSQLiteHelper.TABLE_NAME, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if ((cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.COLUMN_FOV_ID)).equalsIgnoreCase(id))) {
                cursor.close();
                return true;
            }
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        close();
        return false;
    }

    public String getMovie(String id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = database.query(DBSQLiteHelper.TABLE_NAME, allColumns,
                DBSQLiteHelper.COLUMN_FOV_ID + " = ? and " + DBSQLiteHelper.COLUMN_TYPE + "= ?", new String[]{id, mMovieType}, null, null, null);

        cursor.moveToFirst();
        String m = cursor.getString(cursor
                .getColumnIndex(DBSQLiteHelper.COLUMN_Contact));
        return m;
    }

    public String getReview(String id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = database.query(DBSQLiteHelper.TABLE_NAME, allColumns,
                DBSQLiteHelper.COLUMN_FOV_ID + " = ? and " + DBSQLiteHelper.COLUMN_TYPE + "= ?", new String[]{id, mReviewType}, null, null, null);

        cursor.moveToFirst();
        String m = cursor.getString(cursor
                .getColumnIndex(DBSQLiteHelper.COLUMN_Contact));
        return m;
    }

    public String getTrailer(String id) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = database.query(DBSQLiteHelper.TABLE_NAME, allColumns,
                DBSQLiteHelper.COLUMN_FOV_ID + " = ? and " + DBSQLiteHelper.COLUMN_TYPE + "= ?", new String[]{id, mTrailerType}, null, null, null);

        cursor.moveToFirst();
        String m = cursor.getString(cursor
                .getColumnIndex(DBSQLiteHelper.COLUMN_Contact));
        return m;
    }

    public List<Result> getAll() {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Result> mResultList = new ArrayList<>();

        Cursor cursor = database.query(DBSQLiteHelper.TABLE_NAME, allColumns,
                DBSQLiteHelper.COLUMN_TYPE + " = ? ", new String[]{mMovieType}, null,
                null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Result mResult = cursorToResult(cursor);
            mResultList.add(mResult);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        close();
        return mResultList;
    }

    private Result cursorToResult(Cursor cursor) {
        return new Gson().fromJson(cursor.getString(cursor
                .getColumnIndex(DBSQLiteHelper.COLUMN_Contact)), Result.class);
    }
}
