package td.pilot.backend.infrastructure.api;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.Clock;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import td.pilot.backend.application.CreerProjetCommande;
import td.pilot.backend.application.CreerProjetUseCase;
import td.pilot.backend.domaine.modele.ProjetId;
import td.pilot.backend.infrastructure.lecture.ProjetQueryService;
import java.util.UUID;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {

    private final CreerProjetUseCase creerProjet;
    private final Clock horloge;
    private final ProjetQueryService projetQueryService;

    public ProjetController(CreerProjetUseCase creerProjet, Clock horloge, ProjetQueryService projetQueryService) {
        this.creerProjet = creerProjet;
        this.horloge = horloge;
        this.projetQueryService = projetQueryService;
    }

    @PostMapping
    public ResponseEntity<Void> creer(@Valid @RequestBody CreerProjetRequete requete) {
        CreerProjetCommande commande = new CreerProjetCommande(
                requete.intitule(),
                requete.description(),
                requete.budget(),
                requete.dateDebut(),
                requete.dateFin(),
                requete.provinceId(),
                requete.secteurId());

        ProjetId id = creerProjet.executer(commande, horloge.instant());

        return ResponseEntity.created(URI.create("/api/projets/" + id.valeur())).build();
    }

    @GetMapping
    public ResponseEntity<PageReponse<ProjetResume>> lister(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int taille) {

        int pageEffective = Math.max(page, 0);
        int tailleEffective = Math.min(Math.max(taille, 1), 100);

        List<ProjetResume> projets = projetQueryService.rechercher(pageEffective, tailleEffective);
        long total = projetQueryService.compter();

        return ResponseEntity.ok(new PageReponse<>(projets, pageEffective, tailleEffective, total));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProjetDetail> detail(@PathVariable UUID id) {
        return projetQueryService.parId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}