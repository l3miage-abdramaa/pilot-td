package td.pilot.backend.infrastructure.persistance;

import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import td.pilot.backend.domaine.modele.ProvinceId;
import td.pilot.backend.domaine.modele.SecteurId;
import td.pilot.backend.domaine.port.ReferentielRepository;

@Repository
public class ReferentielRepositoryJpa implements ReferentielRepository {

    private final EntityManager em;

    public ReferentielRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean provinceExiste(ProvinceId id) {
        return existe("province", id.valeur());
    }

    @Override
    public boolean secteurExiste(SecteurId id) {
        return existe("secteur", id.valeur());
    }

    private boolean existe(String table, UUID id) {
        Number compte = (Number) em
                .createNativeQuery("SELECT COUNT(*) FROM " + table + " WHERE id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return compte.intValue() > 0;
    }
}