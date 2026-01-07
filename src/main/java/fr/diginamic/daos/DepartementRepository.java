package fr.diginamic.daos;

import fr.diginamic.entities.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartementRepository extends CrudRepository<Departement, Integer> {

    Page<Departement> findAll(Pageable pageable);

    Departement findByNomStartingWith(String nom);

    Departement findById(int id);
}
