package fr.esgi.android.mamoyenne;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.tables.Matiere;

public class ListeDesMatieres extends ListActivity {

	private MatiereDAO matiereDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		matiereDao = new MatiereDAO(this);
		matiereDao.open();
//		refresh();
		setContentView(R.layout.activity_liste_matieres);
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

	public void frmAjoutMatiere(View v) {
		this.startActivity(new Intent(this, AjoutDuneMatiere.class));
	}

	public void refresh() {
		ListView listView = (ListView) findViewById(android.R.id.list);
		List<Matiere> matieres = matiereDao.getMatieres();
		if (!matieres.isEmpty()) {

		}
		
	}

}