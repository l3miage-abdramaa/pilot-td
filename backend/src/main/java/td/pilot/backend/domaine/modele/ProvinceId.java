package td.pilot.backend.domaine.modele;
 
import java.util.Objects;
import java.util.UUID;


public record ProvinceId(UUID valeur) {
    
    public ProvinceId {
        Objects.requireNonNull(valeur, "L'identifiant de la province est obligatoire");
    }

    public static ProvinceId nouveau() {
        return new ProvinceId(UUID.randomUUID());
    }

    
    public static ProvinceId de(String valeur) {
        return new ProvinceId(UUID.fromString(valeur));
    }
}
