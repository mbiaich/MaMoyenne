package fr.esgi.android.mamoyenne.DAO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.esgi.android.mamoyenne.tables.Matiere;
import fr.esgi.android.mamoyenne.tables.Note;

public class NoteDAO extends DAOBase {

	public NoteDAO(Context pContext) {
		super(pContext);
	}

	MatiereDAO matiereDao;
	
	public static final String TABLE_NAME = "note";
	public static final String IDNOTE = "idNote";
	public static final String NOTE = "note";
	public static final String COEFFICIENT = "coefficient";
	public static final String TYPEEXAMEN = "typeExamen";
	public static final String IDMATIERE = "idMatiere";

	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (" + IDNOTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTE
			+ " REAL, " + COEFFICIENT + " REAL, " + TYPEEXAMEN + " TEXT, "
			+ IDMATIERE + " INTEGER, " + "FOREIGN KEY (" + IDMATIERE
			+ ") REFERENCES matiere (idMatiere))";

	public static final String TABLE_DROP = "DROP TABLE IF EXISTS "
			+ TABLE_NAME + ";";

	public void createNote(Note n) {
		ContentValues value = new ContentValues();
		value.put(NOTE, n.getNote());
		value.put(COEFFICIENT, n.getCoefficient());
		value.put(TYPEEXAMEN, n.getTypeExamen());
		value.put(IDMATIERE, n.getIdMatiere());
		mDb.insert(TABLE_NAME, IDNOTE, value);
	}

	public void deleteNote(long id) {
		mDb.delete(TABLE_NAME, IDNOTE + " = ?",
				new String[] { String.valueOf(id) });
	}

	public void updateNote(Note n) {
		ContentValues value = new ContentValues();
		value.put(NOTE, n.getNote());
		value.put(COEFFICIENT, n.getCoefficient());
		value.put(TYPEEXAMEN, n.getTypeExamen());
		value.put(IDMATIERE, n.getIdMatiere());
		mDb.update(TABLE_NAME, value, IDNOTE + " = ?",
				new String[] { String.valueOf(n.getIdNote()) });
	}

	public Note getNote(int idNote) {
		Note n = new Note();
		Cursor cursor = mDb.rawQuery(
				"SELECT idNote, note, coefficient, typeExamen, idMatiere "
						+ " from " + TABLE_NAME + " where idNote = ?",
				new String[] { String.valueOf(idNote) });
		if (cursor.moveToFirst()) {
			n = cursorToNote(cursor);
		}
		cursor.close();
		return n;
	}

	public List<Note> getNotes() {
		List<Note> notes = new ArrayList<Note>();
		Cursor cursor = mDb.rawQuery(
				"SELECT idNote, note, coefficient, typeExamen, idMatiere "
						+ " from " + TABLE_NAME, null);
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Note n = cursorToNote(cursor);
				notes.add(n);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return notes;
	}

	public List<Note> getNotes(long idMatiere) {
		List<Note> notes = new ArrayList<Note>();
		Cursor cursor = mDb.rawQuery(
				"SELECT idNote, note, coefficient, typeExamen, idMatiere FROM "
						+ TABLE_NAME + " WHERE idMatiere = ?",
				new String[] { Long.toString(idMatiere) });
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Note n = cursorToNote(cursor);
				notes.add(n);
				cursor.moveToNext();
			}
		}
		return notes;
	}

	private Note cursorToNote(Cursor cursor) {
		Note n = new Note();
		n.setIdNote(Integer.parseInt(cursor.getString(0)));
		n.setNote(Float.parseFloat(cursor.getString(1)));
		n.setCoefficient(Float.parseFloat(cursor.getString(2)));
		n.setTypeExamen(cursor.getString(3));
		n.setIdMatiere(Integer.parseInt(cursor.getString(4)));
		return n;
	}
	
	public float getMoyenneByMatiere(long idMatiere){
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		List<Note> notes = getNotes(idMatiere);
		float cumulNoteAvecCoeff = 0;
		float cumulCoeff = 0;
		for(int i=0; i<notes.size();i++){	
			Note n = notes.get(i);
			cumulNoteAvecCoeff += n.getNote()*n.getCoefficient();
			cumulCoeff += n.getCoefficient();
		}		
		return Float.parseFloat(df.format(cumulNoteAvecCoeff/cumulCoeff));		
	}
	
}
