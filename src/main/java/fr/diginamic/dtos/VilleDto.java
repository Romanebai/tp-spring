package fr.diginamic.dtos;

/**
 * The type Ville dto.
 */
public class VilleDto {
    private Integer id;
    private String nom;
    private int population;

    private String codeDepartement;
    private Integer idDepartement;

    /**
     * Instantiates a new Ville dto.
     */
    public VilleDto() {}

    @Override
    public String toString() {
        return nom + " - " +
                " population: " + population + " habitants.";
    }

    /**
     * Instantiates a new Ville dto.
     *
     * @param id              the id
     * @param nom             the nom
     * @param population      the population
     * @param codeDepartement the code departement
     * @param idDepartement   the id departement
     */
    public VilleDto(Integer id, String nom, int population, String codeDepartement, Integer idDepartement) {
        this.id = id;
        this.nom = nom;
        this.population = population;
        this.codeDepartement = codeDepartement;
        this.idDepartement = idDepartement;
    }

    /**
     * Gets code departement.
     *
     * @return the code departement
     */
    public String getCodeDepartement() {
        return codeDepartement;
    }

    /**
     * Sets code departement.
     *
     * @param codeDepartement the code departement
     */
    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    /**
     * Gets id departement.
     *
     * @return the id departement
     */
    public Integer getIdDepartement() {
        return idDepartement;
    }

    /**
     * Sets id departement.
     *
     * @param idDepartement the id departement
     */
    public void setIdDepartement(Integer idDepartement) {
        this.idDepartement = idDepartement;
    }

    /**
     * Instantiates a new Ville dto.
     *
     * @param id         the id
     * @param nom        the nom
     * @param population the population
     */
    public VilleDto(Integer id, String nom, int population) {
        this.id = id;
        this.nom = nom;
        this.population = population;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
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
}
