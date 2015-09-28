package com.savare.sincronizacao;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Bruno Nogueira Silva on 17/09/2015.
 */
public class EnviarProvider extends ContentProvider {

    private static final String TAG = "SAVARE";

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate do EnviarProvider");
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query do EnviarProvider");

        return null;
    }

    @Override
    public String getType(Uri uri) {
        Log.i(TAG, "getType do EnviarProvider");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(TAG, "insert do EnviarProvider");
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "delete do EnviarProvider");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "update do EnviarProvider");
        return 0;
    }
}
