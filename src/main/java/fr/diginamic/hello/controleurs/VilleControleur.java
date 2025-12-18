package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Ville;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app")
public class VilleControleur {

    private static int compteur = 7;
    ArrayList<Ville> villes = new ArrayList<>();

    @PostConstruct
    public void initData() {
        villes.add(new Ville(1,"Nice", 343000));
        villes.add(new Ville(2, "Carcassonne", 47800));
        villes.add(new Ville(3,"Narbonne", 53400));
        villes.add(new Ville(4,"Lyon", 484000));
        villes.add(new Ville(5,"Foix", 9700));
        villes.add(new Ville(6,"Pau", 77200));
    }

    @GetMapping("/villes")
    public List<Ville> getVilles() {
        return villes;
    }

    @GetMapping("/villes/{id}")
    public ResponseEntity<?> getVille(@PathVariable int id) {
        for (Ville v: villes){
            if (v.getId()==id){
                System.out.println(v);
                return ResponseEntity.ok("Ville trouvée."+ v);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ville n'a pas été trouvée.");
    }

    @PutMapping("/villes/{id}")
    public ResponseEntity<?> updateVille(@PathVariable int id,  @RequestBody Ville ville) {
        for (Ville v: villes){
            if (v.getId()==id){
                System.out.println(v);
                v.setNom(ville.getNom());
                v.setPopulation(ville.getPopulation());
                return ResponseEntity.ok("Ville modifié avec succès."+ v);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ville n'a pas été trouvée.");
    }

    @DeleteMapping("/villes/{id}")
    public ResponseEntity<?> deleteVille(@PathVariable int id) {
        for (Ville v: villes){
            if (v.getId()==id){
                System.out.println(v);
                villes.remove(v);
                return ResponseEntity.ok("Ville supprimée avec succès." + v);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ville n'a pas été trouvée.");
    }

    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody Ville ville) {
        for (Ville v : villes) {
            if (v.getNom().equals(ville.getNom())) {
                return ResponseEntity.badRequest().body("La ville existe déjà");
            }
        }
        ville.setId(compteur++);
        villes.add(ville);
        return ResponseEntity.ok("Ville insérée avec succès");
    }
}
