package fr.esgi.android.mamoyenne.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import fr.esgi.android.mamoyenne.tables.Matiere;

public class MatiereDAO extends DAOBase {

	public MatiereDAO(Context pContext) {
		super(pContext);
	}

	public static final String TABLE_NAME = "matiere";
	public static final String IDMATIERE = "idMatiere";
	public static final String NOM = "nom";
	public static final String COEFFICIENT = "coefficient";

	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (" + IDMATIERE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM
			+ " TEXT, " + COEFFICIENT + " REAL);";

	public static final String TABLE_DROP = "DROP TABLE IF EXISTS "
			+ TABLE_NAME + ";";
	
	private static final String LOGUPDATE = "MatiereDAO - update";
	private static final String LOGCREATE = "MatiereDAO - create";

	public void createMatiere(Matiere m) {
		
		if (m.getCoefficient() < 11 &&  m.getCoefficient() > 0 && !String.valueOf(m.getCoefficient()).isEmpty() 
				&& !m.getNom().isEmpty())
		{
		ContentValues value = new ContentValues();
		value.put(NOM, m.getNom());
		value.put(COEFFICIENT, m.getCoefficient());
		mDb.insert(TABLE_NAME, IDMATIERE, value);		
			Log.i(LOGCREATE, "La matière : "+m.getNom()+" a été créée.");
		}
		else {
    		Log.e(LOGCREATE, "La matière : "+m.getNom()+" n'a pas été créée.");
    	}
	}

	public void deleteMatiere(long id) {
		mDb.delete("note", IDMATIERE + " = ?", new String[] { String.valueOf(id) });
		mDb.delete(TABLE_NAME, IDMATIERE + " = ?",
				new String[] { String.valueOf(id) });
	}
	
	
	public void updateMatiere(Matiere m) {
		
		if (m.getCoefficient() < 11 &&  m.getCoefficient() > 0 && !String.valueOf(m.getCoefficient()).isEmpty() 
				&& !m.getNom().isEmpty())
		{
			ContentValues value = new ContentValues();
			value.put(NOM, m.getNom());
			value.put(COEFFICIENT, m.getCoefficient());
			mDb.update(TABLE_NAME, value, IDMATIERE + " = ?",
					new String[] { String.valueOf(m.getIdMatiere()) });   		
			Log.i(LOGUPDATE, "La matière : "+m.getNom()+" a été modifiée.");
		}
		else 
		{
    		Log.e(LOGUPDATE, "La matière : "+m.getNom()+" n'a pas été modifiée.");
    	}
	}


	public Matiere getMatiere(long idMatiere) {
		Matiere m = new Matiere();
		Cursor cursor = mDb.rawQuery("SELECT idMatiere, nom, coefficient "
				+ " from " + TABLE_NAME + " where idMatiere = ?",
				new String[] { String.valueOf(idMatiere) });
		if (cursor.moveToFirst()) {
			m = cursorToMatiere(cursor);
		}
		cursor.close();
		return m;
	}

	public List<Matiere> getMatieres() {
		List<Matiere> matieres = new ArrayList<Matiere>();
		Cursor cursor = mDb.rawQuery("SELECT idMatiere, nom, coefficient "
				+ " from " + TABLE_NAME, null);
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Matiere m = cursorToMatiere(cursor);
				matieres.add(m);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return matieres;
	}
	
	public List<Matiere> getMatieresSearch(String nomMatiere) {
		System.out.println(nomMatiere);
		List<Matiere> matieres = new ArrayList<Matiere>();
		Cursor cursor = mDb.rawQuery("SELECT idMatiere, nom, coefficient "
				+ " from " + TABLE_NAME + " where nom = '" + nomMatiere + "'", null);
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Matiere m = cursorToMatiere(cursor);
				matieres.add(m);
				cursor.moveToNext();
			}
		}
		cursor.close();
		System.out.println(matieres.size());
		return matieres;
	}
	
	private Matiere cursorToMatiere(Cursor cursor) {
		Matiere m = new Matiere();
		m.setIdMatiere(Integer.parseInt(cursor.getString(0)));
		m.setNom(cursor.getString(1));
		m.setCoefficient(Float.parseFloat(cursor.getString(2)));
		return m;
	}
	
}
