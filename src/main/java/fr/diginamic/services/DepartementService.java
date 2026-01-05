package fr.diginamic.services;

import fr.diginamic.daos.DepartementDao;
import fr.diginamic.daos.VilleDao;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.mappers.DepartementMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Departement service.
 */
@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;
    @Autowired
    private VilleDao villeDao;

    /**
     * Extract departements list.
     *
     * @return the list
     * @throws VilleApiException the ville api exception
     */
    public List<DepartementDto> extractDepartements() throws VilleApiException {
        List<Departement> dpt = departementDao.extractAll();
        if (dpt.isEmpty()) {
            throw new VilleApiException("La liste de département est vide.");
        }
        return DepartementMapper.dptToList(dpt);
    }

    /**
     * Extract departement by id departement dto.
     *
     * @param id the id
     * @return the departement dto
     * @throws VilleApiException the ville api exception
     */
    public DepartementDto extractDepartementById(int id) throws VilleApiException {
        Departement dpt = departementDao.extractDepartementById(id);

        if (dpt == null) {
            throw new VilleApiException("Le département n'a pas été trouvée pour cet id.");
        }

        return DepartementMapper.dptDto(dpt);
    }

    /**
     * Extract departement by nom departement dto.
     *
     * @param nom the nom
     * @return the departement dto
     * @throws VilleApiException the ville api exception
     */
    public DepartementDto extractDepartementByNom(String nom) throws VilleApiException {
        Departement departement = departementDao.extractDepartementByName(nom);
        if (departement == null) {
            throw new VilleApiException("Le département n'a pas été trouvée pour ce nom.");

        }
        return DepartementMapper.dptDto(departement);
    }

    /**
     * Insert departement.
     *
     * @param dptDto the dpt dto
     * @throws VilleApiException the ville api exception
     */
    @Transactional
    public void insertDepartement(DepartementDto dptDto) throws VilleApiException {

        Departement dpt = DepartementMapper.toEntity(dptDto);
        if (dpt == null || dpt.getNom().length()<2) {
            throw new VilleApiException("Le nom du département doit comporter au moins 2 caractères.");
        } else if (dpt == null || dpt.getCode().length()<2) {
            throw new VilleApiException("Le code du département doit comporter au moins 2 numéro.");
        }

        Departement nomDpt = departementDao.extractDepartementByName(dpt.getNom());
        if (nomDpt != null) {
            throw new VilleApiException("Le département existe déjà.");
        }
        departementDao.insertDepartement(dpt);
    }

    /**
     * Update departement.
     *
     * @param id        the id
     * @param dptUpdate the dpt update
     * @throws VilleApiException the ville api exception
     */
    @Transactional
    public void updateDepartement(int id, DepartementDto dptUpdate) throws VilleApiException {
        Departement departement = departementDao.extractDepartementById(id);
        if (departement == null) {
            throw new VilleApiException("Le département n'a pas été trouvé pour cet id.");
        }  else if (dptUpdate.getNom().length()<2) {
            throw new VilleApiException("Le nom du département doit comporter au moins 2 caractères.");
        } else if (dptUpdate.getCode().length()<2) {
            throw new VilleApiException("Le code du département doit comporter au moins 2 numéro.");
        }
        departement.setNom(dptUpdate.getNom());
        departement.setCode(dptUpdate.getCode());
    }

    /**
     * Delete departement.
     *
     * @param id the id
     * @throws VilleApiException the ville api exception
     */
    @Transactional
    public void deleteDepartement(int id) throws VilleApiException {
        Departement departement = departementDao.extractDepartementById(id);
        List<Ville> villes = villeDao.extractVilleByDepartementId(id);

        if (!villes.isEmpty()) {
            throw new VilleApiException("Impossible de supprimer le département, des villes y sont rattachées.");
        }

        if (departement == null) {
            throw new VilleApiException("Le département n'a pas été trouvé pour cet id.");
        }

        departementDao.removeDepartementById(id);
    }
}
