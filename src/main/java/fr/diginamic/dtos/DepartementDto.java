package fr.diginamic.dtos;

/**
 * The type Departement dto.
 */
public class DepartementDto {
    private Integer id;
    private String code;
    private String nom;

    /**
     * Instantiates a new Departement dto.
     */
    public DepartementDto() {}

    /**
     * Instantiates a new Departement dto.
     *
     * @param id   the id
     * @param code the code
     * @param nom  the nom
     */
    public DepartementDto(Integer id, String code, String nom) {
        this.id = id;
        this.code = code;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "ID : " + id +
                " - " + code + " - " + nom;
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
}
