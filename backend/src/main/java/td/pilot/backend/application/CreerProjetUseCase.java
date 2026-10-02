package td.pilot.backend.application;


import org.springframework.stereotype.Service;
import td.pilot.backend.domaine.modele.ReferenceInconnueException;
import td.pilot.backend.domaine.port.ReferentielRepository;
import java.time.ZoneId;

import java.time.Instant;


import td.pilot.backend.domaine.modele.CodeProjet;
import td.pilot.backend.domaine.modele.Budget;
import td.pilot.backend.domaine.modele.Periode;
import td.pilot.backend.domaine.modele.Projet;
import td.pilot.backend.domaine.modele.ProjetId;
import td.pilot.backend.domaine.modele.ProvinceId;
import td.pilot.backend.domaine.modele.SecteurId;
import td.pilot.backend.domaine.port.ProjetRepository;

@Service
public class CreerProjetUseCase {

    private final ProjetRepository projets;
    private final ReferentielRepository referentiel;

    public CreerProjetUseCase(ProjetRepository projets, ReferentielRepository referentiel) {
        this.projets = projets;
        this.referentiel = referentiel;
    }

    public ProjetId executer(CreerProjetCommande commande, Instant dateReference) {

        ProvinceId provinceId = new ProvinceId(commande.provinceId());
        SecteurId secteurId = new SecteurId(commande.secteurId());

        if (!referentiel.provinceExiste(provinceId)) {
            throw new ReferenceInconnueException("provinceId",
                    "La province %s n'existe pas".formatted(commande.provinceId()));
        }
        if (!referentiel.secteurExiste(secteurId)) {
            throw new ReferenceInconnueException("secteurId",
                    "Le secteur %s n'existe pas".formatted(commande.secteurId()));
        }

        int annee = dateReference.atZone(ZoneId.of("Africa/Ndjamena")).getYear();
        CodeProjet code = GenerateurCodeProjet.suivant(annee, projets.dernierCodeDeLAnnee(annee));

        Projet projet = new Projet(
                ProjetId.nouveau(),
                code,
                commande.intitule(),
                commande.description(),
                new Budget(commande.budget()),
                new Periode(commande.dateDebut(), commande.dateFin()),
                provinceId,
                secteurId,
                dateReference);

        projets.enregistrer(projet);
        return projet.id();
    }
}