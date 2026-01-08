package fr.diginamic.mappers;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Ville;

import java.util.List;

public interface IVilleMapper {
    VilleDto villeDto(Ville ville);
    List<VilleDto> villeDtoList(List<Ville> villes);
    Ville toEntity(VilleDto dto);
}
