package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import fr.esgi.android.mamoyenne.tables.Note;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Attention");
		builder.setMessage("Êtes-vous sûr de vouloir supprimer cette note ?");
		builder.setPositiveButton("Oui", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	    	noteDao.deleteNote(n.getIdNote());
				Intent i = new Intent(DetailsNote.this, NotesPourUneMatiere.class);
				i.putExtra("idMatiere", n.getIdMatiere());
				startActivity(i);
				
				Toast.makeText(getApplicationContext(), "Note supprimée", Toast.LENGTH_LONG).show();
			}
		});
		builder.setNegativeButton("Non", new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		// ne fait rien
		}
		});
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
    }
	
	public void updateNote(View v) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Attention");
		builder.setMessage("Voulez-vous modifier cette note ?");
		builder.setPositiveButton("Oui", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {		
				EditText editTextNote = (EditText)findViewById(R.id.valeurNoteLabel);    
		        EditText editTextCoeff = (EditText)findViewById(R.id.valeurCoeffLabel);
				EditText editValeurTypeExa = (EditText)findViewById(R.id.valeurTypeExam);    
		
				if (String.valueOf(editTextNote.getText().toString()).isEmpty() || String.valueOf(editTextCoeff.getText()).isEmpty() || String.valueOf(editValeurTypeExa.getText()).isEmpty() ){
		    		Toast.makeText(getApplicationContext(), "Erreur ! Veuillez remplir tous les champs.", Toast.LENGTH_LONG).show();
				} else if(Float.parseFloat(editTextNote.getText().toString()) > 20 || Float.parseFloat(editTextNote.getText().toString()) < 0){
		    		Toast.makeText(getApplicationContext(), "Erreur ! Veuillez saisir une note comprise entre 0 et 20", Toast.LENGTH_LONG).show();
		    	} else if (Float.parseFloat(editTextCoeff.getText().toString()) > 10 || Float.parseFloat(editTextCoeff.getText().toString()) < 1){
		    		Toast.makeText(getApplicationContext(), "Erreur ! Veuillez saisir un coefficient compris entre 1 et 10", Toast.LENGTH_LONG).show();
		    	} 
		    	else {
			        n.setNote(Float.parseFloat(editTextNote.getText().toString()));
			        n.setCoefficient(Float.parseFloat(editTextCoeff.getText().toString()));
			        n.setTypeExamen(editValeurTypeExa.getText().toString());		
			        noteDao.updateNote(n);		
					Toast.makeText(getApplicationContext(), "Note modifiée !", Toast.LENGTH_LONG).show();
		    	}
			}
		});
		builder.setNegativeButton("Non", new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		// ne fait rien
		}
		});
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
    }
	
	@Override
	protected void onStop() {		
		super.onStop();
		finish();
	}
		
	@Override
	public void onDestroy(){
		noteDao.close();
		super.onDestroy();
	}
}
