package fr.esgi.android.mamoyenne.DAO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
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
	
	private static final String LOGUPDATE = "NoteDAO - update";
	private static final String LOGCREATE = "NoteDAO - create";

	public void createNote(Note n) {
		
		if (n.getNote() < 21 && n.getNote() >=0 && n.getCoefficient() < 11 &&  n.getCoefficient() > 0 
				&& !n.getNote().toString().isEmpty() && !String.valueOf(n.getCoefficient()).isEmpty() && 
				!n.getTypeExamen().isEmpty())
		{
			ContentValues value = new ContentValues();
			value.put(NOTE, n.getNote());
			value.put(COEFFICIENT, n.getCoefficient());
			value.put(TYPEEXAMEN, n.getTypeExamen());
			value.put(IDMATIERE, n.getIdMatiere());
			mDb.insert(TABLE_NAME, IDNOTE, value);
			Log.i(LOGCREATE, "La note : "+n.getNote()+" avec pour ID : "+n.getIdNote()+" a été créée.");
		}
		else
		{
			Log.e(LOGCREATE, "La note : "+n.getNote()+" avec pour ID : "+n.getIdNote()+" n'a pas été créée.");
		}
	}

	public void deleteNote(long id) {
		mDb.delete(TABLE_NAME, IDNOTE + " = ?",
				new String[] { String.valueOf(id) });
	}

	public void updateNote(Note n) {
		
		if (n.getNote() < 21 && n.getNote() >=0 && n.getCoefficient() < 11 &&  n.getCoefficient() > 0 
				&& !n.getNote().toString().isEmpty() && !String.valueOf(n.getCoefficient()).isEmpty() && 
				!n.getTypeExamen().isEmpty())
		{
			ContentValues value = new ContentValues();
			value.put(NOTE, n.getNote());
			value.put(COEFFICIENT, n.getCoefficient());
			value.put(TYPEEXAMEN, n.getTypeExamen());
			value.put(IDMATIERE, n.getIdMatiere());
			mDb.update(TABLE_NAME, value, IDNOTE + " = ?",
					new String[] { String.valueOf(n.getIdNote()) });
			Log.i(LOGUPDATE, "La note : "+n.getNote()+" avec pour ID : "+n.getIdNote()+" a été modifiée.");
		}
		else
		{
			Log.e(LOGUPDATE, "La note : "+n.getNote()+" avec pour ID : "+n.getIdNote()+" n'a pas été modifiée.");
		}
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
	
	public List<Note> getNotesSearch(String typeNote, long idMatiere) {
		List<Note> notes = new ArrayList<Note>();
		Cursor cursor = mDb.rawQuery(
				"SELECT idNote, note, coefficient, typeExamen, idMatiere FROM "
						+ TABLE_NAME + " WHERE typeExamen like '%" + typeNote + "%' AND idMatiere = ?",
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
	
	public String getMoyenneByMatiere(long idMatiere){
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		List<Note> notes = getNotes(idMatiere);
		float cumulNoteAvecCoeff = 0;
		float cumulCoeff = 0;
		String moyMatiere;
		for(int i=0; i<notes.size();i++){	
			Note n = notes.get(i);
			cumulNoteAvecCoeff += n.getNote()*n.getCoefficient();
			cumulCoeff += n.getCoefficient();
		}		
		if(df.format(cumulNoteAvecCoeff/cumulCoeff).contains("NaN")){
			moyMatiere = "";
		}
		else {
			moyMatiere = df.format(cumulNoteAvecCoeff/cumulCoeff);
		}
		
		return moyMatiere.replace(",", ".");
			
	}
	
	public String getMoyenneGenerale(Context pContext){
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		matiereDao = new MatiereDAO(pContext);
		matiereDao.open();
		
		List<Matiere> matieres = matiereDao.getMatieres();
		float cumulMoyenneAvecCoeff = 0;
		float cumulCoeff = 0;
		String moyGenerale;
		for(int i=0; i<matieres.size();i++){	
			Matiere m = matieres.get(i);
			long idMatiere = m.getIdMatiere();
			if(getMoyenneByMatiere(idMatiere).isEmpty())
			{
				i++;
			}
			else
			{
				cumulMoyenneAvecCoeff += Float.valueOf(getMoyenneByMatiere(idMatiere))*m.getCoefficient();
				cumulCoeff += m.getCoefficient();
			}
		}		
		if(df.format(cumulMoyenneAvecCoeff/cumulCoeff).contains("NaN")){
			moyGenerale = "";
		}
		else {
			moyGenerale = df.format(cumulMoyenneAvecCoeff/cumulCoeff);
		}
		
		return moyGenerale.replace(",", ".");
	}
	
}
