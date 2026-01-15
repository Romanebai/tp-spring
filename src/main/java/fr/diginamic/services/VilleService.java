package fr.diginamic.services;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.exceptions.VilleApiException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface VilleService {
    Optional<VilleDto> findById(Integer id);

    Page<VilleDto> findAll(int page, int size);

    List<VilleDto> findAllVilles();

    VilleDto findByNomStartingWith(String nom);

    List<VilleDto> populationSupMin(int min);

    List<VilleDto> populationBetweenMinAndMax(int min, int max);

    List<VilleDto> byDepartementPopSupMin(String dept, int min);

    List<VilleDto> byDepartementPopBetweenMinMax(String dept, int min, int max);

    Page<VilleDto> mostPopByDepartement(String dept, int n);

    List<VilleDto> extractVilleByDepartementId(int id);

    VilleDto createVille(VilleDto villeDto) throws VilleApiException;

    VilleDto updateVille(int id, VilleDto ville) throws VilleApiException;

    void deleteVille(int id) throws VilleApiException;
}
