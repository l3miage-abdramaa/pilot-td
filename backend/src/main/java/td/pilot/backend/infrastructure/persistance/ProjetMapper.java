package td.pilot.backend.infrastructure.persistance;

import java.time.LocalDate;
import td.pilot.backend.domaine.modele.*;

public final class ProjetMapper {

    private ProjetMapper() { }

    public static ProjetEntity versEntite(Projet projet) {
        ProjetEntity entite = new ProjetEntity();
        entite.setId(projet.id().valeur());
        entite.setCode(projet.code().valeur());
        entite.setIntitule(projet.intitule());
        entite.setDescription(projet.description());
        entite.setBudgetMontant(projet.budget().montant());

        entite.setPeriodePrevueDebut(projet.periodePrevue().dateDebut());
        entite.setPeriodePrevueFin(projet.periodePrevue().dateFin());

        Periode initiale = projet.periodeInitiale();
        entite.setPeriodeInitialeDebut(initiale == null ? null : initiale.dateDebut());
        entite.setPeriodeInitialeFin(initiale == null ? null : initiale.dateFin());

        entite.setProvinceId(projet.provinceId().valeur());
        entite.setSecteurId(projet.secteurId().valeur());
        entite.setResponsableId(
                projet.responsableId() == null ? null : projet.responsableId().valeur());

        entite.setEtat(projet.etat().name());
        entite.setDateCreation(projet.dateCreation());
        entite.setDateModification(projet.dateModification());
        return entite;
    }

    public static Projet versDomaine(ProjetEntity entite) {
        return Projet.reconstituer(
                new ProjetId(entite.getId()),
                new CodeProjet(entite.getCode()),
                entite.getIntitule(),
                entite.getDescription(),
                new Budget(entite.getBudgetMontant()),
                new Periode(entite.getPeriodePrevueDebut(), entite.getPeriodePrevueFin()),
                periodeOuNull(entite.getPeriodeInitialeDebut(), entite.getPeriodeInitialeFin()),
                new ProvinceId(entite.getProvinceId()),
                new SecteurId(entite.getSecteurId()),
                entite.getResponsableId() == null ? null : new UtilisateurId(entite.getResponsableId()),
                EtatProjet.valueOf(entite.getEtat()),
                entite.getDateCreation(),
                entite.getDateModification());
    }

    private static Periode periodeOuNull(LocalDate debut, LocalDate fin) {
        return debut == null || fin == null ? null : new Periode(debut, fin);
    }
}