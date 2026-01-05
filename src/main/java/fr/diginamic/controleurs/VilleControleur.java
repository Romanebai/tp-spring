package fr.diginamic.controleurs;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.services.VilleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Ville controleur.
 */
@RestController
@RequestMapping("/app")
public class VilleControleur {

    private final VilleService villeService;

    /**
     * Instantiates a new Ville controleur.
     *
     * @param villeService the ville service
     */
    public VilleControleur(VilleService villeService) {
        this.villeService = villeService;
    }

    /**
     * Gets villes.
     *
     * @return the villes
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/villes")
    public ResponseEntity<?> getVilles() throws VilleApiException {
        List<VilleDto> villes = villeService.extractVilles();

        return ResponseEntity.ok().body(villes);
    }

    /**
     * Gets ville by id.
     *
     * @param id the id
     * @return the ville by id
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/villes/{id}")
    public ResponseEntity<?> getVilleById(@PathVariable int id) throws VilleApiException {
        VilleDto ville = villeService.extractVilleById(id);
        return ResponseEntity.ok("Ville trouvée." + ville);
    }

    /**
     * Gets ville by nom.
     *
     * @param nom the nom
     * @return the ville by nom
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/villes/nom")
    public ResponseEntity<?> getVilleByNom(@RequestParam String nom) throws VilleApiException {
        VilleDto ville = villeService.extractVilleByNom(nom);
        return ResponseEntity.ok("Ville trouvée." + ville);
    }

    /**
     * Add ville response entity.
     *
     * @param ville the ville
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @PostMapping("/ville")
    public ResponseEntity<String> addVille(@Valid @RequestBody VilleDto ville) throws VilleApiException {
            villeService.insertVille(ville);
            return ResponseEntity.ok("Ville insérée avec succès");
        }


    /**
     * Update ville by id response entity.
     *
     * @param id    the id
     * @param ville the ville
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @PutMapping("/villes/{id}")
    public ResponseEntity<?> updateVilleById(@PathVariable int id, @RequestBody VilleDto ville) throws VilleApiException {
        villeService.updateVille(id, ville);
        return ResponseEntity.ok("Ville modifié avec succès." + ville);
    }


    /**
     * Delete ville by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @DeleteMapping("/villes/{id}")
    public ResponseEntity<?> deleteVilleById(@PathVariable int id) throws VilleApiException {
        villeService.deleteVille(id);
        return ResponseEntity.ok("Ville supprimé avec succès.");
    }

    /**
     * Gets top ville.
     *
     * @param id the id
     * @param n  the n
     * @return the top ville
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/villes/top/{id}/{n}")
    public ResponseEntity<?>  getTopVille(@PathVariable int id, @PathVariable int n) throws VilleApiException {
        villeService.getTopNVilles(id, n);
        return ResponseEntity.ok("Les villes sont : " + villeService.getTopNVilles(id, n));
    }

    /**
     * Gets villes by population.
     *
     * @param id  the id
     * @param min the min
     * @param max the max
     * @return the villes by population
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/villes/population")
    public ResponseEntity<?> getVillesByPopulation(@RequestParam int id, @RequestParam int min, @RequestParam int max) throws VilleApiException {
        villeService.getVillesByPopulation(id,min,max);
        return ResponseEntity.ok("Les villes sont : " + villeService.getVillesByPopulation(id,min,max));
    }

}
