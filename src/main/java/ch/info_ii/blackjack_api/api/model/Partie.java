package ch.info_ii.blackjack_api.api.model;

import java.util.Objects;

public class Partie {

    private Deck deck;

    private Humain joueur;

    private Croupier croupier;

    private String etat;

    private int taux;

    private int nombreDeJetonsMises;

    private String gagnant;

    private boolean finished;

    public Partie(Croupier croupier, Humain joueur, Deck deck){
        this.joueur = joueur;
        this.croupier = croupier;
        this.nombreDeJetonsMises = 0;
        this.taux = 2;
        this.etat = "pas commencé";
        this.finished = false;
        this.deck = deck;
        this.gagnant = "";
    }

    public void demarrer(){
        croupier.emptyHand();
        joueur.emptyHand();
        deck.melanger();
        deck.distribuer(joueur,croupier);
        etat = "en cours";
    }

    /**Jouer le tour du joueur.
     * Si choix est True le joueur tire une carte.
     * Sinon, le joeur ne fait rien et laisse le tour au croupier.*/
    public void jouerTourJoueur(Boolean choix){
        if(choix){
            joueur.jouer(deck);
        }
    }

    /**Jouer le tour du croupier.
     * le coupier est obliger de jouer dans chaque tour jusqu'a ce s'iol atteint la limite
     * Si la limite du crouper est atteint, il ne tire plus de carte.
     * Sinon, il tire.
     * Retourne true si tiré, retourne faux si pas tiré
     * @return boolean*/
    public boolean jouerTourCroupier(){
        int limite = croupier.getLimite();
        if(croupier.getScore() < limite){
            croupier.jouer(deck);
            return true;
        }
        return false;
    }

    /**Détermine le gagnant de la partie.*/
    public void determinerGagnant(){
        int scoreJoueur = joueur.getScore();
        int scoreCroupier = croupier.getScore();

        int gain = taux*nombreDeJetonsMises;

        if(scoreCroupier > 21 && scoreJoueur > 21){
            gagnant = "Pas de gagnant";
            return;
        }
        if(scoreCroupier > 21 && scoreJoueur <= 21){
            gagnant = joueur.getNom();
            joueur.gagner(gain);
            return;
        }
        if(scoreCroupier <= 21 && scoreJoueur > 21){
            gagnant = croupier.getNom();
            return;
        }
        if(scoreCroupier > scoreJoueur){
            gagnant = croupier.getNom();
            return;
        }
        if(scoreCroupier < scoreJoueur){
            gagnant = joueur.getNom();
            joueur.gagner(gain);
            return;
        }
        restituerJetons();
        gagnant = "Egalité";

    }

    public String getEtat() {
        return etat;
    }

    public int getTaux() {
        return taux;
    }

    public int getNombreDeJetonsMises() {
        return nombreDeJetonsMises;
    }

    public void addNombreDeJetonsMises(int nombreDeJetonsMises){
        if(Objects.equals(this.etat, "pas commencé")) {
            this.nombreDeJetonsMises += nombreDeJetonsMises;
        }
    }

    public boolean getFinished(){
        return this.finished;
    }

    public void finishParty(){
        this.finished = true;
        this.determinerGagnant();
        this.etat = "terminé";
    }

    public void setGagnant(String gagnant) {
        this.gagnant = gagnant;
    }

    public String getGagnant() {
        return gagnant;
    }

    public void abandonner(){
        gagnant = croupier.getNom();
        etat = "abandonne";
        finished = true;
    }

    public void restituerJetons(){
        joueur.gagner(nombreDeJetonsMises);
        nombreDeJetonsMises = 0;
    }
}
