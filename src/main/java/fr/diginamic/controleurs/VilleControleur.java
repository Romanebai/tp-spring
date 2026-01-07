package fr.diginamic.controleurs;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.services.VilleService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Ville controller.
 */
@RestController
@RequestMapping("/app")
public class VilleControleur implements VilleControleurAPI {

    private final VilleService villeService;

    /**
     * Instantiates a new Ville Controller.
     *
     * @param villeService the ville service
     */
    public VilleControleur(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping("/ville/id")
    @Override
    public ResponseEntity<?> getVilleById(@PathVariable int id) throws VilleApiException {
        Optional<VilleDto> ville = villeService.findById(id);
        if  (ville.isEmpty()) {
            throw new VilleApiException("La ville n'existe pas.");
        }
        return ResponseEntity.ok().body(ville);
    }

    @GetMapping("/villes")
    @Override
    public ResponseEntity<?> getVilles(@RequestParam int page, @RequestParam int size) throws VilleApiException {
        Page<VilleDto> villes = villeService.findAll(page,size);
        return ResponseEntity.ok().body(villes);
    }


    @GetMapping("/villes/{nom}")
    @Override
    public ResponseEntity<?> getVillesByNom(@PathVariable String nom) throws VilleApiException {
        VilleDto ville = villeService.findByNomStartingWith(nom);
        return ResponseEntity.ok().body("La ville est : " + ville);
    }

    @GetMapping("/villes/population/sup")
    @Override
    public ResponseEntity<?> popSupMin(@RequestParam int min) throws VilleApiException {
        List<VilleDto> villes = villeService.populationSupMin(min);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/villes/population/between")
    @Override
    public ResponseEntity<?> popSupMax(@RequestParam int min, @RequestParam int max) throws VilleApiException {
        List<VilleDto> villes = villeService.populationBetweenMinAndMax(min, max);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/villes/population/departement/sup")
    @Override
    public ResponseEntity<?>deptPopSupMin(@RequestParam String dpt, @RequestParam int min) throws VilleApiException {
        List<VilleDto> villes = villeService.byDepartementPopSupMin(dpt, min);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/villes/population/departement/between")
    @Override
    public ResponseEntity<?>deptPopBetweenMinMax(@RequestParam String dpt, @RequestParam int min, @RequestParam int max) throws VilleApiException {
        List<VilleDto> villes = villeService.byDepartementPopBetweenMinMax(dpt, min, max);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/villes/top")
    @Override
    public ResponseEntity<?> topVilles(@RequestParam String dpt, @RequestParam int n) {
        Page<VilleDto> villes = villeService.mostPopByDepartement(dpt, n);
        return ResponseEntity.ok(villes);
    }

}
