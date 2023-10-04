package com.example.cruduniminuto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class GestionDB extends SQLiteOpenHelper {

    private static final String DATABASE_USERS = "dbUsers";
    private static final int VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (USU_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " USU_DOCUMENT VARCHAR(15) UNIQUE, USU_USER VARCHAR(50) NOT NULL, USU_NAME VARCHAR(100) NOT NULL," +
            " USU_LASTNAME VARCHAR(100) NOT NULL, USU_PASSWORD VARCHAR(15) NOT NULL)";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_USERS;


    public GestionDB(@Nullable Context context ) {
        super(context, DATABASE_USERS, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        Log.i("BASE DE DATOS", "Se creo la base de datos" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
