package fr.diginamic.services;

import fr.diginamic.daos.VilleDao;
import fr.diginamic.daos.VilleRepository;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Ville;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.mappers.IVilleMapper;
import fr.diginamic.mappers.VilleMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Ville service.
 */
@Service
public class VilleServiceImpl implements VilleService {

    private final VilleRepository villerepository;
    private final IVilleMapper villeMapper;
    private final VilleMapperImpl mapper;
    private final VilleDao villeDao;

    public VilleServiceImpl(VilleRepository villerepository, IVilleMapper villeMapper, VilleMapperImpl mapper, VilleDao villeDao) {
        this.villerepository = villerepository;
        this.villeMapper = villeMapper;
        this.mapper = mapper;
        this.villeDao = villeDao;
    }

    @Override
    public Optional<VilleDto> findById(Integer id) {
        return villerepository.findById(id).map(villeMapper::villeDto);
    }

    public List<VilleDto> extractAllVilles() throws VilleApiException {
        List<Ville> ville = villeDao.extractAll();
        if (ville.isEmpty()) {
            throw new VilleApiException("La liste des villes est vide.");
        }
        return mapper.villeDtoList(ville);
    }

    @Override
    public Page<VilleDto> findAll(int page, int size) {
        PageRequest pagination = PageRequest.of(page, size);
        Page<Ville> villes = villerepository.findAll(pagination);
        return villes.map(villeMapper::villeDto);
    }

    @Override
    public VilleDto findByNomStartingWith(String nom) {
         Ville ville = villerepository.findByNomStartingWith(nom);
         return villeMapper.villeDto(ville);
    }

    @Override
    public List<VilleDto>populationSupMin(int min) {
        List<Ville>villes = villerepository.findByPopulationGreaterThanOrderByPopulationDesc(min);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public List<VilleDto>populationBetweenMinAndMax(int min, int max) {
        List<Ville>villes = villerepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public List<VilleDto>byDepartementPopSupMin(String dept, int min) {
        List<Ville>villes = villerepository.findByDepartement_CodeAndPopulationGreaterThanOrderByPopulationDesc(dept, min);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public List<VilleDto>byDepartementPopBetweenMinMax(String dept, int min, int max) {
        List<Ville>villes = villerepository.findByDepartement_CodeAndPopulationBetweenOrderByPopulationDesc(dept, min, max);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public Page<VilleDto>mostPopByDepartement(String dept, int n) {
        PageRequest pagination = PageRequest.of(0, n);
        Page<Ville> villes = villerepository.findByDepartement_Code(dept, pagination);
        return villes.map(villeMapper::villeDto);
    }

    @Override
    public List<VilleDto> extractVilleByDepartementId(int id) {
        List<Ville> villes = villerepository.findByDepartementId(id);
        return villeMapper.villeDtoList(villes);
    }

}
