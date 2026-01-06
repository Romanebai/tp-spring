package fr.diginamic.daos;

import fr.diginamic.entities.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
     * Extract all departements in a list.
     *
     * @return the list
     */
    public List<Departement> extractAll() {
        TypedQuery<Departement> query = em.createQuery("Select d FROM Departement d", Departement.class);
        return query.getResultList();
    }

    /**
     * Extract departement by id.
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
     * Extract departement by name.
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
     * Extract departement by code.
     *
     * @param code the code
     * @return the departement
     */
    public Departement extractDepartementByCode(String code) {
        TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code", Departement.class);
        query.setParameter("code", code);
        List<Departement> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }


    /**
     * Remove departement by id.
     *
     * @param id the id
     */
    public void removeDepartementById(int id){
        Departement departement = em.find(Departement.class,id);
        if (departement != null){
            em.remove(departement);
        }
    }

    /**
     * Insert departement.
     *
     * @param departement the departement
     * @return the departement
     */
    public Departement insertDepartement(Departement departement){
        em.persist(departement);
        return departement;
    }

}
