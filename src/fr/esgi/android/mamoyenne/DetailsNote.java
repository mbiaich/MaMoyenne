package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import fr.esgi.android.mamoyenne.tables.Note;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DetailsNote extends Activity {
	
	private NoteDAO noteDao;
	private Note m;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);
        noteDao = new NoteDAO(this);
        noteDao.open();
        m = (Note) getIntent().getExtras().get("note");
        
        EditText editTextNom = (EditText)findViewById(R.id.valeurNoteLabel);
        editTextNom.setText(m.getNote().toString());
        
        EditText editTextCoeff = (EditText)findViewById(R.id.valeurCoeffLabel);
        editTextCoeff.setText(String.valueOf(m.getCoefficient()));
        
        EditText editTextTypeExa = (EditText)findViewById(R.id.valeurTypeExam);
        editTextTypeExa.setText(String.valueOf(m.getCoefficient()));
               
    }	
	
	public void deletenote(View v) {
		Intent i = new Intent(this, ListeDesNotes.class);
    	noteDao.deleteNote(m.getIdNote());
		startActivity(i);
		
		Toast.makeText(getApplicationContext(), "Note supprimée", Toast.LENGTH_LONG).show();
    }
	
	public void updatenote(View v) {
		EditText editTextNom = (EditText)findViewById(R.id.valeurNoteLabel);    
        EditText editTextCoeff = (EditText)findViewById(R.id.valeurCoeffLabel);
		EditText editValeurTypeExa = (EditText)findViewById(R.id.valeurTypeExam);    

        
        m.setNote(Float.parseFloat(editTextNom.getText().toString()));
        m.setCoefficient(Float.parseFloat(editTextCoeff.getText().toString()));
        m.setTypeExamen(editValeurTypeExa.getText().toString());
		
        noteDao.updateNote(m);
		Intent i = new Intent(this, DetailsNote.class);
		i.putExtra("note", m);
		startActivity(i);
		
		Toast.makeText(getApplicationContext(), "Note modifiée !", Toast.LENGTH_LONG).show();
    }
	
	
	
	@Override
	public void onDestroy(){
		noteDao.close();
		super.onDestroy();
	}
	
	
	

}
