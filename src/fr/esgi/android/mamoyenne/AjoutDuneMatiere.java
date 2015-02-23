package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.tables.Matiere;
import android.app.Activity;
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
	    	
	    	if (String.valueOf(txtNomMatiere.getText().toString()).isEmpty() || String.valueOf(txtCoefficient.getText()).isEmpty()) {
	    		Toast.makeText(getApplicationContext(), "Erreur ! Veuillez remplir tous les champs.", Toast.LENGTH_LONG).show();
	    	} else if (Float.parseFloat(txtCoefficient.getText().toString()) > 10 || Float.parseFloat(txtCoefficient.getText().toString()) < 1){
	    		Toast.makeText(getApplicationContext(), "Erreur ! Veuillez saisir un coefficient compris entre 1 et 10", Toast.LENGTH_LONG).show();
	    	} else {
	    	Matiere m = new Matiere(txtNomMatiere.getText().toString(), Float.parseFloat(txtCoefficient.getText().toString()));
	    	matiereDao.createMatiere(m);
	    	finish();	    	
	    	Toast.makeText(getApplicationContext(), "Mati�re cr�� !", Toast.LENGTH_LONG).show();
	    	
	    	
	    	
	    	}
	    }
	    
	    @Override
		protected void onStop() {
			super.onStop();
			finish();
		}
			
		@Override
		public void onDestroy(){
			matiereDao.close();
			super.onDestroy();
		}

}
