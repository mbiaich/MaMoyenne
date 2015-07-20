package fr.esgi.android.mamoyenne;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import fr.esgi.android.mamoyenne.DAO.MatiereDAO;
import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import fr.esgi.android.mamoyenne.tables.Matiere;
import fr.esgi.android.mamoyenne.tables.Note;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private NoteDAO noteDao;
	private MatiereDAO matiereDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		noteDao = new NoteDAO(this);
		noteDao.open();

		matiereDao = new MatiereDAO(this);
		matiereDao.open();

		setContentView(R.layout.activity_main);

		TextView newTextMoyenne = (TextView) findViewById(R.id.myenneLabel);
		newTextMoyenne.setText(noteDao.getMoyenneGenerale(this).toString()
				+ " " + getString(R.string.mainGeneralMark));
	}

	@Override
	protected void onRestart() {
		refresh();
		super.onRestart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		getMenuInflater().inflate(R.menu.activity_main_actions, menu);
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setSettings() {
		this.startActivity(new Intent(this, Settings.class));
	}

	/**
	 * Launching new activity
	 * */
	private void HelpFound() {
		this.startActivity(new Intent(this, Help.class));
	}

	/**
	 * Launching new activity
	 * */
	private void AboutFound() {
		this.startActivity(new Intent(this, About.class));
	}

	public void onClick(View v) {
		this.startActivity(new Intent(this, ListeDesMatieres.class));
	}

	public void refresh() {
		TextView newTextMoyenne = (TextView) findViewById(R.id.myenneLabel);
		newTextMoyenne.setText(noteDao.getMoyenneGenerale(this).toString()
				+ " " + getString(R.string.mainGeneralMark));
	}

	@Override
	public void onDestroy() {
		noteDao.close();
		super.onDestroy();
	}

	public void export(View v) {
		List<Matiere> matieres = matiereDao.getMatieres();
		String fileName = "MaMoyenne.xls";

		// Saving file in external storage
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/MaMoyenne");

		// create directory if not exist
		if (!directory.isDirectory()) {
			directory.mkdirs();
		}

		// file path
		File file = new File(directory, fileName);
		Uri uri = Uri.fromFile(file);
		System.out.println(file.getAbsolutePath());

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("fr", "FR"));
		WritableWorkbook workbook;

		try {
			workbook = Workbook.createWorkbook(file, wbSettings);
			// Excel sheet name. 0 represents first sheet
			WritableSheet sheet = workbook.createSheet("Liste des matières", 0);

			try {

				sheet.addCell(new Label(0, 0, "Nom de la matière")); // column
																		// and
																		// row
				sheet.addCell(new Label(1, 0, "Coefficient"));
				sheet.addCell(new Label(2, 0, "Moyenne"));

				for (int i = 0; i < matieres.size(); i++) {
					Matiere m = matieres.get(i);
					sheet.addCell(new Label(0, i + 1, m.getNom()));
					sheet.addCell(new Label(1, i + 1, String.valueOf(m
							.getCoefficient())));
					String moyenne = noteDao.getMoyenneByMatiere(m.getIdMatiere());
					sheet.addCell(new Label(2, i + 1, moyenne));
				}

				for (int i = 0; i < matieres.size(); i++) {
					Matiere m = matieres.get(i);
					WritableSheet sheetMatiere = workbook.createSheet(
							m.getNom(), i + 1);
					sheetMatiere.addCell(new Label(0, 0, "Note"));
					sheetMatiere.addCell(new Label(1, 0, "Type d'examen"));
					sheetMatiere.addCell(new Label(2, 0, "Coefficient"));
					List<Note> notes = noteDao.getNotes(m.getIdMatiere());
					for(int j = 0; j < notes.size(); j++) {
						Note n = notes.get(j);
						sheetMatiere.addCell(new Label(0, j + 1, String.valueOf(n.getNote())));
						sheetMatiere.addCell(new Label(1, j + 1, n.getTypeExamen()));
						sheetMatiere.addCell(new Label(2, j + 1, String.valueOf(n.getCoefficient())));
					}
				}
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			workbook.write();
			try {
				workbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("application/excel");
		i.putExtra(Intent.EXTRA_STREAM, uri);
		i.putExtra(Intent.EXTRA_SUBJECT, "Ma Moyenne");
		startActivity(Intent.createChooser(i, "Envoyer : "));
	}
}
