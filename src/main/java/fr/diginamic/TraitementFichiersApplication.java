package fr.diginamic;

import fr.diginamic.daos.DepartementDao;
import fr.diginamic.daos.DepartementRepository;
import fr.diginamic.daos.RegionDao;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.dtos.RegionDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Region;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class TraitementFichiersApplication implements CommandLineRunner {

    private final DepartementDao departementDao;
    private final RegionDao regionDao;

    public TraitementFichiersApplication(DepartementDao departementDao, RegionDao regionDao) {
        this.departementDao = departementDao;
        this.regionDao = regionDao;
    }

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

            regionDao.updateRegion(reg);
        }

        DepartementDto[] dtoArrayDep = restTemplate.getForObject("https://geo.api.gouv.fr/departements", DepartementDto[].class);
        for (DepartementDto departement : dtoArrayDep) {
            Departement dpt = new Departement();

            dpt.setCode(departement.getCode());
            dpt.setNom(departement.getNom());

            Region reg = regionDao.extractRegionByCode(departement.getCodeRegion());
            dpt.setRegion(reg);

            departementDao.updateDepartement(dpt);
        }

        System.out.println("G FINI");


    }
}
