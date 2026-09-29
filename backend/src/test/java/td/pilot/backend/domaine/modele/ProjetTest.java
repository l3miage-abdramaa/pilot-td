package td.pilot.backend.domaine.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProjetTest {

    private static final Instant CREATION = Instant.parse("2026-09-29T08:00:00Z");
    private static final Periode PERIODE =
            new Periode(LocalDate.of(2027, 1, 15), LocalDate.of(2027, 10, 31));

    private Projet projetValide() {
        return creer("Construction ecole primaire", "Six salles de classe",
                new Budget(85_000_000L), PERIODE);
    }

    private Projet creer(String intitule, String description, Budget budget, Periode periode) {
        return new Projet(
                ProjetId.nouveau(),
                new CodeProjet("PRJ-2026-001"),
                intitule,
                description,
                budget,
                periode,
                ProvinceId.nouveau(),
                SecteurId.nouveau(),
                CREATION);
    }

    @Test
    @DisplayName("R-PRJ-03 : un projet cree est EN_PREPARATION, sans periode initiale")
    void unProjetCreeEstEnPreparation() {
        Projet projet = projetValide();

        assertEquals(EtatProjet.EN_PREPARATION, projet.etat());
        assertNull(projet.periodeInitiale());
        assertNull(projet.responsableId());
    }

    @Test
    @DisplayName("R-PRJ-04 : un projet cree n'a ni date de fin reelle, ni motif de cloture")
    void unProjetCreeNEstPasCloture() {
        Projet projet = projetValide();

        assertEquals(CREATION, projet.dateCreation());
        assertEquals(CREATION, projet.dateModification());
    }

    @Test
    @DisplayName("l'intitule est conserve sans espaces superflus")
    void nettoieLIntitule() {
        Projet projet = creer("  Construction ecole  ", "desc", new Budget(1000L), PERIODE);

        assertEquals("Construction ecole", projet.intitule());
    }

    @Test
    @DisplayName("une description absente devient une chaine vide")
    void accepteUneDescriptionNulle() {
        Projet projet = creer("Construction ecole", null, new Budget(1000L), PERIODE);

        assertEquals("", projet.description());
    }

    @Test
    @DisplayName("refuse un intitule nul, vide ou compose d'espaces")
    void refuseUnIntituleInvalide() {
        assertThrows(NullPointerException.class,
                () -> creer(null, "desc", new Budget(1000L), PERIODE));
        assertThrows(IllegalArgumentException.class,
                () -> creer("", "desc", new Budget(1000L), PERIODE));
        assertThrows(IllegalArgumentException.class,
                () -> creer("   ", "desc", new Budget(1000L), PERIODE));
    }

    @Test
    @DisplayName("refuse un budget absent")
    void refuseUnBudgetNul() {
        assertThrows(NullPointerException.class,
                () -> creer("Construction ecole", "desc", null, PERIODE));
    }

    @Test
    @DisplayName("refuse une periode prevue absente")
    void refuseUnePeriodeNulle() {
        assertThrows(NullPointerException.class,
                () -> creer("Construction ecole", "desc", new Budget(1000L), null));
    }

    @Test
    @DisplayName("refuse un identifiant, un code, une province ou un secteur absent")
    void refuseLesReferencesNulles() {
        assertThrows(NullPointerException.class, () -> new Projet(
                null, new CodeProjet("PRJ-2026-001"), "Ecole", "desc",
                new Budget(1000L), PERIODE, ProvinceId.nouveau(), SecteurId.nouveau(), CREATION));

        assertThrows(NullPointerException.class, () -> new Projet(
                ProjetId.nouveau(), null, "Ecole", "desc",
                new Budget(1000L), PERIODE, ProvinceId.nouveau(), SecteurId.nouveau(), CREATION));

        assertThrows(NullPointerException.class, () -> new Projet(
                ProjetId.nouveau(), new CodeProjet("PRJ-2026-001"), "Ecole", "desc",
                new Budget(1000L), PERIODE, null, SecteurId.nouveau(), CREATION));

        assertThrows(NullPointerException.class, () -> new Projet(
                ProjetId.nouveau(), new CodeProjet("PRJ-2026-001"), "Ecole", "desc",
                new Budget(1000L), PERIODE, ProvinceId.nouveau(), null, CREATION));
    }
}