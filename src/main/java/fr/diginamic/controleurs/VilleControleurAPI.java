package fr.diginamic.controleurs;

import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Ville;
import fr.diginamic.exceptions.VilleApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface VilleControleurAPI {


    ResponseEntity<?> getVilleById(@PathVariable int id) throws VilleApiException;

    @GetMapping("/villes")
    ResponseEntity<?> getVilles(@RequestParam int page, @RequestParam int size) throws VilleApiException;

    @GetMapping("/villes/{nom}")
    ResponseEntity<?> getVillesByNom(@PathVariable String nom) throws VilleApiException;

    @GetMapping("/villes/population/sup")
    ResponseEntity<?> popSupMin(@RequestParam int min) throws VilleApiException;

    @GetMapping("/villes/population/between")
    ResponseEntity<?> popSupMax(@RequestParam int min, @RequestParam int max) throws VilleApiException;

    @GetMapping("/villes/population/departement/sup")
    ResponseEntity<?> deptPopSupMin(@RequestParam String dpt, @RequestParam int min) throws VilleApiException;

    @GetMapping("/villes/population/departement/between")
    ResponseEntity<?> deptPopBetweenMinMax(@RequestParam String dpt, @RequestParam int min, @RequestParam int max) throws VilleApiException;

    @GetMapping("/villes/top")
    ResponseEntity<?> topVilles(@RequestParam String dpt, @RequestParam int n);
}
