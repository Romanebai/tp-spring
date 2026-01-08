package fr.diginamic.controleurs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
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
@RequestMapping("/app")
public class VilleControleur implements IVilleControleur {

    private final VilleService villeService;

    public VilleControleur(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping("/ville/{id}")
    public ResponseEntity<?> getVilleById(@PathVariable int id) throws VilleApiException {
        Optional<VilleDto> ville = villeService.findById(id);
        if  (ville.isEmpty()) {
            throw new VilleApiException("La ville n'existe pas.");
        }
        return ResponseEntity.ok().body(ville);
    }

    @GetMapping("/villes")
    public ResponseEntity<?> getVilles(@RequestParam int page, @RequestParam int size) throws VilleApiException {
        Page<VilleDto> villes = villeService.findAll(page,size);
        return ResponseEntity.ok().body(villes);
    }


    @GetMapping("/villes/{nom}")
    public ResponseEntity<?> getVillesByNom(@PathVariable String nom) throws VilleApiException {
        VilleDto ville = villeService.findByNomStartingWith(nom);
        return ResponseEntity.ok().body(ville);
    }

    @GetMapping("/villes/population/sup")
    public ResponseEntity<?> popSupMin(@RequestParam int min) throws VilleApiException {
        List<VilleDto> villes = villeService.populationSupMin(min);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/villes/population/between")
    public ResponseEntity<?> popSupMax(@RequestParam int min, @RequestParam int max) throws VilleApiException {
        List<VilleDto> villes = villeService.populationBetweenMinAndMax(min, max);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/villes/population/departement/sup")
    public ResponseEntity<?>deptPopSupMin(@RequestParam String dpt, @RequestParam int min) throws VilleApiException {
        List<VilleDto> villes = villeService.byDepartementPopSupMin(dpt, min);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/villes/population/departement/between")
    public ResponseEntity<?>deptPopBetweenMinMax(@RequestParam String dpt, @RequestParam int min, @RequestParam int max) throws VilleApiException {
        List<VilleDto> villes = villeService.byDepartementPopBetweenMinMax(dpt, min, max);
        return ResponseEntity.ok().body(villes);
    }

    @GetMapping("/villes/top")
    public ResponseEntity<?> topVilles(@RequestParam String dpt, @RequestParam int n) {
        Page<VilleDto> villes = villeService.mostPopByDepartement(dpt, n);
        return ResponseEntity.ok(villes);
    }
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

}
