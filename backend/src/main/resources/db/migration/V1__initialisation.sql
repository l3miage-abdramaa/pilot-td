-- Migration initiale : verifie que Flyway fonctionne.
-- Les tables metier arriveront avec le modele PILOT-TD.

CREATE TABLE sante_application (
    id          SERIAL PRIMARY KEY,
    verifie_le  TIMESTAMP NOT NULL DEFAULT NOW()
);
