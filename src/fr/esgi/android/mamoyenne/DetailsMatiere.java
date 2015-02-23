package fr.esgi.android.mamoyenne;

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
import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.tables.Matiere;

public class DetailsMatiere extends Activity {
	
	private MatiereDAO matiereDao;
	private Matiere m;
	
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
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialogWarning));
		builder.setMessage(getString(R.string.dialogAskDeleteField));
		builder.setPositiveButton(getString(R.string.dialogYes), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {		
				Intent i = new Intent(DetailsMatiere.this, ListeDesMatieres.class);
		    	matiereDao.deleteMatiere(m.getIdMatiere());
				startActivity(i);				
				Toast.makeText(getApplicationContext(), getString(R.string.deleteFieldToast) , Toast.LENGTH_LONG).show();
		    }
		});
		builder.setNegativeButton(getString(R.string.dialogNo), new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		// ne fait rien
		}
		});
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
	}
	
	public void updateMatiere(View v) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialogWarning));
		builder.setMessage(getString(R.string.dialogAskUpdateField));
		builder.setPositiveButton(getString(R.string.dialogYes), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText editTextNom = (EditText)findViewById(R.id.nomMatiereInput);    
		        EditText editTextCoeff = (EditText)findViewById(R.id.coefficietMatiereInput);
		        
		        if (String.valueOf(editTextNom.getText().toString()).isEmpty() || String.valueOf(editTextCoeff.getText()).isEmpty()) {
		    		Toast.makeText(getApplicationContext(), getString(R.string.errorEmptyFieldsToast), Toast.LENGTH_LONG).show();
		    	} else if (Float.parseFloat(editTextCoeff.getText().toString()) > 10 || Float.parseFloat(editTextCoeff.getText().toString()) < 1){
		    		Toast.makeText(getApplicationContext(), getString(R.string.errorCoeffToast), Toast.LENGTH_LONG).show();
		    	} else {    
			        m.setNom(editTextNom.getText().toString());
			        m.setCoefficient(Float.parseFloat(editTextCoeff.getText().toString()));
			        matiereDao.updateMatiere(m);
			        Toast.makeText(getApplicationContext(), getString(R.string.updateFieldToast), Toast.LENGTH_LONG).show();
		    	}
		}});
		builder.setNegativeButton(getString(R.string.dialogNo), new OnClickListener() {
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
		finish();
		super.onStop();
	}
		
	@Override
	public void onDestroy() {
		matiereDao.close();
		super.onDestroy();
		
	}
}
