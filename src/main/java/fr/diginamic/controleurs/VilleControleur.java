package fr.diginamic.controleurs;

import com.itextpdf.text.*;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.services.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * The type Ville controller.
 */
@RestController
@RequestMapping("/villes")
public class VilleControleur implements IVilleControleur {

    private final VilleService villeService;

    /**
     * Instantiates a new Ville controleur.
     *
     * @param villeService the ville service
     */
    public VilleControleur(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VilleDto> getVilleById(@PathVariable int id) throws VilleApiException {
        VilleDto ville = villeService.findById(id)
                .orElseThrow(() -> new VilleApiException("La ville n'existe pas."));
        return ResponseEntity.ok(ville);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getVilles(@RequestParam int page, @RequestParam int size) throws VilleApiException {
        Page<VilleDto> villes = villeService.findAll(page,size);
        return ResponseEntity.ok().body(villes);
    }


    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getVillesByNom(@PathVariable String nom) throws VilleApiException {
        VilleDto ville = villeService.findByNomStartingWith(nom);
        return ResponseEntity.ok().body(ville);
    }

    @GetMapping("/population/sup")
    public ResponseEntity<?> popSupMin(@RequestParam int min) throws VilleApiException {
        List<VilleDto> villes = villeService.populationSupMin(min);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/population/between")
    public ResponseEntity<?> popSupMax(@RequestParam int min, @RequestParam int max) throws VilleApiException {
        List<VilleDto> villes = villeService.populationBetweenMinAndMax(min, max);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/population/departement/sup")
    public ResponseEntity<?>deptPopSupMin(@RequestParam String dpt, @RequestParam int min) throws VilleApiException {
        List<VilleDto> villes = villeService.byDepartementPopSupMin(dpt, min);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/population/departement/between")
    public ResponseEntity<?>deptPopBetweenMinMax(@RequestParam String dpt, @RequestParam int min, @RequestParam int max) throws VilleApiException {
        List<VilleDto> villes = villeService.byDepartementPopBetweenMinMax(dpt, min, max);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/top")
    public ResponseEntity<?> topVilles(@RequestParam String dpt, @RequestParam int n) {
        Page<VilleDto> villes = villeService.mostPopByDepartement(dpt, n);
        return ResponseEntity.ok(villes);
    }

    /**
     * Fiche ville sup min.
     *
     * @param min      the min
     * @param response the response
     * @throws VilleApiException the ville api exception
     * @throws IOException       the io exception
     * @throws DocumentException the document exception
     */
//Ajoutez une méthode dans votre classe VilleControleur qui exporte au format CSV toutes les
    //villes dont la population est supérieure à un minimum donné.
    // nom de la ville, nombre d’habitants, codedépartement, nom du département
    @GetMapping("/{min}/fiche")
    public void ficheVilleSupMin(@PathVariable int min, HttpServletResponse response) throws VilleApiException, IOException, DocumentException {
        response.setHeader("Content-Disposition","attachement; filename=\"fichier.csv");
        List<VilleDto> villes = villeService.populationSupMin(min);
        response.getWriter().append("Nom;Population;Département;Numéro département\n");
        for (VilleDto ville : villes) {
            response.getWriter().append(ville.getNom()+ ";"+ ville.getPopulation() + ";" + ville.getIdDepartement() + ";" + ville.getCodeDepartement()+"\n");
        }
        response.flushBuffer();

    }

    @PostMapping("/add")
    public ResponseEntity<VilleDto> addVille(@RequestBody VilleDto ville) throws VilleApiException {
        VilleDto newVille = villeService.createVille(ville);
        return ResponseEntity.ok(newVille);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<VilleDto> updateVille(
            @PathVariable int id,
            @RequestBody VilleDto ville
    ) throws VilleApiException {
        VilleDto updated = villeService.updateVille(id, ville);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) throws VilleApiException {
        villeService.deleteVille(id);
        return ResponseEntity.ok("Ville supprimée avec succès.");
    }

}
