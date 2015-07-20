package fr.esgi.android.mamoyenne;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.addMark);
	}

	public void createNote(View v) {
		EditText noteValue = (EditText) findViewById(R.id.noteInput);
		EditText txtCoefficient = (EditText) findViewById(R.id.coefInput);
		EditText txtTypeExamInput = (EditText) findViewById(R.id.typeExamInput);

		if (String.valueOf(noteValue.getText().toString()).isEmpty()
				|| String.valueOf(txtCoefficient.getText()).isEmpty()
				|| String.valueOf(txtTypeExamInput.getText()).isEmpty()) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorEmptyFieldsToast),
					Toast.LENGTH_LONG).show();
		} else if (Float.parseFloat(noteValue.getText().toString()) > 20
				|| Float.parseFloat(noteValue.getText().toString()) < 0) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorMarkToast), Toast.LENGTH_LONG)
					.show();
		} else if (Float.parseFloat(txtCoefficient.getText().toString()) > 10
				|| Float.parseFloat(txtCoefficient.getText().toString()) < 1) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorCoeffToast), Toast.LENGTH_LONG)
					.show();
		} else {
			Note n = new Note(Float.parseFloat(noteValue.getText().toString()),
					Float.parseFloat(txtCoefficient.getText().toString()),
					txtTypeExamInput.getText().toString(), idMatiere);
			noteDao.createNote(n);
			finish();
			Toast.makeText(getApplicationContext(),
					getString(R.string.createMarkToast), Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected void onStop() {
		finish();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		noteDao.close();
		super.onDestroy();
	}

}