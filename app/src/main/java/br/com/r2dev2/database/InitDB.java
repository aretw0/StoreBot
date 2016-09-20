package br.com.r2dev2.database;

import android.content.ContentValues;

public class InitDB {
    // ---------------------- //
    public static final String DATABASE_NAME = "StoreBot.db";
    public static final int VERSION = 1;
    // ---------------------- //

    public static String onCreate() {
        String sql = "CREATE TABLE "+ ProdutoTable._TABLE +"("
                + ProdutoTable.ID + " integer primary key autoincrement,"
                + ProdutoTable.DESCRICAO + " varchar(50),"
                + ProdutoTable.EAN + " varchar(13),"
                + ProdutoTable.TEMFOTO + " boolean NOT NULL default 0,"
                + ProdutoTable.FOTO + " text,"
                + ProdutoTable.DTINSERCAO + " date"
                +")";
        return sql;
    }

    public static String onUpgrade() {
        return "DROP TABLE IF EXISTS " + ProdutoTable._TABLE;
    }



}