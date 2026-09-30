-- Table des projets. Creation en preparation uniquement (sprint 1).

CREATE TABLE projet (
    id                 UUID PRIMARY KEY,
    code               VARCHAR(20) NOT NULL UNIQUE,
    intitule           VARCHAR(255) NOT NULL,
    description        TEXT NOT NULL DEFAULT '',
    budget_montant     BIGINT NOT NULL CHECK (budget_montant >= 0),

    periode_prevue_debut     DATE NOT NULL,
    periode_prevue_fin       DATE NOT NULL,
    periode_initiale_debut   DATE,
    periode_initiale_fin     DATE,

    province_id        UUID NOT NULL REFERENCES province(id),
    secteur_id         UUID NOT NULL REFERENCES secteur(id),
    responsable_id     UUID REFERENCES utilisateur(id),

    etat               VARCHAR(20) NOT NULL,

    date_creation      TIMESTAMPTZ NOT NULL,
    date_modification  TIMESTAMPTZ NOT NULL,
    version            BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT periode_prevue_coherente CHECK (periode_prevue_fin >= periode_prevue_debut)
);

CREATE INDEX idx_projet_etat ON projet(etat);
CREATE INDEX idx_projet_province ON projet(province_id);
CREATE INDEX idx_projet_secteur ON projet(secteur_id);
