package fr.diginamic.dtos;

import java.util.List;

public class RegionDto {

    private int id;
    private String code;
    private String nom;
    private List<DepartementDto> departements;

    public RegionDto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<DepartementDto> getDepartements() {
        return departements;
    }

    public void setDepartements(List<DepartementDto> departements) {
        this.departements = departements;
    }

    @Override
    public String toString() {
        return ", code='" + code +
                ", nom='" + nom;
    }
}
