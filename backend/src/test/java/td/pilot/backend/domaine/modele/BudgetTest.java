package td.pilot.backend.domaine.modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {


    /** Teste si un montant positif normal ;. */
    @Test
    void testBudgetPositif() {
        Budget budget = new Budget(100);
        assertFalse(budget.estNul());
    }

    @Test 
    void testBudgetEstNul() {
        Budget budget = new Budget(0);
        assertTrue(budget.estNul());
    }

    @Test
    void testPlus() {
        Budget budget1 = new Budget(100);
        Budget budget2 = new Budget(50);
        Budget total = budget1.plus(budget2);
        assertEquals(150, total.montant());
    } 

    @Test
    void testBudgetNegatif() {
        assertThrows(IllegalArgumentException.class, () -> new Budget(-1));
    }

    /** Teste un très grand montant pour vérifier que long suffit. */
    @Test
    void testBudgetTresGrand() {
        long montantTresGrand = 9_000_000_000_000_000_000L; // 9 milliards de milliards
        Budget budget = new Budget(montantTresGrand);
        assertEquals(montantTresGrand, budget.montant());
    }
}
