package fr.diginamic.mappers;

import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.entities.Departement;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Departement mapper.
 */
public class DepartementMapper {
    /**
     * Dpt dto departement dto.
     *
     * @param departement the departement
     * @return the departement dto
     */
    public static DepartementDto dptDto(final Departement departement) {
        if (departement == null) {
            return null;
        }

        DepartementDto dto = new DepartementDto();
        dto.setId(departement.getId());
        dto.setNom(departement.getNom());
        dto.setCode(departement.getCode());

        return dto;
    }

    /**
     * Dpt to list list.
     *
     * @param departements the departements
     * @return the list
     */
    public static List<DepartementDto> dptToList(List<Departement> departements) {
        List<DepartementDto> dtos = new ArrayList<>();
        for (Departement departement : departements) {
            dtos.add(dptDto(departement));
        }
        return dtos;
    }

    /**
     * To entity departement.
     *
     * @param dto the dto
     * @return the departement
     */
    public static Departement toEntity(DepartementDto dto) {
        if (dto == null) {
            return null;
        }

        Departement departement = new Departement();

        if (dto.getId() != null) {
            departement.setId(dto.getId());
        }
        departement.setNom(dto.getNom());
        departement.setCode(dto.getCode());

        return departement;
    }





}


