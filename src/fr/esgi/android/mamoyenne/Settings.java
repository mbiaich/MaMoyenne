package fr.esgi.android.mamoyenne;

import java.util.Locale;

import fr.esgi.android.mamoyenne.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Settings extends Activity {

	String selectedLanguage;
	Spinner spinnerctrl;
	Button btn;
	Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		// get action bar
		ActionBar actionBar = getActionBar();
		// Enabling Up / Back navigation
		actionBar.setTitle(R.string.action_settings);
		spinnerctrl = (Spinner) findViewById(R.id.spinner1);
		spinnerctrl.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				if (pos == 1) {

					Toast.makeText(parent.getContext(),
							"Selected language : English", Toast.LENGTH_SHORT)
							.show();
					setLocale("en");
				} else if (pos == 2) {

					Toast.makeText(parent.getContext(),
							"Selected language : Francais", Toast.LENGTH_SHORT)
							.show();
					setLocale("fr");
				} else if (pos == 3) {

					Toast.makeText(parent.getContext(),
							"Selected language : Arabic", Toast.LENGTH_SHORT)
							.show();
					setLocale("ar");
				}

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
	}

	public void setLocale(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		finish();
		Intent i = new Intent(this, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);		
		startActivity(i);
	}

	public void annulerModificationLangue(View v) {
		finish();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
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