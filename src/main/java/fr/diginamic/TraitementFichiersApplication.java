package fr.diginamic;

import fr.diginamic.daos.DepartementRepository;
import fr.diginamic.daos.RegionRepository;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.dtos.RegionDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Region;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.client.RestTemplate;


/**
 * The type Traitement fichiers application.
 */
@SpringBootApplication
public class TraitementFichiersApplication implements CommandLineRunner {

    private final DepartementRepository departementRepository;
    private final RegionRepository regionRepository;

    public TraitementFichiersApplication(DepartementRepository departementRepository, RegionRepository regionRepository) {
        this.departementRepository = departementRepository;
        this.regionRepository = regionRepository;
    }

    /**
     * Instantiates a new Traitement fichiers application.
     *
     * @param departementDao the departement dao
     * @param regionDao      the region dao
     */


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(TraitementFichiersApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        RegionDto[] dtoArrayReg = restTemplate.getForObject("https://geo.api.gouv.fr/regions", RegionDto[].class);

        for (RegionDto region : dtoArrayReg) {
            Region reg = new Region();
            reg.setCode(region.getCode());
            reg.setNom(region.getNom());

            regionRepository.findByCode(reg.getCode())
                    .ifPresentOrElse(
                            existing -> {
                                existing.setNom(reg.getNom());
                                regionRepository.save(existing);
                            },
                            () -> regionRepository.save(reg)
                    );
        }

        DepartementDto[] dtoArrayDep = restTemplate.getForObject("https://geo.api.gouv.fr/departements", DepartementDto[].class);

        for (DepartementDto departement : dtoArrayDep) {
            Region reg = regionRepository.findByCode(departement.getCodeRegion())
                    .orElseThrow(() -> new RuntimeException("Région introuvable : " + departement.getCodeRegion()));

            Departement dpt = new Departement();
            dpt.setCode(departement.getCode());
            dpt.setNom(departement.getNom());
            dpt.setRegion(reg);

            departementRepository.findByCode(dpt.getCode())
                    .ifPresentOrElse(
                            existing -> {
                                existing.setNom(dpt.getNom());
                                existing.setRegion(dpt.getRegion());
                                departementRepository.save(existing);
                            },
                            () -> departementRepository.save(dpt)
                    );
        }

        System.out.println("G FINI");


    }
}
