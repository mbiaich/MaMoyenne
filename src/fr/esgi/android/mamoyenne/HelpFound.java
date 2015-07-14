package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
 
public class HelpFound  extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
 
        // get action bar   
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}