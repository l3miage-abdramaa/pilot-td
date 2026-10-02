package td.pilot.backend.infrastructure.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

public record CreerProjetRequete(
        @NotBlank(message = "L'intitule est obligatoire")
        @Size(max = 255, message = "L'intitule ne peut pas depasser 255 caracteres")
        String intitule,

        @Size(max = 5000)
        String description,

        @PositiveOrZero(message = "Le budget ne peut pas etre negatif")
        long budget,

        @NotNull(message = "La date de debut est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotNull(message = "La province est obligatoire")
        UUID provinceId,

        @NotNull(message = "Le secteur est obligatoire")
        UUID secteurId) { }