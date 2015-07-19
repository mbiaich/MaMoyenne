package fr.esgi.android.mamoyenne;


import fr.esgi.android.mamoyenne.DAO.NoteDAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
        case R.id.ac_accueil:
        	RetourAccueil();
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
    private void setSettings()
    {
    	this.startActivity(new Intent(this, Settings.class));
    }
    
    
    private void RetourAccueil()
    {
    	this.startActivity(new Intent(this, MainActivity.class));
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
        this.startActivity( new Intent(this, About.class));
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
