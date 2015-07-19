package fr.esgi.android.mamoyenne;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.adapters.MatiereListAdapter;
import fr.esgi.android.mamoyenne.tables.Matiere;

public class ListeDesMatieres extends ListActivity {

	private MatiereDAO matiereDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		matiereDao = new MatiereDAO(this);
		matiereDao.open();
		refresh();
		setContentView(R.layout.activity_liste_matieres);
	}
	

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		refresh();
	}


	public void frmAjoutMatiere(View v) {
		this.startActivity(new Intent(this, AjoutDuneMatiere.class));
	}

	public void refresh() {
		ListView listView = (ListView) findViewById(android.R.id.list);
		List<Matiere> matieres = matiereDao.getMatieres();
		if (!matieres.isEmpty()) {
			MatiereListAdapter adapter = new MatiereListAdapter(this, matieres);
			setListAdapter(adapter);
		}
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, NotesPourUneMatiere.class);
		i.putExtra("idMatiere", id);
		startActivity(i);
	}
	
	@Override
	public void onDestroy(){
		matiereDao.close();
		super.onDestroy();
	}

}