package fr.diginamic.daos;

import fr.diginamic.entities.Region;
import fr.diginamic.exceptions.VilleApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


/**
 * The type Region dao.
 */
@Repository
public class RegionDao {
    @PersistenceContext
    private EntityManager em;

    /**
     * Extract region by code region.
     *
     * @param code the code
     * @return the region
     */
    public Region extractRegionByCode(String code) {
        TypedQuery<Region> query = em.createQuery("SELECT r FROM Region r WHERE r.code = :code", Region.class);
        query.setParameter("code", code);
        return query.getSingleResultOrNull();
    }

    /**
     * Update region.
     *
     * @param region the region
     * @throws VilleApiException the ville api exception
     */
    @Transactional
    public void updateRegion(Region region) throws VilleApiException {
        Region r = extractRegionByCode(region.getCode());
        if (r != null) {
            r.setNom(region.getNom());
        } else {
            em.persist(region);
        }

    }
}
