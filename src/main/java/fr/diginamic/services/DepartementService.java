package fr.diginamic.services;

import fr.diginamic.daos.DepartementRepository;
import fr.diginamic.daos.VilleRepository;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.mappers.DepartementMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private VilleRepository villeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartementService.class);


    public DepartementService(DepartementRepository departementRepository) {this.departementRepository = departementRepository;}

    public Page<DepartementDto> findAll(int page, int size) throws VilleApiException {
        PageRequest pagination = PageRequest.of(page, size);
        Page<Departement> dpts = departementRepository.findAll(pagination);
        return dpts.map(DepartementMapper::dptDto);
    }

    public Optional<DepartementDto> findByCode(String code) {
        return departementRepository.findByCode(code)
                .map(DepartementMapper::dptDto);
    }

    public Optional<DepartementDto> findById(Integer id) {
        return departementRepository.findById(id).map(DepartementMapper::dptDto);
    }

    public DepartementDto findByNomStartingWith(String nom) {
        Departement departement = departementRepository.findByNomStartingWith(nom);
        return DepartementMapper.dptDto(departement);
    }

    @Transactional
    public DepartementDto insertDepartement(DepartementDto dptDto) throws VilleApiException {

        if (dptDto == null || dptDto.getNom().length()<2) {
            throw new VilleApiException("Le nom du département doit comporter au moins 2 caractères.");
        } else if (dptDto == null || dptDto.getCode().length()<2) {
            throw new VilleApiException("Le code du département doit comporter au moins 2 numéro.");
        }
        Optional<Departement> existing = departementRepository.findAll()
                .stream()
                .filter(d -> d.getNom().equalsIgnoreCase(dptDto.getNom()) ||
                        d.getCode().equalsIgnoreCase(dptDto.getCode()))
                .findAny();

        if (existing.isPresent()) {
            throw new VilleApiException("Le département existe déjà.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Departement dpt = DepartementMapper.toEntity(dptDto);

        dpt.setDateMaj(LocalDateTime.now());
        dpt.setUserMaj(username);
        Departement saved = departementRepository.save(dpt);

        LOGGER.info("Ajout département : nom={}, code={}, id={} par {}\"", saved.getNom(), saved.getCode(), saved.getId(), username);

        return DepartementMapper.dptDto(saved);
    }

    @Transactional
    public DepartementDto updateDepartement(int id, DepartementDto dptUpdate) throws VilleApiException {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new VilleApiException("Le département n'a pas été trouvé pour cet id."));
        if (dptUpdate == null) {
            throw new VilleApiException("Les données du département sont manquantes.");
        }
        if (dptUpdate.getNom() == null || dptUpdate.getNom().length() < 2) {
            throw new VilleApiException("Le nom du département doit comporter au moins 2 caractères.");
        }
        if (dptUpdate.getCode() == null || dptUpdate.getCode().length() < 2) {
            throw new VilleApiException("Le code du département doit comporter au moins 2 caractères.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        departement.setNom(dptUpdate.getNom());
        departement.setCode(dptUpdate.getCode());
        departement.setDateMaj(LocalDateTime.now());
        departement.setUserMaj(username);



        Departement saved = departementRepository.save(departement);
        LOGGER.info(
                "Modification département : id={}, nom={}, code={} par {}", saved.getId(), saved.getNom(), saved.getCode(), username
        );

        return DepartementMapper.dptDto(saved);
    }

    @Transactional
    public void deleteDepartement(int id) throws VilleApiException {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new VilleApiException("Le département n'a pas été trouvé pour cet id."));

        List<Ville> villes = villeRepository.findByDepartementId(id);
        if (!villes.isEmpty()) {
            throw new VilleApiException("Impossible de supprimer le département, des villes y sont rattachées.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        LOGGER.info("Suppression département : id={}, nom={}, code={} par {}", departement.getId(), departement.getNom(), departement.getCode(), username);

        departementRepository.delete(departement);
    }
}
