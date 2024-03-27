package com.example.gestionescolar;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("create table datoAlum(matricula int primary key, nombre text, carrera text, InicioFin text, NoMaterias int)");
        BaseDeDatos.execSQL("create table datoMat(id_materia int primary key, nombre text, carrera text, semestre text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE datoAlum");
        db.execSQL("DROP TABLE datoMat");*/
    }
}
