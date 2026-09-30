package td.pilot.backend.infrastructure.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import td.pilot.backend.domaine.modele.Budget;
import td.pilot.backend.domaine.modele.CodeProjet;
import td.pilot.backend.domaine.modele.Periode;
import td.pilot.backend.domaine.modele.Projet;
import td.pilot.backend.domaine.modele.ProjetId;
import td.pilot.backend.domaine.modele.ProvinceId;
import td.pilot.backend.domaine.modele.SecteurId;

@DataJpaTest
@Testcontainers
@Import(ProjetRepositoryJpa.class)
class ProjetRepositoryJpaTest {

    private static final Instant CREATION = Instant.parse("2026-09-29T08:00:00Z");

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18-alpine");

    @Autowired
    private ProjetRepositoryJpa repository;

    @Autowired
    private EntityManager em;

    private ProvinceId premiereProvince() {
        UUID id = (UUID) em.createNativeQuery("SELECT id FROM province LIMIT 1").getSingleResult();
        return new ProvinceId(id);
    }

    private SecteurId premierSecteur() {
        UUID id = (UUID) em.createNativeQuery("SELECT id FROM secteur LIMIT 1").getSingleResult();
        return new SecteurId(id);
    }

    private Projet projet(String code, String intitule) {
        return new Projet(
                ProjetId.nouveau(),
                new CodeProjet(code),
                intitule,
                "Description du projet",
                new Budget(85_000_000L),
                new Periode(LocalDate.of(2027, 1, 15), LocalDate.of(2027, 10, 31)),
                premiereProvince(),
                premierSecteur(),
                CREATION);
    }

    @Test
    void enregistreEtRelitUnProjet() {
        Projet projet = projet("PRJ-2026-042", "Construction ecole primaire");

        repository.enregistrer(projet);
        Optional<Projet> relu = repository.parId(projet.id());

        assertTrue(relu.isPresent());
        Projet p = relu.get();
        assertEquals(projet.id(), p.id());
        assertEquals(projet.code(), p.code());
        assertEquals(projet.intitule(), p.intitule());
        assertEquals(projet.description(), p.description());
        assertEquals(projet.budget(), p.budget());
        assertEquals(projet.periodePrevue(), p.periodePrevue());
        assertEquals(projet.provinceId(), p.provinceId());
        assertEquals(projet.secteurId(), p.secteurId());
        assertEquals(projet.etat(), p.etat());
    }

    @Test
    void reconnaitUnCodeDejaUtilise() {
        Projet projet = projet("PRJ-2026-043", "Centre de sante");
        repository.enregistrer(projet);

        assertTrue(repository.existeAvecCode(new CodeProjet("PRJ-2026-043")));
    }

    @Test
    void ignoreUnCodeInconnu() {
        assertEquals(false, repository.existeAvecCode(new CodeProjet("PRJ-2026-999")));
    }

    @Test
    void rechercheEtCompteLesProjets() {
        long avant = repository.compter();
        Projet projet = projet("PRJ-2026-044", "Route provinciale");

        repository.enregistrer(projet);

        assertEquals(avant + 1, repository.compter());
        List<Projet> projets = repository.rechercher(0, 10);
        assertTrue(projets.stream().anyMatch(p -> p.id().equals(projet.id())));
    }
}