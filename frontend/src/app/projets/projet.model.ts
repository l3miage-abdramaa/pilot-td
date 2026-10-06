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