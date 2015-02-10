package fr.esgi.android.mamoyenne.tables;


public class Matiere {

	private long idMatiere;
	private String nom;
	private float coefficient;
	
	/**
	 * @param idMatiere
	 * @param nom
	 * @param coefficient
	 */
	public Matiere(long idMatiere, String nom, float coefficient) {
		this.idMatiere = idMatiere;
		this.nom = nom;
		this.coefficient = coefficient;
	}
	
	
	
	/**
	 * @param nom
	 * @param coefficient
	 */
	public Matiere(String nom, float coefficient) {
		this.nom = nom;
		this.coefficient = coefficient;
	}



	public Matiere() {
		
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
	
	@Override
	public String toString() {
		return this.getNom();
	}
}
