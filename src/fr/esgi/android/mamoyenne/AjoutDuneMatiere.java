package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.tables.Matiere;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

	public void createMatiere(View v) {
		EditText txtNomMatiere = (EditText) findViewById(R.id.nomMatiereInput);
		EditText txtCoefficient = (EditText) findViewById(R.id.coefficietMatiereInput);

		if (String.valueOf(txtNomMatiere.getText().toString()).isEmpty()
				|| String.valueOf(txtCoefficient.getText()).isEmpty()) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorEmptyFieldsToast),
					Toast.LENGTH_LONG).show();
		} else if (Float.parseFloat(txtCoefficient.getText().toString()) > 10
				|| Float.parseFloat(txtCoefficient.getText().toString()) < 1) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorCoeffToast), Toast.LENGTH_LONG)
					.show();
		} else {
			Matiere m = new Matiere(txtNomMatiere.getText().toString(),
					Float.parseFloat(txtCoefficient.getText().toString()));
			matiereDao.createMatiere(m);
			finish();
			Toast.makeText(getApplicationContext(),
					getString(R.string.createFieldToast), Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	@Override
	public void onDestroy() {
		matiereDao.close();
		super.onDestroy();
	}

}
