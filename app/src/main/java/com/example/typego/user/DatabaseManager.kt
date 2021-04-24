package com.example.typego.user

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DatabaseManager(context: Context?) : SQLiteOpenHelper(context, "TypeGo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = "CREATE TABLE " + USER_TABLE + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_LAST_RESULT + " INTEGER, " +
                COLUMN_BEST_RESULT + " INTEGER," +
                COLUMN_AVERAGE_WPM + " REAL," +
                COLUMN_PREFERRED_LANGUAGE + " TEXT)"
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun addUser(user: User): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_USERNAME, user.userName)
        cv.put(COLUMN_PREFERRED_LANGUAGE, user.preferredLanguage)
        val added = db.insert(USER_TABLE, null, cv)
        return if (added == -1L) false else true
    }

    fun getUsers(userName: String): List<User> {
        val userList: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $USER_TABLE WHERE $COLUMN_USERNAME = $userName"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val password = cursor.getString(2)
                val user = User(id, name, password)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        return userList
    }

    fun getUser(userName: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $USER_TABLE WHERE $COLUMN_USERNAME = $userName"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val password = cursor.getString(2)
            return User(id, name, password)
        }
        return null
    }

    companion object {
        const val USER_TABLE = "USER_TABLE"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_LAST_RESULT = "lastResult"
        const val COLUMN_BEST_RESULT = "bestResult"
        const val COLUMN_AVERAGE_WPM = "averageWPM"
        const val COLUMN_PREFERRED_LANGUAGE = "preferredLanguage"
    }
}