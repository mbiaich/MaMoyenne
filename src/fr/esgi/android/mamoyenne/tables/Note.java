package fr.esgi.android.mamoyenne.tables;

import java.io.Serializable;

import fr.esgi.android.mamoyenne.R;

public class Note implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2418284722144989051L;
	private long idNote;
	private Float note;
	private float coefficient;
	private String typeExamen;
	private long idMatiere;
	
	/**
	 * @param idNote
	 * @param note
	 * @param coefficient
	 * @param idMatiere
	 */
	public Note(long idNote, Float note, float coefficient, String typeExamen, long idMatiere) {
		this.idNote = idNote;
		this.note = note;
		this.coefficient = coefficient;
		this.typeExamen = typeExamen;
		this.idMatiere = idMatiere;
	}
	
	/**
	 * @param note
	 * @param coefficient
	 * @param typeExamen
	 * @param idMatière
	 */
	public Note(Float note, float coefficient, String typeExamen, long idMatiere) {
		this.note = note;
		this.coefficient = coefficient;
		this.typeExamen = typeExamen;
		this.idMatiere = idMatiere;
	}
	
	public Note(){
		
	}

	/**
	 * @return the idNote
	 */
	public long getIdNote() {
		return idNote;
	}

	/**
	 * @param idNote the idNote to set
	 */
	public void setIdNote(long idNote) {
		this.idNote = idNote;
	}

	/**
	 * @return the note
	 */
	public Float getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(Float note) {
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
	public long getIdMatiere() {
		return idMatiere;
	}

	/**
	 * @param idMatiere the idMatiere to set
	 */
	public void setIdMatiere(long idMatiere) {
		this.idMatiere = idMatiere;
	}
}
