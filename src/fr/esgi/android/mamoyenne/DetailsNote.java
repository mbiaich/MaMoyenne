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
	private Note n;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);
        noteDao = new NoteDAO(this);
        noteDao.open();
        n = (Note) getIntent().getExtras().get("note");
        
        EditText editTextNom = (EditText)findViewById(R.id.valeurNoteLabel);
        editTextNom.setText(n.getNote().toString());
        
        EditText editTextCoeff = (EditText)findViewById(R.id.valeurCoeffLabel);
        editTextCoeff.setText(String.valueOf(n.getCoefficient()));
        
        EditText editTextTypeExa = (EditText)findViewById(R.id.valeurTypeExam);
        editTextTypeExa.setText(n.getTypeExamen());
               
    }	
	
	public void deleteNote(View v) {
    	noteDao.deleteNote(n.getIdNote());
		Intent i = new Intent(this, NotesPourUneMatiere.class);
		i.putExtra("idMatiere", n.getIdMatiere());
		startActivity(i);
		
		Toast.makeText(getApplicationContext(), "Note supprimée", Toast.LENGTH_LONG).show();
    }
	
	public void updateNote(View v) {
		EditText editTextNom = (EditText)findViewById(R.id.valeurNoteLabel);    
        EditText editTextCoeff = (EditText)findViewById(R.id.valeurCoeffLabel);
		EditText editValeurTypeExa = (EditText)findViewById(R.id.valeurTypeExam);    

        
        n.setNote(Float.parseFloat(editTextNom.getText().toString()));
        n.setCoefficient(Float.parseFloat(editTextCoeff.getText().toString()));
        n.setTypeExamen(editValeurTypeExa.getText().toString());
		
        noteDao.updateNote(n);
		Intent i = new Intent(this, DetailsNote.class);
		i.putExtra("note", n);
		startActivity(i);
		
		Toast.makeText(getApplicationContext(), "Note modifiée !", Toast.LENGTH_LONG).show();
    }
		
	@Override
	public void onDestroy(){
		noteDao.close();
		super.onDestroy();
	}
}
