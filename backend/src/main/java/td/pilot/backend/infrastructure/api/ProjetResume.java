package td.pilot.backend.infrastructure.api;

import java.util.UUID;
import java.time.LocalDate;

public record ProjetResume(
        UUID id,
        String code,
        String intitule,
        String province,
        String secteur,
        String etat,
        LocalDate dateDebut,
        LocalDate dateFin,
        long budget) {}
