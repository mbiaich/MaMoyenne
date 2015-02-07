package fr.esgi.android.mamoyenne.tables;

import fr.esgi.android.mamoyenne.R;

public class Note {

	private int idNote;
	private float note;
	private float coefficient;
	private String typeExamen;
	private int idMatiere;
	
	/**
	 * @param idNote
	 * @param note
	 * @param coefficient
	 * @param idMatiere
	 */
	public Note(int idNote, float note, float coefficient, String typeExamen, int idMatiere) {
		this.idNote = idNote;
		this.note = note;
		this.coefficient = coefficient;
		this.typeExamen = typeExamen;
		this.idMatiere = idMatiere;
	}
	
	public Note() {
		
	}

	/**
	 * @return the idNote
	 */
	public int getIdNote() {
		return idNote;
	}

	/**
	 * @param idNote the idNote to set
	 */
	public void setIdNote(int idNote) {
		this.idNote = idNote;
	}

	/**
	 * @return the note
	 */
	public float getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(float note) {
		this.note = note;
	}

	/**
	 * @return the coefficient
	 */
	public float getCoefficient() {
		return coefficient;
	}

	/**
	 * @param coefficient the coefficient to set
	 */
	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	}

	/**
	 * @return the typeExamen
	 */
	public String getTypeExamen() {
		return typeExamen;
	}

	/**
	 * @param typeExamen the typeExamen to set
	 */
	public void setTypeExamen(String typeExamen) {
		this.typeExamen = typeExamen;
	}

	/**
	 * @return the idMatiere
	 */
	public int getIdMatiere() {
		return idMatiere;
	}

	/**
	 * @param idMatiere the idMatiere to set
	 */
	public void setIdMatiere(int idMatiere) {
		this.idMatiere = idMatiere;
	}
}
