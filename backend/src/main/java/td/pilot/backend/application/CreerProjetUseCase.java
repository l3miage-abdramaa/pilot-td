package td.pilot.backend.application;


import org.springframework.stereotype.Service;
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

    public CreerProjetUseCase(ProjetRepository projets) {
        this.projets = projets;
    }

    public ProjetId executer(CreerProjetCommande commande, Instant dateReference) {

        int annee = dateReference.atZone(ZoneId.of("Africa/Ndjamena")).getYear();
        CodeProjet code = GenerateurCodeProjet.suivant(annee, projets.dernierCodeDeLAnnee(annee));
        
        Projet projet = new Projet(
                ProjetId.nouveau(),
                code,
                commande.intitule(),
                commande.description(),
                new Budget(commande.budget()),
                new Periode(commande.dateDebut(),commande.dateFin()),
                new ProvinceId(commande.provinceId()),
                new SecteurId(commande.secteurId()),
                dateReference
        );
        
        projets.enregistrer(projet);
        
        return projet.id();
    }
}