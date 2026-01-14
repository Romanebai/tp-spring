package fr.diginamic.services;

import fr.diginamic.daos.DepartementRepository;
import fr.diginamic.daos.VilleRepository;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.mappers.IVilleMapper;
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

    private final VilleRepository villeRepository;
    private final IVilleMapper villeMapper;
    private final DepartementRepository departementRepository;

    public VilleServiceImpl (IVilleMapper villeMapper, VilleRepository villeRepository, DepartementRepository departementRepository) {
        this.villeRepository = villeRepository;
        this.villeMapper = villeMapper;
        this.departementRepository = departementRepository;
    }

    @Override
    public Optional<VilleDto> findById(Integer id) {
        return villeRepository.findById(id).map(villeMapper::villeDto);
    }

    public List<VilleDto> extractAllVilles() throws VilleApiException {
        List<Ville> ville = villeRepository.findAll();
        if (ville.isEmpty()) {
            throw new VilleApiException("La liste des villes est vide.");
        }
        return villeMapper.villeDtoList(ville);
    }

    @Override
    public Page<VilleDto> findAll(int page, int size) {
        PageRequest pagination = PageRequest.of(page, size);
        return villeRepository.findAll(pagination).map(villeMapper::villeDto);

    }

    @Override
    public VilleDto findByNomStartingWith(String nom) {
         Ville ville = villeRepository.findByNomStartingWith(nom);
         return villeMapper.villeDto(ville);
    }

    @Override
    public List<VilleDto>populationSupMin(int min) {
        List<Ville>villes = villeRepository.findByPopulationGreaterThanOrderByPopulationDesc(min);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public List<VilleDto>populationBetweenMinAndMax(int min, int max) {
        List<Ville>villes = villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public List<VilleDto>byDepartementPopSupMin(String dept, int min) {
        List<Ville>villes = villeRepository.findByDepartement_CodeAndPopulationGreaterThanOrderByPopulationDesc(dept, min);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public List<VilleDto>byDepartementPopBetweenMinMax(String dept, int min, int max) {
        List<Ville>villes = villeRepository.findByDepartement_CodeAndPopulationBetweenOrderByPopulationDesc(dept, min, max);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public Page<VilleDto>mostPopByDepartement(String dept, int n) {
        PageRequest pagination = PageRequest.of(0, n);
        Page<Ville> villes = villeRepository.findByDepartement_Code(dept, pagination);
        return villes.map(villeMapper::villeDto);
    }

    @Override
    public List<VilleDto> extractVilleByDepartementId(int id) {
        List<Ville> villes = villeRepository.findByDepartementId(id);
        return villeMapper.villeDtoList(villes);
    }

    @Override
    public VilleDto createVille(VilleDto villeDto) throws VilleApiException {

        if (villeDto.getNom() == null || villeDto.getNom().length() < 2) {
            throw new VilleApiException("Le nom de la ville doit comporter au moins 2 caractères.");
        }

        if (villeDto.getPopulation() < 10) {
            throw new VilleApiException("La population n'est pas correcte.");
        }

        if (villeRepository.findByNomStartingWith(villeDto.getNom()) != null) {
            throw new VilleApiException("La ville existe déjà.");
        }

        if (villeDto.getIdDepartement() == null && villeDto.getCodeDepartement() == null) {
            throw new VilleApiException("Il faut renseigner soit l'id du département, soit son code");
        }

        Ville ville = villeMapper.toEntity(villeDto);

        Departement departement = null;
        if (villeDto.getIdDepartement() != null) {
            departement = departementRepository.findById(villeDto.getIdDepartement())
                    .orElseThrow();
        } else if (villeDto.getCodeDepartement() != null) {
            departement = departementRepository.findByCode(villeDto.getCodeDepartement())
                    .orElseGet(() -> {
                        Departement d = new Departement();
                        d.setCode(villeDto.getCodeDepartement());
                        return departementRepository.save(d);
                    });
        }

        ville.setDepartement(departement);

        Ville savedVille = villeRepository.save(ville);

        return villeMapper.villeDto(savedVille);
    }

    @Override
    public VilleDto updateVille(int id, VilleDto villeUpdate) throws VilleApiException {
        Ville ville = villeRepository.findById(id)
                .orElseThrow(() -> new VilleApiException("La ville n'a pas été trouvée pour cet id."));

        if (villeUpdate == null) {
            throw new VilleApiException("Les données de la ville sont manquantes.");
        }
        if (villeUpdate.getNom() == null || villeUpdate.getNom().length() < 2) {
            throw new VilleApiException("Le nom de la ville doit comporter au moins 2 caractères.");
        }
        if (villeUpdate.getPopulation() < 0) {
            throw new VilleApiException("La population doit être positive.");
        }

        ville.setNom(villeUpdate.getNom());
        ville.setPopulation(villeUpdate.getPopulation());

        return villeMapper.villeDto(ville);
    }

    @Override
    public void deleteVille(int id) throws VilleApiException {
        if (!villeRepository.existsById(id)) {
            throw new VilleApiException("La ville n'a pas été trouvée pour cet id.");
        }
        villeRepository.deleteById(id);
    }

}
