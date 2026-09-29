-- Utilisateur technique en attendant l'authentification (decision D-12).
-- A supprimer au sprint 5, lors de la mise en place de la connexion.

CREATE TABLE utilisateur (
    id                UUID PRIMARY KEY,
    nom               VARCHAR(100) NOT NULL,
    prenom            VARCHAR(100) NOT NULL,
    email             VARCHAR(255) NOT NULL UNIQUE,
    mot_de_passe_hash VARCHAR(255) NOT NULL,
    actif             BOOLEAN NOT NULL DEFAULT TRUE,
    role              VARCHAR(50) NOT NULL
);

INSERT INTO utilisateur (id, nom, prenom, email, mot_de_passe_hash, actif, role)
VALUES ('00000000-0000-0000-0000-000000000001',
        'Systeme', 'Utilisateur', 'systeme@pilot-td.local',
        'NON_UTILISABLE', TRUE, 'ADMIN');
