package fr.esgi.android.mamoyenne;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import fr.esgi.android.mamoyenne.adapters.MatiereListAdapter;
import fr.esgi.android.mamoyenne.adapters.NoteListAdapter;
import fr.esgi.android.mamoyenne.tables.Matiere;
import fr.esgi.android.mamoyenne.tables.Note;

public class NotesPourUneMatiere extends ListActivity {

	private NoteDAO noteDao;
	private long idMatiere;
	private MatiereDAO matiereDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteDao = new NoteDAO(this);
		noteDao.open();
		matiereDao = new MatiereDAO(this);
		matiereDao.open();
		idMatiere = getIntent().getExtras().getLong("idMatiere");

		ActionBar actionBar = getActionBar();
		actionBar.setTitle(getString(R.string.markFor) + " "
				+ matiereDao.getMatiere(idMatiere).getNom().toString());

		setContentView(R.layout.activity_notes_pour_matiere);
		ListView l = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(l);

		// Affichage de la moyenne
		TextView newTextMoyenne = (TextView) findViewById(R.id.moyenneMatiereLabel);
		newTextMoyenne.setText(noteDao.getMoyenneByMatiere(idMatiere) + " "
				+ getString(R.string.mainGeneralMark));

		refresh();

	}

	@Override
	protected void onRestart() {
		refresh();
		super.onRestart();

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

	public void frmAjoutNote(View v) {
		Intent i = new Intent(this, AjoutNote.class);
		i.putExtra("idMatiere", idMatiere);
		this.startActivity(i);
	}

	public void refresh() {
		ListView listView = (ListView) findViewById(android.R.id.list);
		List<Note> Notes = noteDao.getNotes(idMatiere);
		if (!Notes.isEmpty()) {
			NoteListAdapter adapter = new NoteListAdapter(this, Notes);
			setListAdapter(adapter);
		}

		TextView newTextMoyenne = (TextView) findViewById(R.id.moyenneMatiereLabel);
		newTextMoyenne.setText(noteDao.getMoyenneByMatiere(idMatiere) + " "
				+ getString(R.string.mainGeneralMark));
	}
	
	public void refreshListWithSearch(View v) {
		
		EditText typeNoteToSearch = (EditText) findViewById(R.id.typeNoteAChercher);		
		ListView listView = (ListView) findViewById(android.R.id.list);
				
		String valueTypeNoteSearch = String.valueOf(typeNoteToSearch.getText().toString());
		
		List<Note> Notes = noteDao.getNotesSearch(valueTypeNoteSearch, idMatiere);
		if (!Notes.isEmpty()) {
			NoteListAdapter adapter = new NoteListAdapter(this, Notes);
			setListAdapter(adapter);
		}
		else
		{
			Toast.makeText(getApplicationContext(),
					getString(R.string.searchNoResultMark), Toast.LENGTH_LONG)
					.show();
		}
		
	}

	/*
	 * @Override protected void onListItemClick(ListView l, View v, int
	 * position, long id) { Intent i = new Intent(this, DetailsNote.class);
	 * i.putExtra("note", noteDao.getNote((int) id)); startActivity(i); }
	 */

	@Override
	public void onDestroy() {
		noteDao.close();
		matiereDao.close();
		super.onDestroy();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_context, menu);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final Note n = (Note) getListAdapter().getItem(info.position);
		if (item.getItemId() == R.id.delete) {
			Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.dialogWarning));
			builder.setMessage(getString(R.string.dialogAskDeleteMark));
			builder.setPositiveButton(getString(R.string.dialogYes),
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							noteDao.deleteNote(n.getIdNote());							
							refresh();
							Toast.makeText(getApplicationContext(),
									getString(R.string.deletedMark),
									Toast.LENGTH_LONG).show();
						}
					});
			builder.setNegativeButton(getString(R.string.dialogNo),
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// ne fait rien
						}
					});
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.show();
		} else if (item.getItemId() == R.id.edit) {
			Intent i = new Intent(this, DetailsNote.class);
			i.putExtra("note", noteDao.getNote((int) n.getIdNote()));
			startActivity(i);
		}
		return super.onContextItemSelected(item);
	}
}