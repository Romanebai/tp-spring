package fr.diginamic.daos;

import fr.diginamic.entities.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Departement repository.
 */
public interface DepartementRepository extends CrudRepository<Departement, Integer> {

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<Departement> findAll(Pageable pageable);

    /**
     * Find by nom starting with departement.
     *
     * @param nom the nom
     * @return the departement
     */
    Departement findByNomStartingWith(String nom);

    /**
     * Find by id departement.
     *
     * @param id the id
     * @return the departement
     */
    Departement findById(int id);
}
