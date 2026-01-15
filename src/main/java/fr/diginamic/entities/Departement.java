package fr.diginamic.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Departement.
 */
@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;
    private String nom;
    private String userMaj;
    private LocalDateTime dateMaj;

    @OneToMany(mappedBy="departement")
    private List<Ville> villes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="REG_ID")
    private Region region;
    /**
     * Instantiates a new Departement.
     */
    public Departement() {}

    @Override
    public String toString() {
        return "code: " + code +
                ", nom: " + nom;
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
     * Gets villes.
     *
     * @return the villes
     */
    public List<Ville> getVilles() {
        return villes;
    }

    /**
     * Sets villes.
     *
     * @param villes the villes
     */
    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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
