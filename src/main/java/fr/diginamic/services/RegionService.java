package fr.diginamic.services;

import fr.diginamic.daos.RegionDao;
import fr.diginamic.entities.Region;
import fr.diginamic.exceptions.VilleApiException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    @Autowired
    private RegionDao regionDao;

    public Region extractRegionByCode(String code) throws VilleApiException {
        Region region = regionDao.extractRegionByCode(code);
        if (region == null) {
            throw new VilleApiException("La région n'a pas été trouvée.");
        }
        return region;

    }
}
