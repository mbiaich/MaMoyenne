package fr.esgi.android.mamoyenne;

import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

	NoteDAO noteDao;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        noteDao = new NoteDAO(this);
        noteDao.open();
        
        setContentView(R.layout.activity_main);
        
        TextView newTextMoyenne = (TextView) findViewById(R.id.myenneLabel);
		newTextMoyenne.setText(noteDao.getMoyenneGenerale(this).toString() + " " + getString(R.string.mainGeneralMark));
    }

    @Override
    protected void onRestart() {
    	refresh();
    	super.onRestart();
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
    
    public void onClick(View v) {
    	this.startActivity(new Intent(this, ListeDesMatieres.class));
    }
    
    public void refresh(){
    	TextView newTextMoyenne = (TextView) findViewById(R.id.myenneLabel);
		newTextMoyenne.setText(noteDao.getMoyenneGenerale(this).toString() + " " + getString(R.string.mainGeneralMark));
    }
    
    @Override
	public void onDestroy(){
		noteDao.close();
		super.onDestroy();
	}
}
