package fr.esgi.android.mamoyenne.tables;

public class Matiere {

	private int idMatiere;
	private String nom;
	private float coefficient;
	
	/**
	 * @param idMatiere
	 * @param nom
	 * @param coefficient
	 */
	public Matiere(int idMatiere, String nom, float coefficient) {
		this.idMatiere = idMatiere;
		this.nom = nom;
		this.coefficient = coefficient;
	}
	
	public Matiere() {
		
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

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
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
}
