package td.pilot.backend.infrastructure.persistance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "projet")
public class ProjetEntity {

    @Id
    private UUID id;

    private String code;
    private String intitule;
    private String description;

    @Column(name = "budget_montant")
    private long budgetMontant;

    @Column(name = "periode_prevue_debut")
    private LocalDate periodePrevueDebut;

    @Column(name = "periode_prevue_fin")
    private LocalDate periodePrevueFin;

    @Column(name = "periode_initiale_debut")
    private LocalDate periodeInitialeDebut;

    @Column(name = "periode_initiale_fin")
    private LocalDate periodeInitialeFin;

    @Column(name = "province_id")
    private UUID provinceId;

    @Column(name = "secteur_id")
    private UUID secteurId;

    @Column(name = "responsable_id")
    private UUID responsableId;

    private String etat;

    @Column(name = "date_creation")
    private Instant dateCreation;

    @Column(name = "date_modification")
    private Instant dateModification;

    @Version
    private long version;

    protected ProjetEntity() {
        // requis par JPA
    } 

        public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getBudgetMontant() { return budgetMontant; }
    public void setBudgetMontant(long budgetMontant) { this.budgetMontant = budgetMontant; }

    public LocalDate getPeriodePrevueDebut() { return periodePrevueDebut; }
    public void setPeriodePrevueDebut(LocalDate d) { this.periodePrevueDebut = d; }

    public LocalDate getPeriodePrevueFin() { return periodePrevueFin; }
    public void setPeriodePrevueFin(LocalDate d) { this.periodePrevueFin = d; }

    public LocalDate getPeriodeInitialeDebut() { return periodeInitialeDebut; }
    public void setPeriodeInitialeDebut(LocalDate d) { this.periodeInitialeDebut = d; }

    public LocalDate getPeriodeInitialeFin() { return periodeInitialeFin; }
    public void setPeriodeInitialeFin(LocalDate d) { this.periodeInitialeFin = d; }

    public UUID getProvinceId() { return provinceId; }
    public void setProvinceId(UUID provinceId) { this.provinceId = provinceId; }

    public UUID getSecteurId() { return secteurId; }
    public void setSecteurId(UUID secteurId) { this.secteurId = secteurId; }

    public UUID getResponsableId() { return responsableId; }
    public void setResponsableId(UUID responsableId) { this.responsableId = responsableId; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public Instant getDateCreation() { return dateCreation; }
    public void setDateCreation(Instant dateCreation) { this.dateCreation = dateCreation; }

    public Instant getDateModification() { return dateModification; }
    public void setDateModification(Instant dateModification) { this.dateModification = dateModification; }

    public long getVersion() { return version; }
    public void setVersion(long version) { this.version = version; }
    
    
}