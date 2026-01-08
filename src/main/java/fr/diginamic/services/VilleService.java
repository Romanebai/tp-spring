package fr.diginamic.services;

import fr.diginamic.dtos.VilleDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface VilleService {
    Optional<VilleDto> findById(Integer id);

    Page<VilleDto> findAll(int page, int size);

    VilleDto findByNomStartingWith(String nom);

    List<VilleDto> populationSupMin(int min);

    List<VilleDto> populationBetweenMinAndMax(int min, int max);

    List<VilleDto> byDepartementPopSupMin(String dept, int min);

    List<VilleDto> byDepartementPopBetweenMinMax(String dept, int min, int max);

    Page<VilleDto> mostPopByDepartement(String dept, int n);

    List<VilleDto> extractVilleByDepartementId(int id);
}
