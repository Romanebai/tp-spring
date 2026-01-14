package fr.diginamic.mappers;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Ville;

import java.util.List;

/**
 * The interface Ville mapper.
 */
public interface IVilleMapper {
    /**
     * Ville dto ville dto.
     *
     * @param ville the ville
     * @return the ville dto
     */
    VilleDto villeDto(Ville ville);

    /**
     * Ville dto list list.
     *
     * @param villes the villes
     * @return the list
     */
    List<VilleDto> villeDtoList(List<Ville> villes);

    /**
     * To entity ville.
     *
     * @param dto the dto
     * @return the ville
     */
    Ville toEntity(VilleDto dto);
}
