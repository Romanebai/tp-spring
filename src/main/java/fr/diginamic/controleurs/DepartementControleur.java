package fr.diginamic.controleurs;

import fr.diginamic.daos.DepartementDao;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Departement controleur.
 */
@RestController
@RequestMapping("/app")
public class DepartementControleur {

    private final DepartementService departementService;

    /**
     * Instantiates a new Departement controleur.
     *
     * @param departementService the departement service
     */
    public DepartementControleur(DepartementService departementService) {
        this.departementService = departementService;
    }

    /**
     * Gets departements.
     *
     * @return the departements
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/departements")
    public ResponseEntity<?> getDepartements() throws VilleApiException {
        List<DepartementDto> dpts = departementService.extractDepartements();
        return ResponseEntity.ok().body(dpts);
    }

    /**
     * Gets departement by id.
     *
     * @param id the id
     * @return the departement by id
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/departements/{id}")
    public ResponseEntity<?> getDepartementById(@PathVariable int id) throws VilleApiException {
        DepartementDto dpt = departementService.extractDepartementById(id);
        return ResponseEntity.ok("Departement trouvé. " + dpt);
    }

    /**
     * Gets departements by nom.
     *
     * @param nom the nom
     * @return the departements by nom
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/departements/nom")
    public ResponseEntity<?> getDepartementsByNom(@RequestParam String nom) throws VilleApiException {
        DepartementDto dpt = departementService.extractDepartementByNom(nom);
        return ResponseEntity.ok("Departement trouvé. " + dpt);
    }

    /**
     * Add departement response entity.
     *
     * @param dpt the dpt
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @PostMapping("/departements")
    public ResponseEntity<String> addDepartement(@Valid @RequestBody DepartementDto dpt) throws VilleApiException {
        departementService.insertDepartement(dpt);
        return ResponseEntity.ok("Departement inséré avec succès. ");
    }

    /**
     * Update departement by id response entity.
     *
     * @param id  the id
     * @param dpt the dpt
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @PutMapping("departements/{id}")
    public ResponseEntity<?> updateDepartementById(@PathVariable int id, @RequestBody DepartementDto dpt) throws VilleApiException {
        departementService.updateDepartement(id, dpt);
        return ResponseEntity.ok("Departement modifié avec succès. " + dpt);
    }

    /**
     * Delete departement by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @DeleteMapping("departements/{id}")
    public ResponseEntity<?> deleteDepartementById(@PathVariable int id) throws VilleApiException {
        departementService.deleteDepartement(id);
        return ResponseEntity.ok("Departement supprimé avec succès. ");
    }
}
