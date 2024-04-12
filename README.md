# Robi
Robi est composé de deux parties, la première est une série d'exercices sur la conception d'un interpréteur de commande, et la deuxième partie comprend un client-serveur intégrant cet interpréteur, accompagné d'une ihm permettant de saisir les commandes et de visualiser les éléments graphiques.

## Rendu
<a id="rendu" class="anchor"></a>
1. Partie 1: 
    Les exercices 1, 2.1, 2.2, 3, 4.1, 4.2 et 5 ont été rendu, notre version finale de l'interpréteur permet d'ajouter différents types d'éléments : rectangle, cercle, image, et label.
    Un élément peux être modifié et selon son type il possible de modifié plusieurs choses, sa position, sa couleur, ses dimensions.
    Chaque élément peux aussi contenir des éléments pour cela il faut utilisé une notation composé, exemple : space.robi.texte .
2. Partie 2:
    Les points 1, 2, 3 et 4 ont été rendu, notre version finale du client-serveur intègre l'interpréteur qui permet de faire toutes les commandes de la partie précédente.
    L'ihm réalisé avec la librairie JavaFx permet de saisir des commandes et visualiser graphiquement les commandes exécuté côté serveur.
    Tous les échanges entre le client et le serveur se font en json.
    À chaque exécution l'environnement affiché sous forme d'arbres dans l'ihm est mis à jour tout comme les snodes.

## Éléments Techniques
<a id="elementsTechniques" class="anchor"></a>

## Bilan
<a id="bilan" class="anchor"></a>
Dans l'interpréteur un oubli a été fait, lors de la suppression d'un élément ses enfants ne sont pas supprimés, visuellement l'élément et ses enfants sont bien supprimés mais environnement contient toujours les references des enfants.
L'exercice 6 de la partie 1 et le point 5 de la partie 2 n'ont pas été fait pas manque de temps.
## Auteurs
<a id="auteurs" class="anchor"></a>
- ROUSVAL Romain
- LE BRAS Erwan
- NICOLAS Pierre
- KERVRAN Maxime