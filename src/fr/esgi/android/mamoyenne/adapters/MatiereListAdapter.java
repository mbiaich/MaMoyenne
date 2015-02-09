package fr.esgi.android.mamoyenne.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.esgi.android.mamoyenne.R;
import fr.esgi.android.mamoyenne.tables.Matiere;


public class MatiereListAdapter extends BaseAdapter {

	private Context context;
	private List<Matiere> matieres;
	private LayoutInflater inflater;
	
	public MatiereListAdapter(Context context, List<Matiere> matieres) {
		this.context = context;
		this.matieres = matieres;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return matieres.size();
	}

	@Override
	public Object getItem(int position) {
		return matieres.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Matiere matiere = (Matiere) getItem(position);
		convertView = inflater.inflate(R.layout.list_view_matiere, null);
		TextView txtRowListMatiere = (TextView) convertView.findViewById(R.id.txt_row_liste_matiere);
		txtRowListMatiere.setText(matiere.getNom());
		return convertView;
	}

}
