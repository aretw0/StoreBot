package br.com.r2dev2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Dao extends SQLiteOpenHelper{
	private final String ID_LOG = "DAO";
	private SQLiteDatabase db;
	
	public Dao(Context context) {
		super(context, InitDB.DATABASE_NAME, null, InitDB.VERSION);
	}
	
	private Dao Open() {
		db = this.getWritableDatabase(); // Diz ao Android que o banco sera utilizado para leitura e escrita de dados
		return this;
	}
	
	private void Close() {
		if (db.isOpen()) {
			db.close();	
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		execute(InitDB.onCreate(), "criação", "criadas");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		execute(InitDB.onUpgrade(), "atualização", "atualizadas");
	}
	
	private void execute(String scriptSQL, String operationB, String operationE){
		Log.i(ID_LOG, "Iniciando a " + operationB + " das tabelas");
		try {
			db.beginTransaction();
			db.execSQL(scriptSQL);
			db.setTransactionSuccessful();	
		} finally {						
			db.endTransaction();			
		}
		Log.i(ID_LOG, "Tabelas " + operationE);
	}
	
	public long insert(String tabela, ContentValues values) {
		long result = 0; // Linhas Inseridas
		this.Open();
		try {
			Log.i(ID_LOG, "Inserindo registro");
			result = db.insert(tabela, null, values);
			Log.i(ID_LOG, "Linhas inseridas: " + String.valueOf(result));	
		} finally {
			this.Close();
		}
		return result;
	}
	
	public Cursor select(String tabela, String campos[], String where, String[] whereArgs, String groupBy, String having, String orderBy) {
		Cursor c = null;
		this.Open();
		try {
			Log.i(ID_LOG, "Iniciando Busca");
			this.Open();
			//db = this.getReadableDatabase();
			c = db.query(tabela, campos, where, whereArgs, groupBy, having, orderBy);
			if(c != null){
	            c.moveToFirst();
	        }
			Log.i(ID_LOG, "Busca realizada. Total de registros: " + c.getCount());
		} finally {			
			this.Close();
		}
		return c;
	}
	
	public long update(String tabela, ContentValues values, String where, String[] whereArgs) {
		long result = 0; //Linhas Alteradas 
		this.Open();
		try {
			Log.i(ID_LOG, "Alterando registro(s)");
			result = db.update(tabela, values, where, whereArgs);
		} finally {
			this.Close();
			Log.i(ID_LOG, "Linha(s) alterada(s): " + String.valueOf(result));
		}
		return result;
	}	
	
	public int delete(String tabela, String where, String[] whereArgs) {		
		int result = 0; //Linhas Excluidas
		this.Open();		
		try {
			Log.i(ID_LOG, "Iniciando exclusão");
			this.Open();
			db = this.getReadableDatabase();
			result = db.delete(tabela, where, whereArgs);
			Log.i(ID_LOG, "Registro excluido(s): " + String.valueOf(result));
		} finally {
			this.Close();
		}
		return result;
	}
	
}
