package fr.diginamic.controleurs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.dtos.VilleDto;
import fr.diginamic.exceptions.VilleApiException;
import fr.diginamic.services.DepartementService;
import fr.diginamic.services.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

    private final DepartementService departementService;
    private final VilleService villeService;

    public DepartementControleur(DepartementService departementService, VilleService villeService) {
        this.departementService = departementService;
        this.villeService = villeService;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/all")
    public ResponseEntity<?> getDepartements(@RequestParam int page, @RequestParam int size) throws VilleApiException {
        return ResponseEntity.ok(departementService.findAll(page,size));
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/id/{id}")
    public ResponseEntity<DepartementDto> getDepartementById(@PathVariable int id) throws VilleApiException {
        DepartementDto dpt = departementService.findById(id)
                .orElseThrow(() -> new VilleApiException("Le département n'a pas été trouvé."));
        return ResponseEntity.ok(dpt);
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/nom/{nom}")
    public ResponseEntity<DepartementDto> getDepartementByNom(@PathVariable String nom) throws VilleApiException {
        DepartementDto dpt = departementService.findByNomStartingWith(nom);
        if (dpt == null) throw new VilleApiException("Le département n'a pas été trouvé.");
        return ResponseEntity.ok(dpt);
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/code/{code}")
    public ResponseEntity<DepartementDto> getDepartementByCode(@PathVariable String code) throws VilleApiException {
        return ResponseEntity.ok(
                departementService.findByCode(code)
                        .orElseThrow(() -> new VilleApiException("Le département n'a pas été trouvé."))
        );
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<DepartementDto> addDepartement(@Valid @RequestBody DepartementDto dpt) throws VilleApiException {
        DepartementDto created = departementService.insertDepartement(dpt);
        return ResponseEntity.ok(created);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/id/{id}")
    public ResponseEntity<DepartementDto> updateDepartement(@PathVariable int id, @RequestBody DepartementDto dpt) throws VilleApiException {
        DepartementDto updated = departementService.updateDepartement(id, dpt);
        return ResponseEntity.ok(updated);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteDepartement(@PathVariable int id) throws VilleApiException {
        departementService.deleteDepartement(id);
        return ResponseEntity.ok("Département supprimé avec succès.");
    }


    //
// Ajoutez une méthode d’export PDF dans votre classe DepartementControleur.
//    o La méthode PDF prend le code département en paramètre de chemin
//    o Le fichier PDF retourne en titre le nom du département ainsi que les éléments
//    suivants :
//   ▪ Code du département
//▪ Nom du département
//▪ Liste des villes de ce département avec nom et population
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/code/{code}/fiche")
    public void villeByDepartementFiche(
            @PathVariable String code,
            HttpServletResponse response
    ) throws VilleApiException, IOException, DocumentException {

        response.setHeader("Content-Disposition", "attachment; filename=villes.pdf");

        DepartementDto departement = departementService.findByCode(code)
                .orElseThrow(() -> new VilleApiException("Le département n'a pas été trouvé."));

        List<VilleDto> villes = villeService.extractVilleByDepartementId(departement.getId());
        String region = departement.getNomRegion();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
        Font titleFont = new Font(baseFont, 20, Font.BOLD, new BaseColor(0, 51, 80));

        Paragraph title = new Paragraph(
                "Les villes du département " + departement.getNom()
                        + " (" + departement.getCode() + ") - Région : " + region,
                titleFont
        );
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 1});

        Font headerFont = new Font(baseFont, 16, Font.BOLD);
        Font contentFont = new Font(baseFont, 13);

        table.addCell(new PdfPCell(new Phrase("Ville", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Population", headerFont)));

        for (VilleDto ville : villes) {
            table.addCell(new PdfPCell(new Phrase(ville.getNom(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(ville.getPopulation()), contentFont)));
        }

        document.add(table);
        document.close();
        response.flushBuffer();
    }
}
