package ch.info_ii.blackjack_api.api.controller;

import ch.info_ii.blackjack_api.api.model.Croupier;
import ch.info_ii.blackjack_api.api.model.Humain;
import ch.info_ii.blackjack_api.api.model.Partie;
import ch.info_ii.blackjack_api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @PostMapping("/api/start")
    public void start(){
        gameService.startGame();
    }

    @PostMapping("/api/new_player")
    public Humain newPlayer(@RequestParam String name){
        gameService.newPlayer(name);
        return gameService.getPlayer();
    }

    /*
    @PostMapping ("/api/new_party")
    public Partie newParty(){
        this.croupier = new Croupier();
        this.deck = new Deck();
        this.partie = new Partie(this.croupier, this.humain, this.deck);
        this.partie.demarrer();
        return this.partie;
    }

     */

    @GetMapping("/api/player")
    public Humain getPlayer(){
        return gameService.getPlayer();
    }

    @GetMapping("/api/croupier")
    public Croupier getCroupier(){
        return gameService.getCroupier();
    }

    @PostMapping("/api/player/bet_tokens")
    public Map<String, Integer> betTokens(@RequestParam int number){
        gameService.betTokens(number);
        Map<String, Integer> response = new HashMap<String, Integer>(){
            {
                put("number", number);
            }
        };
        return response;
    }

    @PutMapping("/api/player/play/new_card")
    public Humain playHuman(){
        gameService.humanPlay();
        return gameService.getPlayer();
    }

    @PutMapping("/api/player/play/stay")
    public Humain stayHuman(){
        gameService.humanStay();
        return gameService.getPlayer();
    }

    @PutMapping ("/api/player/play/abandon")
    public Map<String, String> abandon(){
        Map<String, String> response = new HashMap<String, String>(){
            {
                put("message", "Vous avez perdu.");
            }
        };
        gameService.abandon();
        return response;
    }

    @GetMapping("/api/croupier/score")
    public Map<String, Object> getCroupierScore(){
        Croupier c = gameService.getCroupier();
        if(c.getCardsNumber() > 0) {
            Map<String, Object> response = new HashMap<String, Object>() {
                {
                    put("score", c.getScore());
                    put("cardsNumber", c.getCardsNumber());
                    put("firstCard", c.getHand().get(0));
                }
            };
            return response;
        }
        else {
            Map<String, Object> response = new HashMap<String, Object>() {
                {
                    put("score", c.getScore());
                    put("cardsNumber", c.getCardsNumber());
                    put("firstCard", new HashMap<String, String>());
                }
            };
            return response;
        }


    }


    @GetMapping("/api/current_party")
    public Partie getParty(){
        return gameService.getParty();
    }


    @PutMapping("/api/new_party")
    public void newParty(){
        gameService.startNewParty();
    }

    @PutMapping("/api/empty_hand")
    public void emptyHand(){
        gameService.emptyHands();
    }


}

