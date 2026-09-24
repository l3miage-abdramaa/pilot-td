# Carnet d'apprentissage

## PATH
Liste ordonnée des dossiers où le système cherche une commande.
Il prend le premier trouvé. C'est pourquoi `java` renvoyait
encore Java 17 alors que Java 25 était installé.

## Session vs permanent
`sdk use` et `nvm use` ne valent que pour le terminal courant.
`sdk default` et `nvm alias default` rendent le choix permanent
en écrivant dans ~/.bashrc, lu à chaque ouverture de terminal.

## WSL
Linux sans interface graphique. Le navigateur et Docker Desktop
vivent côté Windows. Même situation qu'un serveur en production. 


Spring Boot 4 a déplacé ses classes internes : les recettes de la version 3 ne marchent plus.
Quand un conteneur s'arrête, docker compose logs donne la cause, et souvent la solution.
PostgreSQL 18 veut son volume sur /var/lib/postgresql.
Les secrets ne vivent jamais dans le code, mais dans des variables d'environnement.
La boucle complète : branche, commits, Pull Request, revue, correction, fusion.
