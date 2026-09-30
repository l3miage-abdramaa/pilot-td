package td.pilot.backend.infrastructure.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import td.pilot.backend.domaine.modele.Budget;
import td.pilot.backend.domaine.modele.CodeProjet;
import td.pilot.backend.domaine.modele.Periode;
import td.pilot.backend.domaine.modele.Projet;
import td.pilot.backend.domaine.modele.ProjetId;
import td.pilot.backend.domaine.modele.ProvinceId;
import td.pilot.backend.domaine.modele.SecteurId;

class ProjetMapperTest {

    private static final Instant CREATION = Instant.parse("2026-09-29T08:00:00Z");
    private static final Periode PERIODE =
            new Periode(LocalDate.of(2027, 1, 15), LocalDate.of(2027, 10, 31));

    private Projet projetValide() {
        return new Projet(
                ProjetId.nouveau(),
                new CodeProjet("PRJ-2026-001"),
                "Construction ecole primaire",
                "Six salles de classe",
                new Budget(85_000_000L),
                PERIODE,
                ProvinceId.nouveau(),
                SecteurId.nouveau(),
                CREATION);
    }

    @Test
    void conserveLesDonneesLorsDeLAllerRetour() {
        Projet original = projetValide();

        Projet resultat = ProjetMapper.versDomaine(ProjetMapper.versEntite(original));

        assertEquals(original.id(), resultat.id());
        assertEquals(original.code(), resultat.code());
        assertEquals(original.intitule(), resultat.intitule());
        assertEquals(original.description(), resultat.description());
        assertEquals(original.budget(), resultat.budget());
        assertEquals(original.periodePrevue(), resultat.periodePrevue());
        assertEquals(original.provinceId(), resultat.provinceId());
        assertEquals(original.secteurId(), resultat.secteurId());
        assertEquals(original.etat(), resultat.etat());
        assertNull(resultat.periodeInitiale());
        assertNull(resultat.responsableId());
    }
}