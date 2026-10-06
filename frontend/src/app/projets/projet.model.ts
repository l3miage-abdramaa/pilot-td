export type EtatProjet = 'EN_PREPARATION' | 'EN_COURS' | 'CLOTURE';

export interface ProjetResume {
  id: string;
  code: string;
  intitule: string;
  province: string;
  secteur: string;
  etat: EtatProjet;
  dateDebut: string;
  dateFin: string;
  budget: number;
}

export interface PageReponse<T> {
  contenu: T[];
  page: number;
  taille: number;
  total: number;
}


export interface ReferenceResume {
  id: string;
  nom: string;
}

export interface CreerProjetRequete {
  intitule: string;
  description: string;
  budget: number;
  dateDebut: string;
  dateFin: string;
  provinceId: string;
  secteurId: string;
}