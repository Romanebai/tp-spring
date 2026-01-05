package fr.diginamic.services;

import fr.diginamic.daos.DepartementDao;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.daos.VilleDao;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.mappers.VilleMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Ville service.
 */
@Service
public class VilleService {

    @Autowired
    private VilleDao villeDao;

    @Autowired
    private DepartementDao departementDao;

    /**
     * Extract villes list.
     *
     * @return the list
     * @throws VilleApiException the ville api exception
     */
    public List<VilleDto> extractVilles() throws VilleApiException {
       List<Ville> villes = villeDao.extractAll();
       if (villes.isEmpty()) {
           throw new VilleApiException("La liste de ville est vide.");
       }
       return VilleMapper.villeDtoList(villes);
    }

    /**
     * Extract ville by id ville dto.
     *
     * @param id the id
     * @return the ville dto
     * @throws VilleApiException the ville api exception
     */
    public VilleDto extractVilleById(int id) throws VilleApiException {
        Ville ville = villeDao.extractVilleById(id);

        if (ville == null) {
            throw new VilleApiException("La ville n'a pas été trouvée pour cet id.");
        }

        return VilleMapper.villeDto(ville);
    }

    /**
     * Extract ville by nom ville dto.
     *
     * @param nom the nom
     * @return the ville dto
     * @throws VilleApiException the ville api exception
     */
    public VilleDto extractVilleByNom(String nom) throws VilleApiException {
        Ville ville = villeDao.extractVilleByNom(nom);
        if (ville == null) {
            throw new VilleApiException("La ville n'a pas été trouvée pour ce nom.");
        }
        return VilleMapper.villeDto(ville);
    }

    /**
     * Insert ville.
     *
     * @param villeDto the ville dto
     * @throws VilleApiException the ville api exception
     */
    @Transactional
    public void insertVille(VilleDto villeDto) throws VilleApiException {

        Ville ville = VilleMapper.toEntity(villeDto);

        Ville nomVille = villeDao.extractVilleByNom(villeDto.getNom());
        if (nomVille != null) {
            throw new VilleApiException("La ville existe déjà.");
        } else if (villeDto.getNom().length()<2){
            throw new VilleApiException("Le nom de la ville doit comporter au moins 2 caractères.");
        } else if (villeDto.getPopulation()<10){
            throw new VilleApiException("La population n'est pas correcte.");
        } else if (villeDto.getIdDepartement() == null && villeDto.getCodeDepartement() == null) {
            throw new VilleApiException("Il faut renseigner soit l'id du département, soit son code");
        }

        Departement departement = null;

        if (villeDto.getIdDepartement() != null) {
            departement = departementDao.extractDepartementById(villeDto.getIdDepartement());
            if (departement == null) {
                throw new VilleApiException("Département inconnu");
            }
        }
        else if (villeDto.getCodeDepartement() != null) {
            departement = departementDao.extractDepartementByCode(villeDto.getCodeDepartement());

            if (departement == null) {
                departement = new Departement();
                departement.setCode(villeDto.getCodeDepartement());
                departementDao.insertDepartement(departement);
            }
        }

        ville.setDepartement(departement);

        villeDao.insertVille(ville);
    }

    /**
     * Update ville.
     *
     * @param id          the id
     * @param villeUpdate the ville update
     * @throws VilleApiException the ville api exception
     */
    @Transactional
    public void updateVille(int id, VilleDto villeUpdate) throws VilleApiException {
        Ville ville = villeDao.extractVilleById(id);
        if (ville == null) {
            throw new VilleApiException("La ville n'a pas été trouvée pour cet id.");
        } else if (villeUpdate.getNom().length()<2){
            throw new VilleApiException("Le nom de la ville doit comporter au moins 2 caractères.");
        } else if (villeUpdate.getPopulation()<10){
            throw new VilleApiException("La population n'est pas correcte.");
        }
            ville.setNom(villeUpdate.getNom());
            ville.setPopulation(villeUpdate.getPopulation());

        Departement departement = null;

        if (villeUpdate.getIdDepartement() != null) {
            departement = departementDao.extractDepartementById(villeUpdate.getIdDepartement());
            if (departement == null) {
                throw new VilleApiException("Département inconnu pour cet id");
            }
        } else if (villeUpdate.getCodeDepartement() != null) {
            departement = departementDao.extractDepartementByName(villeUpdate.getCodeDepartement());
            if (departement == null) {
                departement = new Departement();
                departement.setCode(villeUpdate.getCodeDepartement());
                departementDao.insertDepartement(departement);
            }
        }
        ville.setDepartement(departement);
    }

    /**
     * Delete ville.
     *
     * @param id the id
     * @throws VilleApiException the ville api exception
     */
    @Transactional
    public void deleteVille(int id) throws VilleApiException {
        Ville ville = villeDao.extractVilleById(id);
        if (ville == null) {
            throw new VilleApiException("La ville n'a pas été trouvée pour cet id.");
        }
        villeDao.removeVilleById(id);
    }


    /**
     * Gets top n villes.
     *
     * @param departementId the departement id
     * @param n             the n
     * @return the top n villes
     * @throws VilleApiException the ville api exception
     */
    public List<Ville> getTopNVilles(int departementId, int n) throws VilleApiException {
        List<Ville> villes = villeDao.findTopVillesByDepartement(departementId, n);
        if (villes.isEmpty()) {
            throw new VilleApiException("Aucune ville trouvée pour ce département.");
        }
        return villes;
    }

    /**
     * Gets villes by population.
     *
     * @param departementId the departement id
     * @param minPop        the min pop
     * @param maxPop        the max pop
     * @return the villes by population
     * @throws VilleApiException the ville api exception
     */
    public List<Ville> getVillesByPopulation(int departementId, int minPop, int maxPop) throws VilleApiException {
        if (minPop > maxPop) {
            throw new VilleApiException("La population minimale ne peut pas être supérieure à la maximale.");
        }

        List<Ville> villes = villeDao.findVillesByPopulationAndDepartement(departementId, minPop, maxPop);
        if (villes.isEmpty()) {
            throw new VilleApiException("Aucune ville trouvée avec ces critères.");
        }
        return villes;
    }
}
