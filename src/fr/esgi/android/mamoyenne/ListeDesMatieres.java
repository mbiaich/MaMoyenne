package fr.esgi.android.mamoyenne;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
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
		ListView l = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(l);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.subjectList);

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
	
	public void refreshListWithSearch(View v) {
		
		EditText matiereToSearch = (EditText) findViewById(R.id.matiereAChercher);		
		ListView listView = (ListView) findViewById(android.R.id.list);
				
		String valueMatiereSearch = String.valueOf(matiereToSearch.getText().toString());
		
		List<Matiere> matieres = matiereDao.getMatieresSearch(valueMatiereSearch);
		if (!matieres.isEmpty()) {
			MatiereListAdapter adapter = new MatiereListAdapter(this, matieres);
			setListAdapter(adapter);
		}
		else
		{
			Toast.makeText(getApplicationContext(),
					getString(R.string.searchNoResultSubject), Toast.LENGTH_LONG)
					.show();
		}
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, NotesPourUneMatiere.class);
		i.putExtra("idMatiere", id);
		startActivity(i);
	}

	@Override
	public void onDestroy() {
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
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final Matiere m = (Matiere) getListAdapter().getItem(info.position);
		if (item.getItemId() == R.id.delete) {
			Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.dialogWarning));
			builder.setMessage(getString(R.string.dialogAskDeleteField));
			builder.setPositiveButton(getString(R.string.dialogYes),
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {						
							matiereDao.deleteMatiere(m.getIdMatiere());
							refresh();
							Toast.makeText(getApplicationContext(),
									getString(R.string.deleteFieldToast),
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
			Intent i = new Intent(this, DetailsMatiere.class);
			i.putExtra("matiere", matiereDao.getMatiere(m.getIdMatiere()));
			this.startActivity(i);
		}
		return super.onContextItemSelected(item);
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