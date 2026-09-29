-- Donnees de reference : provinces et secteurs (decision D-11).

CREATE TABLE province (
    id   UUID PRIMARY KEY,
    nom  VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE secteur (
    id   UUID PRIMARY KEY,
    nom  VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO province (id, nom) VALUES
    (gen_random_uuid(), 'Batha'),
    (gen_random_uuid(), 'Borkou'),
    (gen_random_uuid(), 'Chari-Baguirmi'),
    (gen_random_uuid(), 'Ennedi-Est'),
    (gen_random_uuid(), 'Ennedi-Ouest'),
    (gen_random_uuid(), 'Guera'),
    (gen_random_uuid(), 'Hadjer-Lamis'),
    (gen_random_uuid(), 'Kanem'),
    (gen_random_uuid(), 'Lac'),
    (gen_random_uuid(), 'Logone Occidental'),
    (gen_random_uuid(), 'Logone Oriental'),
    (gen_random_uuid(), 'Mandoul'),
    (gen_random_uuid(), 'Mayo-Kebbi Est'),
    (gen_random_uuid(), 'Mayo-Kebbi Ouest'),
    (gen_random_uuid(), 'Moyen-Chari'),
    (gen_random_uuid(), 'N''Djamena'),
    (gen_random_uuid(), 'Ouaddai'),
    (gen_random_uuid(), 'Salamat'),
    (gen_random_uuid(), 'Sila'),
    (gen_random_uuid(), 'Tandjile'),
    (gen_random_uuid(), 'Tibesti'),
    (gen_random_uuid(), 'Wadi Fira'),
    (gen_random_uuid(), 'Barh-El-Gazel');

INSERT INTO secteur (id, nom) VALUES
    (gen_random_uuid(), 'Education'),
    (gen_random_uuid(), 'Sante'),
    (gen_random_uuid(), 'Infrastructure'),
    (gen_random_uuid(), 'Agriculture'),
    (gen_random_uuid(), 'Eau et assainissement'),
    (gen_random_uuid(), 'Energie'),
    (gen_random_uuid(), 'Gouvernance'),
    (gen_random_uuid(), 'Environnement');
