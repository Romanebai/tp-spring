package fr.diginamic.daos;

import fr.diginamic.entities.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Departement dao.
 */
@Repository
public class DepartementDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * Extract all list.
     *
     * @return the list
     */
    public List<Departement> extractAll() {
        TypedQuery<Departement> query = em.createQuery("Select d FROM Departement d", Departement.class);
        return query.getResultList();
    }

    /**
     * Extract departement by id departement.
     *
     * @param id the id
     * @return the departement
     */
    public Departement extractDepartementById(int id){
        TypedQuery<Departement> query = em.createQuery("Select d FROM Departement d WHERE d.id = :id", Departement.class);
        query.setParameter("id", id);
        return query.getSingleResultOrNull();
    }

    /**
     * Extract departement by name departement.
     *
     * @param nom the nom
     * @return the departement
     */
    public Departement extractDepartementByName(String nom){
        TypedQuery<Departement> query = em.createQuery("Select d FROM Departement d WHERE d.nom = :nom", Departement.class);
        query.setParameter("nom", nom);
        return query.getSingleResultOrNull();
    }

    /**
     * Extract departement by code departement.
     *
     * @param code the code
     * @return the departement
     */
    public Departement extractDepartementByCode(String code) {
        TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code", Departement.class);
        query.setParameter("code", code);
        return query.getSingleResultOrNull();
    }

    /**
     * Remove departement by id.
     *
     * @param id the id
     */
    @Transactional
    public void removeDepartementById(int id){
        Departement departement = em.find(Departement.class,id);
        if (departement != null){
            em.remove(departement);
        }
    }

    /**
     * Update departement departement.
     *
     * @param departement the departement
     * @return the departement
     */
    @Transactional
    public Departement updateDepartement(Departement departement){
        Departement dpt = extractDepartementByCode(departement.getCode());
        if (dpt != null) {
            dpt.setNom(departement.getNom());
            dpt.setCode(departement.getCode());
            dpt.setRegion(departement.getRegion());
        } else {
            em.persist(departement);
        }
            return dpt;
    }

    /**
     * Insert departement departement.
     *
     * @param departement the departement
     * @return the departement
     */
    @Transactional
    public Departement insertDepartement(Departement departement){
        em.persist(departement);
        return departement;
    }


}
