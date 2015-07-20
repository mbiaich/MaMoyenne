package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
 
public class Help  extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
 
      
        ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.help);
    }
}