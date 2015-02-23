package fr.esgi.android.mamoyenne;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import fr.esgi.android.mamoyenne.tables.Note;

public class AjoutNote extends Activity {

	private NoteDAO noteDao;
	private Long idMatiere;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_ajout_note);
	        noteDao = new NoteDAO(this);
	        noteDao.open();
	        idMatiere = getIntent().getExtras().getLong("idMatiere");
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
	    
	    public void createNote(View v) {
	    	EditText noteValue = (EditText) findViewById(R.id.noteInput);
	    	EditText txtCoefficient = (EditText) findViewById(R.id.coefInput);
	    	EditText txtTypeExamInput = (EditText) findViewById(R.id.typeExamInput);
	    	
	    	if (String.valueOf(noteValue.getText().toString()).isEmpty() || String.valueOf(txtCoefficient.getText()).isEmpty() || String.valueOf(txtTypeExamInput.getText()).isEmpty() ){
	    		Toast.makeText(getApplicationContext(), getString(R.string.errorEmptyFieldsToast), Toast.LENGTH_LONG).show();
	    	} else if(Float.parseFloat(noteValue.getText().toString()) > 20 || Float.parseFloat(noteValue.getText().toString()) < 0){
	    		Toast.makeText(getApplicationContext(), getString(R.string.errorMarkToast), Toast.LENGTH_LONG).show();
	    	} else if (Float.parseFloat(txtCoefficient.getText().toString()) > 10 || Float.parseFloat(txtCoefficient.getText().toString()) < 1){
	    		Toast.makeText(getApplicationContext(), getString(R.string.errorCoeffToast), Toast.LENGTH_LONG).show();
	    	} else {
	    		Note n = new Note(Float.parseFloat(noteValue.getText().toString()),Float.parseFloat(txtCoefficient.getText().toString()),txtTypeExamInput.getText().toString(),idMatiere);
		    	noteDao.createNote(n);
				finish();
				Toast.makeText(getApplicationContext(), getString(R.string.createMarkToast), Toast.LENGTH_LONG).show();
	    	}	    	
	    }
	    
	    @Override
		protected void onStop() {	
	    	finish();
			super.onStop();
		}
	    
	    @Override
		public void onDestroy(){
	    	noteDao.close();
			super.onDestroy();
		}

}