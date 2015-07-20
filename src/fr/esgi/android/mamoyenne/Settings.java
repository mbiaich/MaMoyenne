package fr.esgi.android.mamoyenne;

import java.util.Locale;

import fr.esgi.android.mamoyenne.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Settings extends Activity {

	String selectedLanguage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		// get action bar
		ActionBar actionBar = getActionBar();
		// Enabling Up / Back navigation
		actionBar.setTitle(R.string.action_settings);
	}

	public void annulerModificationLangue(View v) {
		finish();
	}

	public void validerLangue(View v) {
		onRadioButtonClicked(v);

	}

	public String setLanguageSelected(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radioButtonAR:
			if (checked) {
				selectedLanguage = "ar";

			}
			break;
		case R.id.radioButtonEN:
			if (checked) {
				selectedLanguage = "en";
			}
			break;

		case R.id.radioButtonFR:
			if (checked) {
				selectedLanguage = "fr";
			}
			break;

		}
		return selectedLanguage;
	}

	public void onRadioButtonClicked(View view) {

		Locale locale = new Locale(selectedLanguage);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
}