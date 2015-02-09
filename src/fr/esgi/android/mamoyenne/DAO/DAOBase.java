package fr.esgi.android.mamoyenne.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import fr.esgi.android.mamoyenne.R;

public class DAOBase {
	
	// Nous sommes à la première version de la base
	  // Si je décide de la mettre à jour, il faudra changer cet attribut
	  protected final static int VERSION = 2;
	  // Le nom du fichier qui représente ma base
	  protected final static String NOM = "database.db";
	    
	  protected SQLiteDatabase mDb = null;
	  protected DatabaseHandler mHandler = null;
	  protected CursorFactory factory = null;
	    
	  public DAOBase(Context pContext) {
	    this.mHandler = new DatabaseHandler(pContext, NOM, factory, VERSION);
	  }
	    
	  public SQLiteDatabase open() {
	    // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
	    mDb = mHandler.getWritableDatabase();
	    return mDb;
	  }
	    
	  public void close() {
	    mDb.close();
	  }
	    
	  public SQLiteDatabase getDb() {
	    return mDb;
	}
}
