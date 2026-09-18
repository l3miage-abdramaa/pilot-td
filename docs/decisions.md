# Journal des décisions — PILOT-TD

Chaque décision est définitive sauf nouveau besoin client
ou contradiction découverte à l'implémentation.

| ID | Décision | Choix retenu |
|---|---|---|
| D-01 | Modification du budget après lancement | Tracée : ancien, nouveau, motif, auteur, date |
| D-02 | Base de l'alerte de fraîcheur | Dernière date d'observation ; à défaut, début initial du projet |
| D-03 | Cohérence des périodes en préparation | Brouillon libre ; vérifiée au lancement puis en exécution |
| D-04 | Alerte RESPONSABLE_NON_AFFECTE | Retirée de la V1 |
| D-05 | Statut d'une étape | NON_COMMENCEE / EN_REALISATION / TERMINEE |
| D-06 | Ordre des étapes | positionAffichage, présentation seule, aucune dépendance métier |
| D-07 | Désactivation d'un utilisateur | Refusée s'il est responsable d'un projet en préparation ou en cours |
| D-08 | Modifications simultanées | Verrouillage optimiste par version dès la V1 |
| D-09 | Outil de suivi | Jira remplace GitHub Projects |
| D-10 | Environnement de développement | WSL Ubuntu, code dans ~/projets |
