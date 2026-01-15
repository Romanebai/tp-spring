package fr.diginamic.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * The type Ville.
 */
@Entity
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private int population;
    private String userMaj;
    private LocalDateTime dateMaj;

    @ManyToOne
    @JoinColumn(name="DEPT_ID")
    private Departement departement;

    /**
     * Instantiates a new Ville.
     */
    public Ville() {}

    @Override
    public String toString() {
        return "Nom: " + nom +
                ", population: " + population;
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
     * Gets population.
     *
     * @return the population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Sets population.
     *
     * @param population the population
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * Gets departement.
     *
     * @return the departement
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Sets departement.
     *
     * @param departement the departement
     */
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public String getUserMaj() {
        return userMaj;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalDateTime getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(LocalDateTime dateMaj) {
        this.dateMaj = dateMaj;
    }
}
