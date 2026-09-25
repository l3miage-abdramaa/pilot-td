package td.pilot.backend.domaine.modele;


public record PoidsEtape(int valeur) {

    public PoidsEtape {
        if (valeur < 1 || valeur > 100) {
            throw new IllegalArgumentException(
                "RG-VO-06 : le poids de l'étape doit être compris entre 1 et 100 (%d)".formatted(valeur));
        } 
    }
    
}
