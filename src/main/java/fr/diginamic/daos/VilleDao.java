package fr.diginamic.daos;

import fr.diginamic.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Ville dao.
 */
@Repository
public class VilleDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * Extract all towns in a list.
     *
     * @return the list
     */
    public List<Ville> extractAll() {
        TypedQuery<Ville> query = em.createQuery("Select v FROM Ville v", Ville.class);
        return query.getResultList();
    }

    /**
     * Extract ville by id.
     *
     * @param id the id
     * @return the ville
     */
    public Ville extractVilleById(int id){
        TypedQuery<Ville> query = em.createQuery("Select v FROM Ville v WHERE v.id = :id", Ville.class);
        query.setParameter("id", id);
        return query.getSingleResultOrNull();
    }

    /**
     * Extract ville by departement id in a list.
     *
     * @param id the id
     * @return the list
     */
    public List<Ville> extractVilleByDepartementId(int id){
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :deptId", Ville.class);
        query.setParameter("deptId", id);
        return query.getResultList();
    }

    /**
     * Extract ville by nom.
     *
     * @param nom the nom
     * @return the ville
     */
    public Ville extractVilleByNom(String nom){
        TypedQuery<Ville> query = em.createQuery("Select v FROM Ville v WHERE v.nom = :nom", Ville.class);
        query.setParameter("nom", nom);
        return query.getSingleResultOrNull();
    }


    /**
     * Remove ville by id.
     *
     * @param id the id
     */
    public void removeVilleById(int id){
        Ville ville = em.find(Ville.class, id);
        if (ville != null) {
            em.remove(ville);
        }
    }

    /**
     * Insert a town.
     *
     * @param ville the ville
     * @return the ville
     */
    public Ville insertVille(Ville ville){
        em.persist(ville);
        return ville;
    }

    /**
     * Find top villes by departement id.
     *
     * @param departementId the departement id
     * @param n             the n
     * @return the list
     */
    public List<Ville> findTopVillesByDepartement(int departementId, int n) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :deptId ORDER BY v.population DESC", Ville.class);
        query.setParameter("deptId", departementId);
        query.setMaxResults(n);
        return query.getResultList();
    }

    /**
     * Find villes by population and departement between a min and a max .
     *
     * @param departementId the departement id
     * @param minPop        the min pop
     * @param maxPop        the max pop
     * @return the list
     */
    public List<Ville> findVillesByPopulationAndDepartement(int departementId, int minPop, int maxPop) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :deptId AND v.population BETWEEN :minPop AND :maxPop ORDER BY v.population DESC", Ville.class);
        query.setParameter("deptId", departementId);
        query.setParameter("minPop", minPop);
        query.setParameter("maxPop", maxPop);

        return query.getResultList();
    }


}
