package td.pilot.backend.infrastructure.api;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import td.pilot.backend.application.CreerProjetUseCase;
import td.pilot.backend.domaine.modele.ProjetId;
import td.pilot.backend.domaine.modele.ReferenceInconnueException;
import td.pilot.backend.infrastructure.lecture.ProjetQueryService;

// TODO : import de WebMvcTest a completer
@WebMvcTest(ProjetController.class)
@Import(ProjetControllerTest.HorlogeDeTest.class)
class ProjetControllerTest {

    @TestConfiguration
    static class HorlogeDeTest {
        @Bean Clock horloge() {
            return Clock.fixed(Instant.parse("2026-10-01T08:00:00Z"), ZoneId.of("Africa/Ndjamena"));
        }
    }

    @Autowired private MockMvc mvc;
    @MockitoBean private CreerProjetUseCase creerProjet;
    @MockitoBean private ProjetQueryService queries;

    private static final String REQUETE_VALIDE = """
            {
              "intitule": "Construction ecole primaire",
              "description": "Six salles de classe",
              "budget": 85000000,
              "dateDebut": "2027-01-15",
              "dateFin": "2027-10-31",
              "provinceId": "d4f5c86b-e5a9-42fa-859c-5f0ac1e1e52c",
              "secteurId": "acb0be88-c5ba-4f50-824b-eece8e79a0b0"
            }
            """;

    @Test
    void creeUnProjetEtRenvoie201() throws Exception {
        ProjetId id = ProjetId.nouveau();
        when(creerProjet.executer(any(), any())).thenReturn(id);

        mvc.perform(post("/api/projets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUETE_VALIDE))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/projets/" + id.valeur()));
    }


    @Test
    void refuseUneRequeteInvalideAvecLeDetailDesChamps() throws Exception {
        String requeteInvalide = """
                {
                "intitule": "",
                "budget": -5,
                "dateDebut": "2027-01-15",
                "dateFin": "2027-10-31",
                "provinceId": "d4f5c86b-e5a9-42fa-859c-5f0ac1e1e52c",
                "secteurId": "acb0be88-c5ba-4f50-824b-eece8e79a0b0"
                }
                """;

        mvc.perform(post("/api/projets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requeteInvalide))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erreurs").isArray())
                .andExpect(jsonPath("$.erreurs.length()").value(2));
    } 


    @Test
    void refuseUneRequeteAvecProvinceInconnue() throws Exception {
        when(creerProjet.executer(any(), any()))
                .thenThrow(new ReferenceInconnueException("provinceId", "La province n'existe pas"));

        mvc.perform(post("/api/projets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUETE_VALIDE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.champ").value("provinceId"))
                .andExpect(jsonPath("$.detail").value("La province n'existe pas"))
                .andExpect(jsonPath("$.title").value("Reference inconnue"));
    }
    
}