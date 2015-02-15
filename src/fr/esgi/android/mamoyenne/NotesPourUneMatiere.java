package fr.esgi.android.mamoyenne;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import fr.esgi.android.mamoyenne.adapters.NoteListAdapter;
import fr.esgi.android.mamoyenne.tables.Note;

public class NotesPourUneMatiere extends ListActivity {

	private NoteDAO NoteDAO;
	private long idMatiere;
	private MatiereDAO matiereDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NoteDAO = new NoteDAO(this);
		NoteDAO.open();
		matiereDao = new MatiereDAO(this);
		matiereDao.open();
		idMatiere = getIntent().getExtras().getLong("idMatiere");
		
		setContentView(R.layout.activity_notes_pour_matiere);
		
		//Affichage de la matière
		TextView newTextMatiere = (TextView) findViewById(R.id.ajoutMatiereLabel);
		newTextMatiere.setText(getString(R.string.markFor) + " " + matiereDao.getMatiere(idMatiere).getNom().toString());
			    
		//Affichage de la moyenne	
		TextView newTextMoyenne = (TextView)findViewById(R.id.moyenneMatiereLabel);
		newTextMoyenne.setText(String.valueOf(NoteDAO.getMoyenneByMatiere(idMatiere)) + " " + getString(R.string.mainGeneralMark));
		
		refresh();
		
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

	public void frmAjoutNote(View v) {
		Intent i = new Intent(this, AjoutNote.class);
		i.putExtra("idMatiere", idMatiere);
		this.startActivity(i);
	}
	
	public void viewDetailsMatiere(View v) {
		Intent i = new Intent(this, DetailsMatiere.class);
		i.putExtra("matiere", matiereDao.getMatiere(idMatiere));
		this.startActivity(i);
	}
	

	public void refresh() {
		ListView listView = (ListView) findViewById(android.R.id.list);
		List<Note> Notes = NoteDAO.getNotes(idMatiere);
		if (!Notes.isEmpty()) {
			NoteListAdapter adapter = new NoteListAdapter(this, Notes);
			setListAdapter(adapter);
		}		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, DetailsNote.class);
		i.putExtra("note", NoteDAO.getNote((int) id));
		startActivity(i);
	}
	
	@Override
	public void onDestroy(){
		NoteDAO.close();
		matiereDao.close();
		super.onDestroy();
	}
}