package fr.diginamic.controleurs;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.exceptions.VilleApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

public interface IVilleControleur {
    @Operation(summary = "Retourne la liste de toutes les villes coucou")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDto.class))))
    })
    ResponseEntity<?> getVilles()throws VilleApiException;

    //ResponseEntity<?> getVilles(int page, int size)throws VilleApiException;

    ResponseEntity<?> getVilleById(@PathVariable int id) throws VilleApiException;

    ResponseEntity<?> getVillesByNom(@PathVariable String nom) throws VilleApiException;

    ResponseEntity<?> popSupMin(@RequestParam int min) throws VilleApiException;

    ResponseEntity<?> popSupMax(@RequestParam int min, @RequestParam int max) throws VilleApiException;

    ResponseEntity<?> deptPopSupMin(@RequestParam String dpt, @RequestParam int min) throws VilleApiException;

    ResponseEntity<?> deptPopBetweenMinMax(@RequestParam String dpt, @RequestParam int min, @RequestParam int max) throws VilleApiException;

    ResponseEntity<?> topVilles(@RequestParam String dpt, @RequestParam int n);
}
