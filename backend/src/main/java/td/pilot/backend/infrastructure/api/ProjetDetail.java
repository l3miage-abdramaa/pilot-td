package td.pilot.backend.infrastructure.api;

import java.util.UUID;
import java.time.LocalDate;
import java.time.Instant;

public record ProjetDetail(
        UUID id,
        String code,
        String intitule,
        String description,
        long budget,
        LocalDate dateDebut,
        LocalDate dateFin,
        UUID provinceId,
        String province,
        UUID secteurId,
        String secteur,
        String etat,
        String situation,
        Instant dateCreation,
        Instant dateModification) {
}
