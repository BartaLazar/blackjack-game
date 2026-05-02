package ch.info_ii.blackjack_api.service;

import ch.info_ii.blackjack_api.api.model.Croupier;
import ch.info_ii.blackjack_api.api.model.Deck;
import ch.info_ii.blackjack_api.api.model.Humain;
import ch.info_ii.blackjack_api.api.model.Partie;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GameService {

    private Partie p1;

    private Humain h1;

    private Croupier c1;

    private Deck d1;

    private volatile Boolean wait;

    private volatile Boolean waitPlayer;

    private volatile Boolean waitBet;

    private volatile Boolean waitPlay;

    private boolean finishCroupier = false;
    private boolean finishJoueur = false;

    private boolean waitRestart = false;

    public void startGame(){
        game();
    }

    public void newPlayer(String name){
        h1 = new Humain(name);
        waitPlayer = false;
    }

    public void betTokens(int number){
        h1.miser(number, p1);
        waitBet = false;
    }

    public void humanPlay(){
        p1.jouerTourJoueur(true);
        finishJoueur = false;
        waitPlay = false;
    }

    public void humanStay(){
        p1.jouerTourJoueur(false);
        finishJoueur = true;
        waitPlay = false;
    }

    public void emptyHands(){
        c1.emptyHand();
        h1.emptyHand();
    }

    public Partie getParty() {
        return p1;
    }

    public Humain getPlayer() {
        return h1;
    }

    public Croupier getCroupier() {
        return c1;
    }

    public Deck getDeck() {
        return d1;
    }

    public void abandon(){
        p1.abandonner();
        System.out.println(h1.getNom() + " a abandonné le jeu.");
    }

    public void startNewParty(){
        waitRestart = false;
    }

    public void game() {

        System.out.println("---Début du jeu---");

        c1 = new Croupier();

        // attendre que le joueur soit créé:
        waitPlayer = true;
        System.out.println("Attente pour le joueur");
        System.out.println("Instruction: Créez un nouveau joueur");
        while (waitPlayer) {
            Thread.onSpinWait();
            //waiting
        }

        System.out.println("Joueur créé. " + h1.getNom());

        while (h1.getJetons() > 0) {
            System.out.println("---Nouvelle partie---");
            int jetonsMises = 0;

            d1 = new Deck();
            p1 = new Partie(c1, h1, d1);

            // attendre que le joeur mise
            do {
                waitBet = true;
                System.out.println("Attente pour que le joeur mise");
                System.out.println("Instruction: Misez des jetons");
                while (waitBet) {
                    Thread.onSpinWait();
                    //waiting
                }
                jetonsMises = p1.getNombreDeJetonsMises();
            }while (jetonsMises <= 0);
            System.out.println(h1.getNom() + " a misé " + jetonsMises + " jetons et lui restent " + h1.getJetons() + " jetons");

            // début du jeu
            p1.demarrer();
            System.out.println("---La partie commence---");


            do{
                System.out.println("C'est le tour du croupier");
                if(p1.jouerTourCroupier()){
                    System.out.println(" Le croupier a tiré une carte");
                    finishCroupier = false;
                }
                else{
                    System.out.println(" Le croupier n'a pas tiré de carte");
                    finishCroupier = true;
                }
                System.out.print(" ");
                c1.printHand();
                System.out.println(" Score: " + c1.getScore());

                // Tour de l'humain
                System.out.println("C'est le tour de "+h1.getNom());
                System.out.println(" Score: " + h1.getScore());
                System.out.println(" Hand: ");
                System.out.print(" ");
                h1.printHand();

                waitPlay = true;
                System.out.println("Attente pour que "+ h1.getNom() +" joue.");
                System.out.println("Instruction: Tirez une carte our restez");
                while (waitPlay && !p1.getFinished()) {
                    Thread.onSpinWait();
                    //waiting
                }
                h1.printHand();

                if(finishJoueur && finishCroupier){
                    p1.finishParty();
                    System.out.println("Fin de la partie.");
                }

            }while (!p1.getFinished());

            System.out.println("--------------------------------------------");

            if(!Objects.equals(p1.getEtat(), "abandonné")){

                System.out.println("Le score du croupier est: " + c1.getScore());
                System.out.print("La main du croupier est: ");
                c1.printHand();
                System.out.println(" ");

                System.out.println("Le score de "+ h1.getNom() +" est: " + h1.getScore());
                System.out.print("La main de "+ h1.getNom() +" est: ");
                h1.printHand();
                System.out.println(" ");

            }
            System.out.println("Le gagnant est:");
            System.out.println(p1.getGagnant());

            System.out.println(h1.getNom() + " a " + h1.getJetons() + " jetons.");

            waitRestart = true;
            while (waitRestart) {
                Thread.onSpinWait();
                //waiting
            }

        }
        System.out.println("Fin du jeu. Vous n'avez plus de jetons.");
        p1.setGagnant("fini");
        c1.emptyHand();
        h1.emptyHand();
    }

}
