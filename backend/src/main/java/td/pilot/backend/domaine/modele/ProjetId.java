package td.pilot.backend.domaine.modele;

import java.util.Objects;
import java.util.UUID;



public record ProjetId(UUID valeur) {
    
    public ProjetId {
        Objects.requireNonNull(valeur, "L'identifiant du projet est obligatoire");
    }

    public static ProjetId nouveau() {
        return new ProjetId(UUID.randomUUID());
    }

    public static ProjetId de(String valeur) {
        return new ProjetId(UUID.fromString(valeur));
    }

}
