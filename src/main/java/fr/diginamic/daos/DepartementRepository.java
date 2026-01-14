package fr.diginamic.daos;

import fr.diginamic.entities.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    Page<Departement> findAll(Pageable pageable);

    Departement findByNomStartingWith(String nom);
    Optional<Departement> findByNom(String nom);

    Optional<Departement> findByCode(String code);

    Optional<Departement> findById(Integer id);
}
