package ch.info_ii.blackjack_api.api.model;

import java.util.*;

public class Carte {

    private final int valeur;

    private final String couleur;

    public Carte (int valeur, String couleur){
        this.valeur = valeur;
        this.couleur = couleur;
    }
    /** va renvoyer la valeur de chaque carte*/
    public int getValeur() {
        return valeur;
    }
    /** va renvoyer la couleur de chaque carte*/
    public String getCouleur() {
        return couleur;
    }

    public String toString(){
        return(valeur +" de "+ couleur);
    }

}
