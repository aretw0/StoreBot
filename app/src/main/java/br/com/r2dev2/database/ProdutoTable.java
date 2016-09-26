package br.com.r2dev2.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

/**
 * Created by jdone on 20/09/2016.
 */

public class ProdutoTable {

    public static final String _TABLE = "produtos";
    public static final String ID = "id_produto";
    public static final String DESCRICAO = "descricao";
    public static final String EAN = "ean";
    public static final String TEMFOTO = "tem_foto";
    public static final String FOTO = "foto";
    public static final String DTINSERCAO = "dt_insercao";
    public static final String[] campos =  {ID, DESCRICAO, EAN, TEMFOTO, FOTO, DTINSERCAO};

    public static ContentValues putProdutoValues(String desc, String ean, String temfoto, String foto, String dtinsercao){
        ContentValues values = new ContentValues();
        values.put(DESCRICAO, desc);
        values.put(EAN, ean);
        values.put(TEMFOTO, temfoto);
        values.put(FOTO, foto);
        values.put(DTINSERCAO, dtinsercao);
        return values;
    }

    public static boolean hasProduct(String ean, Dao crud){
        String where = ProdutoTable.EAN + " = " + ean;
        Cursor cursor = crud.select(ProdutoTable._TABLE, ProdutoTable.campos, where, null, null, null, null);

        if (cursor.getCount() > 0)
            return true;

        return false;
    }

    public static boolean hasPhoto(String ean, Dao crud){
        String where = ProdutoTable.EAN + " = " + ean;
        Cursor cursor = crud.select(ProdutoTable._TABLE, ProdutoTable.campos, where, null, null, null, null);
        if ((cursor.getInt(cursor.getColumnIndex(ProdutoTable.TEMFOTO)) == 1))
            return true;

        return false;
    }

}
