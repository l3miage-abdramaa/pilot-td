package td.pilot.backend.domaine.modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CodeProjetTest {

    @Test
    void testValidCodeProjet() {
        assertDoesNotThrow(() -> new CodeProjet("PRJ-2024-001"));
    }

    @Test
    void testInvalidCodeProjet() {
        assertThrows(IllegalArgumentException.class, () -> new CodeProjet("INVALID"));
    } 

    @Test
    void refuseUnCodeNull() {
        assertThrows(NullPointerException.class, () -> new CodeProjet(null));
    }

    @Test
    void refuseLesFormatsProches() {
        assertThrows(IllegalArgumentException.class, () -> new CodeProjet("PRJ-2024-1"));    // 1 chiffre
        assertThrows(IllegalArgumentException.class, () -> new CodeProjet("PRJ-24-001"));    // annee courte
        assertThrows(IllegalArgumentException.class, () -> new CodeProjet("prj-2024-001"));  // minuscules
        assertThrows(IllegalArgumentException.class, () -> new CodeProjet("PRJ-2024-0011")); // 4 chiffres
        assertThrows(IllegalArgumentException.class, () -> new CodeProjet(""));              // vide
    }
}
