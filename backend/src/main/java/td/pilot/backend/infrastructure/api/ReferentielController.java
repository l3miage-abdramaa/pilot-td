package td.pilot.backend.infrastructure.api;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import td.pilot.backend.infrastructure.lecture.ReferentielQueryService;

@RestController
@RequestMapping("/api")
public class ReferentielController {

    private final ReferentielQueryService referentiel;

    public ReferentielController(ReferentielQueryService referentiel) {
        this.referentiel = referentiel;
    }

    @GetMapping("/provinces")
    public List<ReferenceResume> provinces() {
        return referentiel.provinces();
    }

    @GetMapping("/secteurs")
    public List<ReferenceResume> secteurs() {
        return referentiel.secteurs();
    }
}