package fr.esgi.android.mamoyenne.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import fr.esgi.android.mamoyenne.tables.Matiere;

public class MatiereDAO extends DAOBase {

	private NoteDAO noteDao;
	
	public MatiereDAO(Context pContext) {
		super(pContext);
		noteDao = new NoteDAO(pContext);
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
			Log.i(LOGCREATE, "La mati�re : "+m.getNom()+" a �t� cr��e.");
		}
		else {
    		Log.e(LOGCREATE, "La mati�re : "+m.getNom()+" n'a pas �t� cr��e.");
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
			Log.i(LOGUPDATE, "La mati�re : "+m.getNom()+" a �t� modifi�e.");
		}
		else 
		{
    		Log.e(LOGUPDATE, "La mati�re : "+m.getNom()+" n'a pas �t� modifi�e.");
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
				+ " from " + TABLE_NAME + " ORDER BY nom ASC", null);
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
				+ " from " + TABLE_NAME + " where nom like '%" + nomMatiere + "%'", null);
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
	
	public List<Matiere> sortByAlphabet(boolean alphabetSortAsc) {
		List<Matiere> matieres = new ArrayList<Matiere>();
		Cursor cursor;
		if(alphabetSortAsc) {
			cursor = mDb.rawQuery("SELECT idMatiere, nom, coefficient "
					+ " from " + TABLE_NAME + " ORDER BY nom DESC", null);
		} else {
			cursor = mDb.rawQuery("SELECT idMatiere, nom, coefficient "
					+ " from " + TABLE_NAME + " ORDER BY nom ASC", null);
		}
		
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
	
	public List<Matiere> sortByMoyenne(boolean moyenneSortAsc) {
		List<Matiere> matieres = new ArrayList<Matiere>();
		TreeMap<Float, Matiere> matieresMap = new TreeMap<Float, Matiere>();
		Cursor cursor;
		TreeMap<Float, Matiere> newMap;
		noteDao.open();
		
		for (Matiere matiere : getMatieres()) {
			String moyenne = noteDao.getMoyenneByMatiere(matiere.getIdMatiere());
			if(moyenne.equals("")) {
				moyenne = "0.00";
			}
			matieresMap.put(Float.valueOf(moyenne), matiere);
		}
		if(moyenneSortAsc) {
			newMap = new TreeMap<Float, Matiere>(Collections.reverseOrder());
			newMap.putAll(matieresMap);
		} else {
			newMap = new TreeMap<Float, Matiere>();
			newMap.putAll(matieresMap);
		}
		matieres.addAll(newMap.values());
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
