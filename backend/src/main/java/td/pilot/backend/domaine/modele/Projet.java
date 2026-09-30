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

        /** Constructeur complet, reserve a la reconstruction depuis la persistance. */
    private Projet(ProjetId id, CodeProjet code, String intitule, String description,
                   Budget budget, Periode periodePrevue, Periode periodeInitiale,
                   ProvinceId provinceId, SecteurId secteurId, UtilisateurId responsableId,
                   EtatProjet etat, Instant dateCreation, Instant dateModification) {

        this.id = Objects.requireNonNull(id);
        this.code = Objects.requireNonNull(code);
        this.intitule = Objects.requireNonNull(intitule);
        this.description = description == null ? "" : description;
        this.budget = Objects.requireNonNull(budget);
        this.periodePrevue = Objects.requireNonNull(periodePrevue);
        this.periodeInitiale = periodeInitiale;
        this.provinceId = Objects.requireNonNull(provinceId);
        this.secteurId = Objects.requireNonNull(secteurId);
        this.responsableId = responsableId;
        this.etat = Objects.requireNonNull(etat);
        this.dateCreation = Objects.requireNonNull(dateCreation);
        this.dateModification = Objects.requireNonNull(dateModification);
    }

    /**
     * Reconstruit un projet a partir de donnees deja persistees.
     *
     * RESERVE A LA COUCHE DE PERSISTANCE. Ne jamais utiliser pour creer
     * ou modifier un projet : les regles metier ne sont pas rejouees ici.
     */
    public static Projet reconstituer(ProjetId id, CodeProjet code, String intitule,
                                      String description, Budget budget, Periode periodePrevue,
                                      Periode periodeInitiale, ProvinceId provinceId,
                                      SecteurId secteurId, UtilisateurId responsableId,
                                      EtatProjet etat, Instant dateCreation,
                                      Instant dateModification) {
        return new Projet(id, code, intitule, description, budget, periodePrevue,
                periodeInitiale, provinceId, secteurId, responsableId, etat,
                dateCreation, dateModification);
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