package td.pilot.backend.domaine.modele;

import java.util.Objects;
import java.util.UUID;

public record SecteurId(UUID valeur) {
    
    public SecteurId {
        Objects.requireNonNull(valeur, "L'identifiant du secteur est obligatoire");
    }

    public static SecteurId nouveau() {
        return new SecteurId(UUID.randomUUID());
    }

    public static SecteurId de(String valeur) {
        return new SecteurId(UUID.fromString(valeur));
    }
}
