package td.pilot.backend.application;

import java.util.Optional;
import td.pilot.backend.domaine.modele.CodeProjet;

public final class GenerateurCodeProjet {

    private GenerateurCodeProjet() { }

    /**
     * Produit le code suivant pour une annee donnee.
     * Sans code existant, renvoie PRJ-AAAA-001.
     */
    public static CodeProjet suivant(int annee, Optional<CodeProjet> dernier) {
        int numero = dernier
                .map(c -> Integer.parseInt(c.valeur().substring(9)) + 1)
                .orElse(1);
        return new CodeProjet("PRJ-%d-%03d".formatted(annee, numero));
    }
}