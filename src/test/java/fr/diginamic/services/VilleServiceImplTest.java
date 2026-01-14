package fr.diginamic.services;

import fr.diginamic.VilleTest;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.exceptions.VilleApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes= VilleTest.class)
@ActiveProfiles("test")
class VilleServiceImplTest {

    @Autowired
    private VilleServiceImpl villeService;

    @Test
    public void extraireAllVilles() throws VilleApiException {
       Iterable<VilleDto>villes = villeService.extractAllVilles();
       assertTrue(villes.iterator().hasNext());

    }
}
