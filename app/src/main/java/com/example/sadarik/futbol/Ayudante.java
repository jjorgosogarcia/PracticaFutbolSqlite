package com.example.sadarik.futbol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "futbol.sqlite";
    public static final int DATABASE_VERSION = 2;

    public Ayudante (Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table " + Contrato.TablaPartido.TABLA +
                " (" + Contrato.TablaPartido._ID +
                " integer primary key autoincrement , " +
                Contrato.TablaPartido.IDJUGADOR + " integer, " +
                Contrato.TablaPartido.CONTRINCANTE + " text, " +
                Contrato.TablaPartido.VALORACION + " integer," +
                " unique ("+Contrato.TablaPartido.IDJUGADOR+","+Contrato.TablaPartido.CONTRINCANTE+"))";
        db.execSQL(sql);

        sql = "create table "+ Contrato.TablaJugador2.TABLA +
                " (" + Contrato.TablaJugador2._ID +
                " integer primary key autoincrement, " +
                Contrato.TablaJugador2.NOMBRE + " text unique, " +
                Contrato.TablaJugador2.TELEFONO + " text, " +
                Contrato.TablaJugador2.FNAC + " text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar sin perder datos

        // 1º creamos tablas de respaldo (idénticas)
        String sql = "create table respaldo"+ Contrato.TablaJugador.TABLA +
                " (" + Contrato.TablaJugador._ID +
                " integer primary key, " +
                Contrato.TablaJugador.NOMBRE + " text, " +
                Contrato.TablaJugador.TELEFONO + " text unique, " +
                Contrato.TablaJugador.VALORACION+ " integer, "+
                Contrato.TablaJugador.FNAC + " text)";
        db.execSQL(sql);
        // 2º copiamos los datos que tenemos en esas tablas
        sql= "insert into respaldo"+Contrato.TablaJugador.TABLA+" select * from "+Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        // 3º Borramos las tablas originales
        sql = "drop table "+Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        // 4º creamos las tablas nuevas
        onCreate(db);
        // 5º copiamos los datos de las tablas de respaldo en mis tablas
        sql= "insert into "+Contrato.TablaJugador2.TABLA+" select "
                +Contrato.TablaJugador._ID+","+Contrato.TablaJugador.NOMBRE+","+Contrato.TablaJugador.TELEFONO+","+
                Contrato.TablaJugador.FNAC +" from respaldo"+Contrato.TablaJugador.TABLA;
        db.execSQL(sql);

        sql = "insert into "+Contrato.TablaPartido.TABLA+"("+ Contrato.TablaPartido.IDJUGADOR +","+Contrato.TablaPartido.VALORACION+") select "+Contrato.TablaJugador._ID+","
                +Contrato.TablaJugador.VALORACION+" from respaldo"+Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        sql = "update "+Contrato.TablaPartido.TABLA+" set "+Contrato.TablaPartido.CONTRINCANTE+"='inicial'";
        db.execSQL(sql);
        // 6º borramos las tablas de respaldo.
        sql="drop table respaldo"+Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
    }

}