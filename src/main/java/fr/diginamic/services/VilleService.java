package fr.diginamic.services;

import fr.diginamic.daos.DepartementDao;
import fr.diginamic.daos.VilleRepository;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.daos.VilleDao;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.mappers.VilleMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Ville service.
 */
@Service
public class VilleService {

  private final VilleRepository villerepository;

    public VilleService(VilleRepository villerepository) {
        this.villerepository = villerepository;
    }

    public Optional<VilleDto> findById(Integer id) {
        return villerepository.findById(id).map(VilleMapper::villeDto);
    }

    public Page<VilleDto> findAll(int page, int size) {
        PageRequest pagination = PageRequest.of(page, size);
        Page<Ville> villes = villerepository.findAll(pagination);
        return villes.map(VilleMapper::villeDto);
    }

    public VilleDto findByNomStartingWith(String nom) {
         Ville ville = villerepository.findByNomStartingWith(nom);
         return VilleMapper.villeDto(ville);
    }

    public List<VilleDto>populationSupMin(int min) {
        List<Ville>villes = villerepository.findByPopulationGreaterThanOrderByPopulationDesc(min);
        return VilleMapper.villeDtoList(villes);
    }

    public List<VilleDto>populationBetweenMinAndMax(int min, int max) {
        List<Ville>villes = villerepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
        return VilleMapper.villeDtoList(villes);
    }

    public List<VilleDto>byDepartementPopSupMin(String dept, int min) {
        List<Ville>villes = villerepository.findByDepartement_CodeAndPopulationGreaterThanOrderByPopulationDesc(dept, min);
        return VilleMapper.villeDtoList(villes);
    }

    public List<VilleDto>byDepartementPopBetweenMinMax(String dept, int min, int max) {
        List<Ville>villes = villerepository.findByDepartement_CodeAndPopulationBetweenOrderByPopulationDesc(dept, min, max);
        return VilleMapper.villeDtoList(villes);
    }

    public Page<VilleDto>mostPopByDepartement(String dept, int n) {
        PageRequest pagination = PageRequest.of(0, n);
        Page<Ville> villes = villerepository.findByDepartement_Code(dept, pagination);
        return villes.map(VilleMapper::villeDto);
    }

}
