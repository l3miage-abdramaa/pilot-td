package td.pilot.backend.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import td.pilot.backend.domaine.modele.ReferenceInconnueException;
import td.pilot.backend.domaine.modele.CodeProjet;
import td.pilot.backend.domaine.modele.Projet;
import td.pilot.backend.domaine.modele.ProjetId;
import td.pilot.backend.domaine.port.ProjetRepository;
import td.pilot.backend.domaine.port.ReferentielRepository;
import td.pilot.backend.domaine.modele.ProvinceId;
import td.pilot.backend.domaine.modele.SecteurId;
import td.pilot.backend.domaine.modele.EtatProjet;

class CreerProjetUseCaseTest {

    private static final Instant REFERENCE = Instant.parse("2026-09-30T08:00:00Z");

    /** Faux depot en memoire : aucune base necessaire. */
    private static class DepotEnMemoire implements ProjetRepository {

        private final List<Projet> projets = new ArrayList<>();

        @Override public void enregistrer(Projet projet) { projets.add(projet); }

        @Override public Optional<Projet> parId(ProjetId id) {
            return projets.stream().filter(p -> p.id().equals(id)).findFirst();
        }

        @Override public boolean existeAvecCode(CodeProjet code) {
            return projets.stream().anyMatch(p -> p.code().equals(code));
        }

        @Override public List<Projet> rechercher(int page, int taille) { return List.copyOf(projets); }

        @Override public long compter() { return projets.size(); }

        @Override public Optional<CodeProjet> dernierCodeDeLAnnee(int annee) {
            return projets.stream()
                    .map(Projet::code)
                    .filter(c -> c.valeur().startsWith("PRJ-" + annee))
                    .max((a, b) -> a.valeur().compareTo(b.valeur()));
        }
    }

    private CreerProjetCommande commande(String intitule) {
        return new CreerProjetCommande(
                intitule,
                "Description",
                85_000_000L,
                LocalDate.of(2027, 1, 15),
                LocalDate.of(2027, 10, 31),
                UUID.randomUUID(),
                UUID.randomUUID());
    } 


    /** Faux referentiel : tout existe, sauf ce qu'on declare absent. */
    private static class ReferentielEnMemoire implements ReferentielRepository {
        boolean toutExiste = true;

        @Override public boolean provinceExiste(ProvinceId id) { return toutExiste; }
        @Override public boolean secteurExiste(SecteurId id) { return toutExiste; }
    }

    @Test
    void creeLePremierProjetAvecLeCode001() {
        DepotEnMemoire depot = new DepotEnMemoire();
        CreerProjetUseCase useCase = new CreerProjetUseCase(depot, new ReferentielEnMemoire());
        ProjetId id = useCase.executer(commande("Premier projet"), REFERENCE);
        Projet projet = depot.parId(id).orElseThrow();
        assertEquals(new CodeProjet("PRJ-2026-001"), projet.code());
    }

    @Test
    void incrementeLeCodeAuProjetSuivant() {
        DepotEnMemoire depot = new DepotEnMemoire();
        CreerProjetUseCase useCase = new CreerProjetUseCase(depot, new ReferentielEnMemoire());
        ProjetId premierId = useCase.executer(commande("Premier Projet"), REFERENCE);
        ProjetId deuxiemeId = useCase.executer(commande("Deuxieme Projet"), REFERENCE);
        Projet premierProjet = depot.parId(premierId).orElseThrow();
        Projet deuxiemeProjet = depot.parId(deuxiemeId).orElseThrow();
        assertEquals(new CodeProjet("PRJ-2026-002"), deuxiemeProjet.code());
    }

    @Test
    void leProjetCreeEstEnPreparation() {
        DepotEnMemoire depot = new DepotEnMemoire();
        CreerProjetUseCase useCase = new CreerProjetUseCase(depot, new ReferentielEnMemoire());
        ProjetId id = useCase.executer(commande("Projet en preparation"), REFERENCE);
        Projet projet = depot.parId(id).orElseThrow(() -> new IllegalStateException("Projet non trouvé"));
        assertEquals(EtatProjet.EN_PREPARATION, projet.etat());
    }

    @Test
    void enregistreLeProjetDansLeDepot() {
        DepotEnMemoire depot = new DepotEnMemoire();
        CreerProjetUseCase useCase = new CreerProjetUseCase(depot, new ReferentielEnMemoire());

        useCase.executer(commande("Ecole primaire"), REFERENCE);

        assertEquals(1L, depot.compter());
    } 


    @Test
    void refuseUneProvinceInconnue() {
        DepotEnMemoire depot = new DepotEnMemoire();
        ReferentielEnMemoire referentiel = new ReferentielEnMemoire();
        referentiel.toutExiste = false;
        CreerProjetUseCase useCase = new CreerProjetUseCase(depot, referentiel);

        assertThrows(ReferenceInconnueException.class,
                () -> useCase.executer(commande("Projet test"), REFERENCE));
        assertEquals(0L, depot.compter());
    }


}