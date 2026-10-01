package td.pilot.backend.application;

import java.time.LocalDate;
import java.util.UUID;

public record CreerProjetCommande(
        String intitule,
        String description,
        long budget,
        LocalDate dateDebut,
        LocalDate dateFin,
        UUID provinceId,
        UUID secteurId) { }