package fr.esgi.android.mamoyenne.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	public static final String MATIERE_TABLE_NAME = "Matiere";
	public static final String MATIERE_ID = "idMatiere";
	public static final String MATIERE_NOM = "nom";
	public static final String MATIERE_COEFFICIENT = "coefficient";
	public static final String MATIERE_TABLE_CREATE = "CREATE TABLE " + 
			MATIERE_TABLE_NAME + " (" +
			MATIERE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			MATIERE_NOM + " TEXT, " +
			MATIERE_COEFFICIENT + " REAL);";
	public static final String MATIERE_TABLE_DROP = "DROP TABLE IF EXISTS " + MATIERE_TABLE_NAME + ";";
	
	public static final String NOTE_TABLE_NAME = "Note";
	public static final String NOTE_ID = "idNote";
	public static final String NOTE_NOTE = "note";
	public static final String NOTE_COEFFICIENT = "coefficient";
	public static final String NOTE_TYPEEXAMEN = "typeExamen";
	public static final String NOTE_IDMATIERE = "idMatiere";
	public static final String NOTE_TABLE_CREATE = "CREATE TABLE " +
			NOTE_TABLE_NAME + " (" +
			NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			NOTE_NOTE + " REAL, " +
			NOTE_COEFFICIENT + " REAL, " +
			NOTE_TYPEEXAMEN + " TEXT, " +
			NOTE_IDMATIERE + " INTEGER," +
			" FOREIGN KEY (" + NOTE_IDMATIERE + ") REFERENCES " + MATIERE_TABLE_NAME + " (" + MATIERE_ID + "));";
	public static final String NOTE_TABLE_DROP = "DROP TABLE IF EXISTS " + NOTE_TABLE_NAME + ";";
	
	public DatabaseHandler(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MATIERE_TABLE_CREATE);
		db.execSQL(NOTE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(MATIERE_TABLE_DROP);
		db.execSQL(NOTE_TABLE_DROP);
		onCreate(db);
	}

}
