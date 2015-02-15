package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.tables.Matiere;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutDuneMatiere extends Activity {

	private MatiereDAO matiereDao;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_ajout_matiere);
	        matiereDao = new MatiereDAO(this);
	        matiereDao.open();
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	    
	    public void createMatiere(View v) {
	    	EditText txtNomMatiere = (EditText) findViewById(R.id.nomMatiereInput);
	    	EditText txtCoefficient = (EditText) findViewById(R.id.coefficietMatiereInput);
	    	Matiere m = new Matiere(txtNomMatiere.getText().toString(), Float.parseFloat(txtCoefficient.getText().toString()));
	    	matiereDao.createMatiere(m);
	    	this.startActivity(new Intent(this, ListeDesMatieres.class));
	    	
	    	Toast.makeText(getApplicationContext(), "Matière ajoutée !", Toast.LENGTH_LONG).show();
	    }

}
