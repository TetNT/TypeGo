package com.example.typego;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_LAST_RESULT = "lastResult";
    public static final String COLUMN_BEST_RESULT = "bestResult";
    public static final String COLUMN_AVERAGE_WPM = "averageWPM";
    public static final String COLUMN_PREFERRED_LANGUAGE = "preferredLanguage";

    public DatabaseManager(@Nullable Context context) {
        super(context, "TypeGo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + USER_TABLE + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_LAST_RESULT + " INTEGER, " +
                COLUMN_BEST_RESULT + " INTEGER," +
                COLUMN_AVERAGE_WPM + " REAL," +
                COLUMN_PREFERRED_LANGUAGE + " TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getUserName());
        cv.put(COLUMN_PREFERRED_LANGUAGE, user.getPreferredLanguage());
        long added = db.insert(USER_TABLE, null, cv);
        if (added == -1) return false;
        return true;
    }


    public List<UserModel> getUsers(String userName) {
        List<UserModel> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USERNAME + " = " + userName;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
             do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String password = cursor.getString(2);
                UserModel user = new UserModel(id, name, password);
                userList.add(user);

            } while (cursor.moveToNext());
        }
        return userList;
    }

    public UserModel getUser(String userName) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USERNAME + " = " + userName;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            UserModel user = new UserModel(id, name, password);
            return user;
        }
        return null;
    }
}
