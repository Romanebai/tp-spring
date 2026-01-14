package fr.diginamic.services;

import fr.diginamic.daos.RegionRepository;
import fr.diginamic.entities.Region;
import fr.diginamic.exceptions.VilleApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public Region extractRegionByCode(String code) throws VilleApiException {
        return regionRepository.findByCode(code)
                .orElseThrow(() -> new VilleApiException("La région n'a pas été trouvée."));
    }
}
