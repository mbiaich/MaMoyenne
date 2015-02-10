package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import fr.esgi.android.mamoyenne.tables.Matiere;
import fr.esgi.android.mamoyenne.tables.Note;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DetailsMatiere extends Activity {
	
	private MatiereDAO matiereDao;
	private Matiere m;
	
	private String nomMatiere;
	private float coeffMatiere;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_matiere);
        matiereDao = new MatiereDAO(this);
        matiereDao.open();
        m = (Matiere) getIntent().getExtras().get("matiere");
        
        EditText editTextNom = (EditText)findViewById(R.id.nomMatiereInput);
        editTextNom.setText(m.getNom());
        
        EditText editTextCoeff = (EditText)findViewById(R.id.coefficietMatiereInput);
        editTextCoeff.setText(String.valueOf(m.getCoefficient()));
               
    }	
	
	public void deleteMatiere(View v) {
		Intent i = new Intent(this, ListeDesMatieres.class);
    	matiereDao.deleteMatiere(m.getIdMatiere());
		startActivity(i);
		
		Toast.makeText(getApplicationContext(), "Matière supprimée !", Toast.LENGTH_LONG).show();
    }
	
	public void updateMatiere(View v) {
		EditText editTextNom = (EditText)findViewById(R.id.nomMatiereInput);    
        EditText editTextCoeff = (EditText)findViewById(R.id.coefficietMatiereInput);
        
        m.setNom(editTextNom.getText().toString());
        m.setCoefficient(Float.parseFloat(editTextCoeff.getText().toString()));
		
        matiereDao.updateMatiere(m);
		Intent i = new Intent(this, DetailsMatiere.class);
		i.putExtra("matiere", m);
		startActivity(i);
		
		Toast.makeText(getApplicationContext(), "Matière modifiée !", Toast.LENGTH_LONG).show();
    }
	
	
	
	@Override
	public void onDestroy(){
		matiereDao.close();
		super.onDestroy();
	}
	
	
	

}
