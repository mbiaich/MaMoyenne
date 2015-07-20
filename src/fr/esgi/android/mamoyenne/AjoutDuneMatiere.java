package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.tables.Matiere;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.addSubject);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		getMenuInflater().inflate(R.menu.menu_ecran_standard, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_about:
			AboutFound();
			return true;
		case R.id.action_help:
			// location found
			HelpFound();
			return true;
		case R.id.action_settings:
			// refresh
			return true;
		case R.id.ac_parametres:
			setSettings();
			return true;
		case R.id.ac_accueil:
			retourAccueil();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void retourAccueil() {
		this.startActivity(new Intent(this, MainActivity.class));
	}

	private void setSettings() {
		this.startActivity(new Intent(this, Settings.class));
	}

	private void HelpFound() {
		this.startActivity(new Intent(this, Help.class));
	}

	private void AboutFound() {
		this.startActivity(new Intent(this, About.class));
	}

}
