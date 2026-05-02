package ch.info_ii.blackjack_api.api.model;

import java.util.*;

public class Joueur {

    private String nom;

    private int score;

    List<Carte> hand =new ArrayList<Carte>();

    private int cardsNumber;

    public Joueur(String nom){
        this.nom = nom;
        this.score = 0;
        this.cardsNumber = 0;

    }

    /** en recevant une carte, la carte se rajoute dans la main du joueur une carte*/
    public void recevoirCarte(Carte carte){
        hand.add(carte);
        cardsNumber += 1;

    }

    /** la somme des valeurs de la main du joueur*/
    public int getScore() {
        score = 0;
        for (Carte carte : hand) {
            //System.out.println(carte.getValeur() + " de " + carte.getCouleur());
            score += carte.getValeur();
        }
        return score;
    }

    public void jouer(Deck deck){
        recevoirCarte(deck.tirerCarte());
    }

    public String getNom() {
        return nom;
    }

    public void printHand(){
        for (Carte c : hand) {
            System.out.print(c.toString() + ", ");
        }
        System.out.println("");
    }

    public List<Carte> getHand(){
        return hand;
    }

    public int getCardsNumber() {
        return cardsNumber;
    }

    public void emptyHand(){
        hand = new ArrayList<Carte>();
        cardsNumber = 0;
    }
}
