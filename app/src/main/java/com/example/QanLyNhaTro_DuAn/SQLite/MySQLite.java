package com.example.QanLyNhaTro_DuAn.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLite extends SQLiteOpenHelper {
    public MySQLite(Context context) {
        super(context, "data.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_NGUOIDUNG = " CREATE TABLE NguoiDung(UserName NVARCHAR(30) PRIMARY KEY,Name NVARCHAR(50) , Phone NVARCHAR(20) NOT NULL, Pass NVARCHAR(50))";

        db.execSQL(CREATE_TABLE_NGUOIDUNG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
