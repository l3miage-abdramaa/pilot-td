package td.pilot.backend.domaine.modele;

import java.time.Instant;
import java.util.Objects;


/**
 * Racine de l'agregat projet.
 *
 * Classe mutable : l'etat evolue dans le temps (lancement, replanification,
 * cloture). Les objets-valeur qu'elle porte restent immuables.
 */
public class Projet {

    private final ProjetId id;
    private final CodeProjet code;

    private String intitule;
    private String description;
    private Budget budget;
    private Periode periodePrevue;
    private ProvinceId provinceId;
    private SecteurId secteurId;

    private EtatProjet etat;
    private Periode periodeInitiale;
    private UtilisateurId responsableId;

    private final Instant dateCreation;
    private Instant dateModification;

    /** Constructeur de creation : le projet naît toujours EN_PREPARATION. */
    public Projet(ProjetId id,
                  CodeProjet code,
                  String intitule,
                  String description,
                  Budget budget,
                  Periode periodePrevue,
                  ProvinceId provinceId,
                  SecteurId secteurId,
                  Instant dateCreation) {

        this.id = Objects.requireNonNull(id, "L'identifiant est obligatoire");
        this.code = Objects.requireNonNull(code, "Le code est obligatoire");
        this.intitule = exigerTexteNonVide(intitule, "L'intitule est obligatoire");
        this.description = description == null ? "" : description.trim();
        this.budget = Objects.requireNonNull(budget, "Le budget est obligatoire");
        this.periodePrevue = Objects.requireNonNull(periodePrevue, "La periode prevue est obligatoire");
        this.provinceId = Objects.requireNonNull(provinceId, "La province est obligatoire");
        this.secteurId = Objects.requireNonNull(secteurId, "Le secteur est obligatoire");
        this.dateCreation = Objects.requireNonNull(dateCreation, "La date de creation est obligatoire");

        this.etat = EtatProjet.EN_PREPARATION;
        this.periodeInitiale = null;
        this.responsableId = null;
        this.dateModification = dateCreation;
    }

    private static String exigerTexteNonVide(String valeur, String message) {
        Objects.requireNonNull(valeur, message);
        String nettoye = valeur.trim();
        if (nettoye.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return nettoye;
    }

    public ProjetId id() { return id; }
    public CodeProjet code() { return code; }
    public String intitule() { return intitule; }
    public String description() { return description; }
    public Budget budget() { return budget; }
    public Periode periodePrevue() { return periodePrevue; }
    public Periode periodeInitiale() { return periodeInitiale; }
    public ProvinceId provinceId() { return provinceId; }
    public SecteurId secteurId() { return secteurId; }
    public UtilisateurId responsableId() { return responsableId; }
    public EtatProjet etat() { return etat; }
    public Instant dateCreation() { return dateCreation; }
    public Instant dateModification() { return dateModification; }
}