package td.pilot.backend.infrastructure.api;

import java.util.List;

public record PageReponse<T>(List<T> contenu, int page, int taille, long total) { }