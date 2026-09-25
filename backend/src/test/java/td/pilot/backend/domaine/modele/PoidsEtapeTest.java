package td.pilot.backend.domaine.modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class PoidsEtapeTest {

    @Test
    void testPoidsEtapeValide() {
        PoidsEtape poids = new PoidsEtape(50);
        assertEquals(50, poids.valeur());
    }

    @Test
    void testPoidsEtapeTropPetit() {
        assertThrows(IllegalArgumentException.class, () -> new PoidsEtape(0));
    }

    @Test
    void testPoidsEtapeTropGrand() {
        assertThrows(IllegalArgumentException.class, () -> new PoidsEtape(101));
    } 

    @Test
    void testPoidsEtapeAccepteLesBornes() {
        assertEquals(1, new PoidsEtape(1).valeur());
        assertEquals(100, new PoidsEtape(100).valeur());
    }

    @Test
    void testPoidsEtapeNegatif() {
        assertThrows(IllegalArgumentException.class, () -> new PoidsEtape(-5));
    }
}
