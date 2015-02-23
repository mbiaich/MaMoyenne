package fr.esgi.android.mamoyenne.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.esgi.android.mamoyenne.R;
import fr.esgi.android.mamoyenne.tables.Note;


public class NoteListAdapter extends BaseAdapter {

	private Context context;
	private List<Note> notes;
	private LayoutInflater inflater;
	
	public NoteListAdapter(Context context, List<Note> notes) {
		this.context = context;
		this.notes = notes;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return notes.size();
	}

	@Override
	public Object getItem(int position) {
		return notes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return notes.get(position).getIdNote();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Note note = (Note) getItem(position);
		convertView = inflater.inflate(R.layout.list_view_note, null);
		TextView txtRowListNote = (TextView) convertView.findViewById(R.id.txt_row_liste_note);
		txtRowListNote.setTextColor(Color.WHITE);
		txtRowListNote.setText(note.getNote().toString());
		return convertView;
	}
}
