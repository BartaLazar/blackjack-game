package ch.info_ii.blackjack_api.api.model;

public class Croupier extends Joueur{

    private int limite;

    public Croupier() {
        super("Croupier");
        this.limite = 17;
    }

    public int getLimite() {
        return limite;
    }
}
