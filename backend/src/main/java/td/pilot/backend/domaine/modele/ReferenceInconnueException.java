package td.pilot.backend.domaine.modele;



/** Levee quand une reference (province, secteur, utilisateur) n'existe pas. */
public class ReferenceInconnueException extends RuntimeException {

    private final String champ;

    public ReferenceInconnueException(String champ, String message) {
        super(message);
        this.champ = champ;
    }

    public String champ() {
        return champ;
    }
}