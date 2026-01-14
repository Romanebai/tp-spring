package fr.diginamic.daos;

import fr.diginamic.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    Optional<Region> findByCode(String code);
    Optional<Region> findByNom(String nom);
}
