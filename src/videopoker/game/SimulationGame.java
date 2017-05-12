package videopoker.game;

import videopoker.game.Game;
import videopoker.cards.*;
import videopoker.Player;
import videopoker.Dealer;
import videopoker.utils.CommandHandler;
import videopoker.utils.HandEvaluator;
import videopoker.utils.HandRank;
import videopoker.game.Scoreboard;
import videopoker.evaluators.Evaluator;

public class SimulationGame implements Game{

    private final int betOnTheTable;
    private final int nbDeals;
    private int roundCounter;
    private Player player;
    private Dealer dealer;

    public SimulationGame(int initialBalance, int bet, int nbDeals ){
        this.player = new Player(initialBalance);
        this.dealer = new Dealer();
        this.betOnTheTable = bet;
        this.nbDeals = nbDeals;
        this.roundCounter = 0;
    }

    public void betStage(){

        if(this.player.bet(this.betOnTheTable) != this.betOnTheTable) {
            System.out.println("player has no more funds");
            System.exit(0);
        }
        this.roundCounter++;
    }

    public void dealStage(){
        this.dealer.shuffleDeck();
        this.player.setHand(this.dealer.dealFullHand());
    }

    public void holdStage() {
        int[] decision;
        int[] holdIndexes = {};

        player.showHand();
        this.dealer.updateEvaluator(this.player.getHand());
        this.dealer.getHandRank(); //set Evaluator's static variable @handRank
        if ((decision = this.dealer.getAdvice()) != null){
            holdIndexes = this.dealer.indexOrderedToUnordered(decision, this.player.getHand());
            for(int i = 0; i < holdIndexes.length; i++)
                holdIndexes[i]++; //increment indexes because this.player.hold() uses indexes starting at 1
        }

        Card[] ret = this.player.hold(holdIndexes, this.dealer.dealSecondCards(5 - holdIndexes.length));
        this.dealer.receiveCards(ret);
        player.showHand();
    }

    public void evaluationStage(){

        Evaluator.updateEvaluator(this.player.getHand());
        HandRank playerRank = this.dealer.getHandRank();
        this.player.updateBalance(this.dealer.payout(this.betOnTheTable));
        this.player.updateScoreboard(playerRank);
        this.dealer.receiveCards(this.player.releaseHand());

        if(this.roundCounter == this.nbDeals)
            this.player.statistics();

    }
      
}
