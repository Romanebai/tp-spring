package fr.diginamic.controleurs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.daos.DepartementDao;
import fr.diginamic.daos.VilleDao;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.dtos.RegionDto;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entities.Region;
import fr.diginamic.entities.Ville;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.services.DepartementService;
import fr.diginamic.services.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Departement Controller
 */
@RestController
@RequestMapping("/app")
public class DepartementControleur {

    private final DepartementService departementService;
    private final VilleService villeService;

    /**
     * Instantiates a new Departement controleur.
     *
     * @param departementService the departement service
     * @param villeService       the ville service
     */
    public DepartementControleur(DepartementService departementService, VilleService villeService) {
        this.departementService = departementService;
        this.villeService = villeService;
    }

    /**
     * Gets all departements on localhost:8282/app/departements
     *
     * @return the departements
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/departements")
    public ResponseEntity<?> getDepartements() throws VilleApiException {
        List<DepartementDto> dpts = departementService.extractDepartements();
        return ResponseEntity.ok().body(dpts);
    }

    /**
     * Gets departement by id on localhost:8282/app/departements/id
     *
     * @param id the id
     * @return the departement by id
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/departements/{id}")
    public ResponseEntity<?> getDepartementById(@PathVariable int id) throws VilleApiException {
        DepartementDto dpt = departementService.extractDepartementById(id);
        return ResponseEntity.ok("Departement trouvé. " + dpt);
    }

    /**
     * Gets departements by nom on localhost:8282/app/departements/nom.
     *
     * @param nom the nom
     * @return the departements by nom
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("/departements/nom")
    public ResponseEntity<?> getDepartementsByNom(@RequestParam String nom) throws VilleApiException {
        DepartementDto dpt = departementService.extractDepartementByNom(nom);
        return ResponseEntity.ok("Departement trouvé. " + dpt);
    }

    /**
     * Insert departement entity on localhost:8282/app/departements
     *
     * @param dpt the dpt
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @PostMapping("/departements")
    public ResponseEntity<String> addDepartement(@Valid @RequestBody DepartementDto dpt) throws VilleApiException {
        departementService.insertDepartement(dpt);
        return ResponseEntity.ok("Departement inséré avec succès. ");
    }

    /**
     * Update departement by id on localhost:8282/app/departements/id
     *
     * @param id  the id
     * @param dpt the dpt
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @PutMapping("departements/{id}")
    public ResponseEntity<?> updateDepartementById(@PathVariable int id, @RequestBody DepartementDto dpt) throws VilleApiException {
        departementService.updateDepartement(id, dpt);
        return ResponseEntity.ok("Departement modifié avec succès. " + dpt);
    }

    /**
     * Delete departement by id on localhost:8282/app/departements/id
     *
     * @param id the id
     * @return the response entity
     * @throws VilleApiException the ville api exception
     */
    @DeleteMapping("departements/{id}")
    public ResponseEntity<?> deleteDepartementById(@PathVariable int id) throws VilleApiException {
        departementService.deleteDepartement(id);
        return ResponseEntity.ok("Departement supprimé avec succès. ");
    }

    /**
     * Gets departement by code.
     *
     * @param code the code
     * @return the departement by code
     * @throws VilleApiException the ville api exception
     */
    @GetMapping("departement/{code}")
    public ResponseEntity<?> getDepartementByCode(@PathVariable String code) throws VilleApiException {
        DepartementDto dpt = departementService.extractDepartementByCode(code);
        return ResponseEntity.ok(dpt);
    }

    /**
     * Ville by departement fiche.
     *
     * @param code     the code
     * @param response the response
     * @throws VilleApiException the ville api exception
     * @throws IOException       the io exception
     * @throws DocumentException the document exception
     */
//
// Ajoutez une méthode d’export PDF dans votre classe DepartementControleur.
//    o La méthode PDF prend le code département en paramètre de chemin
//    o Le fichier PDF retourne en titre le nom du département ainsi que les éléments
//    suivants :
//   ▪ Code du département
//▪ Nom du département
//▪ Liste des villes de ce département avec nom et population
    @GetMapping("/departement/{code}/fiche")
    public void villeByDepartementFiche(@PathVariable String code, HttpServletResponse response) throws VilleApiException, IOException, DocumentException {
        response.setHeader("Content-Disposition","attachement; filename=\"villes.pdf");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();
        DepartementDto departement = departementService.extractDepartementByCode(code);
        int dptId = departement.getId();
        List<VilleDto> villes = villeService.extractVilleByDepartementId(dptId);
        String region = departement.getNomRegion();

        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
        Font titleFont = new Font(baseFont, 20, Font.BOLD, new BaseColor(0, 51, 80));
        Paragraph title = new Paragraph("Les villes du département " + departement.getNom() + " (" + departement.getCode() + ")" + ". Region : " + region, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 1});
        Font headerFont = new Font(baseFont, 16, Font.BOLD, new BaseColor(75, 0, 130));
        Font contentFont = new Font(baseFont, 13, Font.NORMAL, BaseColor.BLACK);

        PdfPCell cellVille = new PdfPCell(new Phrase("Ville", headerFont));
        cellVille.setBackgroundColor(new BaseColor(200, 180, 255));
        cellVille.setVerticalAlignment(Element.ALIGN_CENTER);
        cellVille.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellVille);

        PdfPCell cellPop = new PdfPCell(new Phrase("Population", headerFont));
        cellPop.setBackgroundColor(new BaseColor(200, 180, 255));
        cellPop.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellPop.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellPop);

        for (VilleDto ville : villes) {
            table.addCell(new PdfPCell(new Phrase(ville.getNom(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(ville.getPopulation()), contentFont)));
        }


        document.add(table);

        document.close();
        response.flushBuffer();
    }
}
