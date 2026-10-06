package td.pilot.backend.infrastructure.lecture;

import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import td.pilot.backend.infrastructure.api.ReferenceResume;

@Service
public class ReferentielQueryService {

    private final JdbcClient jdbc;

    public ReferentielQueryService(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<ReferenceResume> provinces() {
        return jdbc.sql("SELECT id, nom FROM province ORDER BY nom")
                .query(ReferenceResume.class)
                .list();
    }

    public List<ReferenceResume> secteurs() {
        return jdbc.sql("SELECT id, nom FROM secteur ORDER BY nom")
                .query(ReferenceResume.class)
                .list();
    }
}