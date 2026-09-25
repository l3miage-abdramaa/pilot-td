package td.pilot.backend.domaine.modele;


import java.util.Objects;

public record Budget(long montant) {

    public Budget {
        if (montant < 0) {
            throw new IllegalArgumentException(
                "RG-VO-05 : le montant du budget ne peut pas etre negatif (%d)".formatted(montant));
        }
    }

    /** Indique si le budget est nul. */
    public boolean estNul() {
        return montant == 0;
    }

    public Budget plus(Budget autre) {
        Objects.requireNonNull(autre, "Le budget est obligatoire");
        return new Budget(montant + autre.montant());
    }
}
