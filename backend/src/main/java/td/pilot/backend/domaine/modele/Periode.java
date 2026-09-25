package td.pilot.backend.domaine.modele;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Periode de realisation, bornee par deux dates incluses.
 *
 * RG-VO-04 : dateFin >= dateDebut.
 */
public record Periode(LocalDate dateDebut, LocalDate dateFin) {

    public Periode {
        Objects.requireNonNull(dateDebut, "La date de debut est obligatoire");
        Objects.requireNonNull(dateFin, "La date de fin est obligatoire");
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException(
                    "RG-VO-04 : la date de fin (%s) ne peut pas preceder la date de debut (%s)"
                            .formatted(dateFin, dateDebut));
        }
    }

    /** Indique si la date est comprise dans la periode, bornes incluses. */
    public boolean contient(LocalDate date) {
        Objects.requireNonNull(date, "La date est obligatoire");
        return !date.isBefore(dateDebut) && !date.isAfter(dateFin);
    }

    /** Indique si l'autre periode est entierement incluse dans celle-ci. */
    public boolean contient(Periode autre) {
        Objects.requireNonNull(autre, "La periode est obligatoire");
        return contient(autre.dateDebut()) && contient(autre.dateFin());
    }
}