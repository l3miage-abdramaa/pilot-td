package td.pilot.backend.domaine.modele;

import java.util.Objects;
import java.util.regex.Pattern;

public record CodeProjet(String valeur) {
  
    private static final Pattern FORMAT = Pattern.compile("PRJ-\\d{4}-\\d{3}");

    public CodeProjet {
        Objects.requireNonNull(valeur, "Le code du projet est obligatoire");
        if (!FORMAT.matcher(valeur).matches()) {
            throw new IllegalArgumentException(
                "RG-VO-07 : le code du projet doit respecter le format PRJ-AAAA-NNN (%s)".formatted(valeur));
        }
    }
}
