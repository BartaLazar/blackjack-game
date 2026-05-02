package ch.info_ii.blackjack_api.api.model;

import java.util.*;

public class Deck {

    private List<Carte> cartes = new ArrayList<Carte>(40);

    /**Pointeur pour la prochaine carte à distribuer*/
    private int nbCartesDistribues;

    public Deck (){
        List<String> colors = new ArrayList<String>(Arrays.asList("clubs", "hearts", "spades", "diamonds"));

        // créer les cartes
        for(int i = 1; i<=10; i++){
            for (String color : colors) {
                cartes.add(new Carte(i, color));
            }
        }

        nbCartesDistribues = 0;
    }

    /**Retourne la prochaine carte du deck et met à jour le compteur des cartes.*/
    public Carte tirerCarte(){
        nbCartesDistribues += 1;
        return cartes.get(nbCartesDistribues-1);
    }

    /**Méalnge le deck dans un ordre aléatoire.*/
    public void melanger(){
        Collections.shuffle(cartes, new Random());
    }

    /**Distribue 2 cartes au joueur et au croupier.*/
    public void distribuer(Joueur joueur, Croupier croupier) {
        joueur.recevoirCarte(tirerCarte());
        croupier.recevoirCarte(tirerCarte());
        joueur.recevoirCarte(tirerCarte());
        croupier.recevoirCarte(tirerCarte());
    }




}
