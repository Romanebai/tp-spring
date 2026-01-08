package fr.diginamic.daos;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Ville;
import fr.diginamic.exceptions.VilleApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VilleRepository extends CrudRepository<Ville, Integer> {

    //Optional<Ville> findById(int id) throws VilleApiException;
    Page<Ville> findAll(Pageable pageable);

    //Recherche de toutes les villes dont le nom commence par une chaine de caractères
    Ville findByNomStartingWith(String nom);

    //Recherche de toutes les villes dont la population est supérieure à min
    List<Ville>findByPopulationGreaterThanOrderByPopulationDesc(int min);

    //Recherche de toutes les villes dont la population est supérieure à min et inférieure à max
    List<Ville>findByPopulationBetweenOrderByPopulationDesc(int min, int max);

    //Recherche de toutes les villes d’un département dont la population est supérieure à min
    List<Ville>findByDepartement_CodeAndPopulationGreaterThanOrderByPopulationDesc(String departement, int min);

    //Recherche de toutes les villes d’un département dont la population sup à min et inf à max
    List<Ville>findByDepartement_CodeAndPopulationBetweenOrderByPopulationDesc(String departement, int min, int max);

    //Recherche des n villes les plus peuplées d’un département donné
    Page<Ville>findByDepartement_Code(String departement, Pageable pageable);

    List<Ville>findByDepartementId(int id);
}
