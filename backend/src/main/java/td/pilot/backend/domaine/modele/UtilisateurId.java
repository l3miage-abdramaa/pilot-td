package td.pilot.backend.domaine.modele;

import java.util.Objects;
import java.util.UUID;

public record UtilisateurId(UUID valeur) {
    
    public UtilisateurId {
        Objects.requireNonNull(valeur, "L'identifiant de l'utilisateur est obligatoire");
    }

    public static UtilisateurId nouveau() {
        return new UtilisateurId(UUID.randomUUID());
    }

    public static UtilisateurId de(String valeur) {
        return new UtilisateurId(UUID.fromString(valeur));
    }
}
