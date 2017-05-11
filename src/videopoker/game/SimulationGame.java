package videopoker.game;

import videopoker.game.Game;
import videopoker.cards.*;
import videopoker.Player;
import videopoker.Dealer;
import videopoker.utils.CommandHandler;
import videopoker.utils.HandEvaluator;
import videopoker.utils.HandRank;
import videopoker.game.Scoreboard;

public class SimulationGame implements Game{

    private final int betOnTheTable;
    private final int nbDeals;
    private int roundCounter;
    private Player player;
    private Dealer dealer;

    public SimulationGame(int initialBalance, int bet, int nbDeals ){
        player = new Player(initialBalance);
        dealer = new Dealer();
        this.betOnTheTable = bet;
        this.nbDeals = nbDeals;
        this.roundCounter = 0;
    }

    public void betStage(){

        if(player.bet(this.betOnTheTable) != this.betOnTheTable) {
            System.out.println("player has no more funds");
            System.exit(0);
        }
        this.roundCounter++;
    }

    public void dealStage(){
        dealer.shuffleDeck();
        player.setHand(dealer.dealFullHand());
    }

    public void holdStage() {
        int[] decision;
        int[] holdIndexes = {};

        dealer.updateEvaluator(player.getHand());
        player.showHand();
        if ((decision = dealer.getAdvice()) != null){
            holdIndexes = dealer.indexOrderedToUnordered(decision, player.getHand());
            for(int i = 0; i < holdIndexes.length; i++)
                holdIndexes[i]++; //increment indexes because player.hold() uses indexes starting at 1
        }

        Card[] ret = player.hold(holdIndexes, dealer.dealSecondCards(5 - holdIndexes.length));
        dealer.receiveCards(ret);
    }

    public void evaluationStage(){

        dealer.updateEvaluator(player.getHand());
        HandRank playerRank = dealer.evaluate();
        player.updateBalance(dealer.payout(this.betOnTheTable));
        player.updateScoreboard(playerRank);
        dealer.receiveCards(player.releaseHand());

        if(this.roundCounter == this.nbDeals)
            player.statistics();

    }
      
}
