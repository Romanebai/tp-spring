package fr.diginamic.mappers;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Ville;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Ville mapper.
 */
public class VilleMapper {
    /**
     * Ville dto ville dto.
     *
     * @param ville the ville
     * @return the ville dto
     */
    public static VilleDto villeDto(Ville ville) {
        if (ville == null) {
            return null;
        }

        VilleDto dto = new VilleDto();
        dto.setId(ville.getId());
        dto.setNom(ville.getNom());
        dto.setPopulation(ville.getPopulation());

        if (ville.getDepartement() != null) {
            dto.setIdDepartement(ville.getDepartement().getId());
            dto.setCodeDepartement(ville.getDepartement().getCode());
        }


        return dto;
    }

    /**
     * Ville dto list list.
     *
     * @param villes the villes
     * @return the list
     */
    public static List<VilleDto> villeDtoList(List<Ville> villes) {
        List<VilleDto> dtos = new ArrayList<>();

        for (Ville v : villes) {
            dtos.add(villeDto(v));
        }

        return dtos;
    }

    /**
     * To entity ville.
     *
     * @param dto the dto
     * @return the ville
     */
    public static Ville toEntity(VilleDto dto) {
        if (dto == null) {
            return null;
        }

        Ville ville = new Ville();

        if (dto.getId() != null) {
            ville.setId(dto.getId());
        }

        ville.setNom(dto.getNom());
        ville.setPopulation(dto.getPopulation());

        return ville;
    }


}
