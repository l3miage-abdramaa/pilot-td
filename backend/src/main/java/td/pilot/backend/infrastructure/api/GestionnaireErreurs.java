package td.pilot.backend.infrastructure.api;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import td.pilot.backend.domaine.modele.ReferenceInconnueException;

@RestControllerAdvice
public class GestionnaireErreurs {

    public record ErreurChamp(String champ, String message) { }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail requeteInvalide(MethodArgumentNotValidException e) {
        List<ErreurChamp> erreurs = e.getBindingResult().getFieldErrors().stream()
                .map(f -> new ErreurChamp(f.getField(), f.getDefaultMessage()))
                .toList();

        ProblemDetail probleme = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        probleme.setType(URI.create("https://pilot-td/erreurs/validation"));
        probleme.setTitle("Requete invalide");
        probleme.setDetail("%d champ(s) invalide(s)".formatted(erreurs.size()));
        probleme.setProperty("erreurs", erreurs);
        return probleme;
    }

    
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail regleMetierViolee(IllegalArgumentException e) {
        ProblemDetail probleme = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        probleme.setType(URI.create("https://pilot-td/erreurs/regle-metier"));
        probleme.setTitle("Regle metier non respectee");
        probleme.setDetail(e.getMessage());

        String message = e.getMessage();
        if (message != null && message.startsWith("RG-")) {
            probleme.setProperty("regle", message.substring(0, message.indexOf(' ')));
        }
        return probleme;
    }


    @ExceptionHandler(NullPointerException.class)
    public ProblemDetail pointeurNull(NullPointerException e) {
        ProblemDetail probleme = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        probleme.setType(URI.create("https://pilot-td/erreurs/null-pointer"));
        probleme.setTitle("Valeur nulle");
        probleme.setDetail(e.getMessage());
        return probleme;
    } 


    @ExceptionHandler(ReferenceInconnueException.class)
    public ProblemDetail referenceInconnue(ReferenceInconnueException e) {
        ProblemDetail probleme = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        probleme.setType(URI.create("https://pilot-td/erreurs/reference-inconnue"));
        probleme.setTitle("Reference inconnue");
        probleme.setDetail(e.getMessage());
        probleme.setProperty("champ", e.champ());
        return probleme;
    }
}