package td.pilot.backend.domaine.port;

import td.pilot.backend.domaine.modele.ProvinceId;
import td.pilot.backend.domaine.modele.SecteurId;

public interface ReferentielRepository {

    boolean provinceExiste(ProvinceId id);

    boolean secteurExiste(SecteurId id);
}