package td.pilot.backend.domaine.port;

import java.util.List;
import java.util.Optional;

import td.pilot.backend.domaine.modele.CodeProjet;
import td.pilot.backend.domaine.modele.Projet;
import td.pilot.backend.domaine.modele.ProjetId;


public interface ProjetRepository {
    

    void enregistrer(Projet projet);

    Optional<Projet> parId(ProjetId id);

    boolean existeAvecCode(CodeProjet code);

    List<Projet> rechercher(int page, int taille);

    long compter();
}
