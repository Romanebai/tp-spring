package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * The type Region.
 */
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private String nom;

    @OneToMany(mappedBy = "region")
    private List<Departement> departements;

    /**
     * Instantiates a new Region.
     */
    public Region() {}

    @Override
    public String toString() {
        return " code='" + code + '\'' +
                ", nom='" + nom;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets nom.
     *
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets nom.
     *
     * @param nom the nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Gets departements.
     *
     * @return the departements
     */
    public List<Departement> getDepartements() {
        return departements;
    }

    /**
     * Sets departements.
     *
     * @param departements the departements
     */
    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }
}
