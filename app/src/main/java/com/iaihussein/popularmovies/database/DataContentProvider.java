package com.iaihussein.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.iaihussein.popularmovies.Var;

/**
 * Created by ibrahim.hussein on 5/15/17.
 */

public class DataContentProvider extends ContentProvider {
    public static final int All = 100;
    public static final int GET_ONE_BY_ID = 101;
    // Declare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    DBSQLiteHelper mHelper;

    // Define a static buildUriMatcher method that associates URI's with their int match

    /**
     * Initialize a new matcher object without any matches,
     * then use .addURI(String authority, String path, int match) to add matches
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
        uriMatcher.addURI(Var.AUTHORITY, DBSQLiteHelper.TABLE_NAME, All);
        uriMatcher.addURI(Var.AUTHORITY, DBSQLiteHelper.TABLE_NAME + "/#", GET_ONE_BY_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mHelper = new DBSQLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mHelper.getReadableDatabase();

        // COMPLETED (2) Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor = null;
        if (All == match) {
            retCursor = db.query(DBSQLiteHelper.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
        }
        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        // URI to be returned
        Uri returnUri = null;
        if (All == sUriMatcher.match(uri)) {
            long id = db.insert(DBSQLiteHelper.TABLE_NAME, null, values);

            if (id > 0)
                returnUri = ContentUris.withAppendedId(Var.CONTENT_URI, id);
        }
        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mHelper.getWritableDatabase();

        // Keep track of the number of deleted tasks
        // starts as 0
        int tasksDeleted = 0;

        //  Write the code to delete a single row of data
        if (All == sUriMatcher.match(uri)) {
            // Use selections/selectionArgs
            tasksDeleted = db.delete(DBSQLiteHelper.TABLE_NAME, selection, selectionArgs);

        }

        // Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
