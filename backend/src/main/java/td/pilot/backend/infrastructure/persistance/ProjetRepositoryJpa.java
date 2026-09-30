package td.pilot.backend.infrastructure.persistance;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import td.pilot.backend.domaine.modele.CodeProjet;
import td.pilot.backend.domaine.modele.Projet;
import td.pilot.backend.domaine.modele.ProjetId;
import td.pilot.backend.domaine.port.ProjetRepository;

@Repository
public class ProjetRepositoryJpa implements ProjetRepository {

    private final ProjetJpaRepository jpa;

    public ProjetRepositoryJpa(ProjetJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void enregistrer(Projet projet) {
        jpa.save(ProjetMapper.versEntite(projet));
    }

    @Override
    public Optional<Projet> parId(ProjetId id) {
        return jpa.findById(id.valeur()).map(ProjetMapper::versDomaine);
    }

    @Override
    public boolean existeAvecCode(CodeProjet code) {
        return jpa.existsByCode(code.valeur());
    }

    @Override
    public List<Projet> rechercher(int page, int taille) {
        return jpa.findAll(PageRequest.of(page, taille))
                .map(ProjetMapper::versDomaine)
                .toList();
    }

    @Override
    public long compter() {
        return jpa.count();
    }
}