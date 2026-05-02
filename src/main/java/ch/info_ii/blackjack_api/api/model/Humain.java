package ch.info_ii.blackjack_api.api.model;

import java.util.Objects;

public class Humain extends Joueur{

    private int nombreDeJetons;


    public Humain(String nom) {
        
        super(nom);
        this.nombreDeJetons = 10;
    }

    public void miser(int nombreDeJetonsMises, Partie partie){

        if(Objects.equals(partie.getEtat(), "pas commencé") && this.nombreDeJetons >= nombreDeJetonsMises) {

            partie.addNombreDeJetonsMises(nombreDeJetonsMises);

            nombreDeJetons = nombreDeJetons - nombreDeJetonsMises;

            System.out.println(this.getNom() + " a misé " + nombreDeJetonsMises + " jetons.");
        }

    }


    public void gagner(int x){
        System.out.println(this.getNom() + " a reçu " + x + " jetons.");
        this.nombreDeJetons += x;
    }

    // quand on renvoie un objet Humain dans le API, il va prendre les éléments du constructeur et des getters pour créer le JSON
    public int getJetons(){
        return nombreDeJetons;
    }


}
