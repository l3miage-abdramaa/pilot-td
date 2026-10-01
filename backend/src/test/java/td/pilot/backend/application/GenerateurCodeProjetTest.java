package td.pilot.backend.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import td.pilot.backend.domaine.modele.CodeProjet;
import td.pilot.backend.application.GenerateurCodeProjet;

class GenerateurCodeProjetTest {
    
    @Test
    void premierCodeDeLAnnee() {
        assertEquals(new CodeProjet("PRJ-2026-001"),
                GenerateurCodeProjet.suivant(2026, Optional.empty()));
    }

    @Test
    void incrementeLeNumero() {
        assertEquals(new CodeProjet("PRJ-2026-002"),
                GenerateurCodeProjet.suivant(2026, Optional.of(new CodeProjet("PRJ-2026-001"))));
        assertEquals(new CodeProjet("PRJ-2026-010"),
                GenerateurCodeProjet.suivant(2026, Optional.of(new CodeProjet("PRJ-2026-009"))));
        assertEquals(new CodeProjet("PRJ-2026-100"),
                GenerateurCodeProjet.suivant(2026, Optional.of(new CodeProjet("PRJ-2026-099"))));
    }
}
