package fr.esgi.android.mamoyenne;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import fr.esgi.android.mamoyenne.adapters.NoteListAdapter;
import fr.esgi.android.mamoyenne.tables.Note;

public class ListeDesNotes extends ListActivity {

	private NoteDAO NoteDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NoteDAO = new NoteDAO(this);
		NoteDAO.open();
		refresh();
		setContentView(R.layout.activity_notes_pour_matiere);
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
		List<Note> notes = NoteDAO.getNotes();
		if (!notes.isEmpty()) {
			NoteListAdapter adapter = new NoteListAdapter(this, notes);
			setListAdapter(adapter);
		}
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, NotesPourUneMatiere.class);
		i.putExtra("idNote", id);
		startActivity(i);
	}
	
	@Override
	public void onDestroy(){
		NoteDAO.close();
		super.onDestroy();
	}

}