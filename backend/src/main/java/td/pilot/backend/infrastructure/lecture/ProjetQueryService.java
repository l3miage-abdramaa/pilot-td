package td.pilot.backend.infrastructure.lecture;
import java.util.Optional;
import java.util.UUID;

import java.util.List;
import org.springframework.stereotype.Service;

import org.springframework.jdbc.core.simple.JdbcClient;
import td.pilot.backend.infrastructure.api.ProjetResume;
import td.pilot.backend.infrastructure.api.ProjetDetail;

@Service
public class ProjetQueryService {

    private final JdbcClient jdbc;

    public ProjetQueryService(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<ProjetResume> rechercher(int page, int taille) {
        return jdbc.sql("""
               SELECT p.id,
                    p.code,
                    p.intitule,
                    pr.nom AS province,
                    s.nom AS secteur,
                    p.etat,
                    p.periode_prevue_debut AS date_debut,
                    p.periode_prevue_fin   AS date_fin,
                    p.budget_montant       AS budget
                FROM projet p
                JOIN province pr ON pr.id = p.province_id
                JOIN secteur s   ON s.id  = p.secteur_id
                ORDER BY p.code DESC
                LIMIT :taille OFFSET :offset
                """)
                .param("taille", taille)
                .param("offset", page * taille)
                .query(ProjetResume.class)
                .list();
    }

    public long compter() {
        return jdbc.sql("SELECT COUNT(*) FROM projet").query(Long.class).single();
    }

    public Optional<ProjetDetail> parId(UUID id) {
        return jdbc.sql("""
                SELECT p.id,
                    p.code,
                    p.intitule,
                    p.description,
                    p.budget_montant         AS budget,
                    p.periode_prevue_debut   AS date_debut,
                    p.periode_prevue_fin     AS date_fin,
                    p.province_id            AS province_id,
                    pr.nom                   AS province,
                    p.secteur_id             AS secteur_id,
                    s.nom                    AS secteur,
                    p.etat,
                    NULL                     AS situation,
                    p.date_creation          AS date_creation,
                    p.date_modification      AS date_modification
                FROM projet p
                JOIN province pr ON pr.id = p.province_id
                JOIN secteur s   ON s.id  = p.secteur_id
                WHERE p.id = :id
                """)
                .param("id", id)
                .query(ProjetDetail.class)
                .optional();
    }
}
