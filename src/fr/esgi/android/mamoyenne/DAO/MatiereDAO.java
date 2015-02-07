package fr.esgi.android.mamoyenne.DAO;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.esgi.android.mamoyenne.R;
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

	public void createMatiere(Matiere m) {
		ContentValues value = new ContentValues();
		value.put(NOM, m.getNom());
		value.put(COEFFICIENT, m.getCoefficient());
		mDb.insert(TABLE_NAME, IDMATIERE, value);
	}

	public void deleteMatiere(long id) {
		mDb.delete(TABLE_NAME, IDMATIERE + " = ?",
				new String[] { String.valueOf(id) });
	}

	public Matiere getMatiere(int idMatiere) {
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
	
	private Matiere cursorToMatiere(Cursor cursor) {
		Matiere m = new Matiere();
		m.setIdMatiere(Integer.parseInt(cursor.getString(0)));
		m.setNom(cursor.getString(1));
		m.setCoefficient(Float.parseFloat(cursor.getString(2)));
		return m;
	}
}
