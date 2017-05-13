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

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * This abstract class implements the Game interface built as
 * state machine for videopoker rounds.
 *
 * It implements the interface in such a way that it will work with
 * any game mode that receives external commands regarding the actions
 * to take in each round stage.
 *
 * It must be extended by a subclass that implements a less generic game mode.
 *
 * The actions taken at each stage are described in the Game interface class file.
 */
public abstract class AbstractGame implements Game{

    protected CommandHandler cmdHandler;
    protected Player player;
    protected Dealer dealer;
    protected int betOnTheTable;
    protected boolean noBetDeal = false;

    public void betStage(){
        String input;
        int command;
        boolean valid;
        do{
            if((input = this.cmdHandler.getCommand(this.getClass())) == null)
                System.exit(0);
            valid = false;
            if((command = this.cmdHandler.validateCommand(input)) == 8) {
                System.out.println("player quit the game with credit " + this.player.getBalance());
                System.exit(0);
            } else if ((command == 4) && (this.betOnTheTable > 0)) {
                int bet;
                if(this.betOnTheTable == 0)
                    bet = 5;
                else
                    bet = this.betOnTheTable;

                this.betOnTheTable = this.player.bet(bet);

                if(this.betOnTheTable == 0) {
                    System.out.println("player has no more funds");
                    System.exit(0);
                }
                if (bet > this.betOnTheTable)
                    System.out.println("player only has " + this.betOnTheTable + " credits, betted all");

                this.noBetDeal = true;
                break;
                
            } else if(command == 2){ //empty bet b; bet same as previous bet, or 5 if no bet was placed before
                int bet;
                if(this.betOnTheTable == 0)
                    bet = 5;
                else
                    bet = this.betOnTheTable;

                this.betOnTheTable = this.player.bet(bet);

                if(this.betOnTheTable == 0) {
                    System.out.println("player has no more funds");
                    System.exit(0);
                }
                if (bet > this.betOnTheTable)
                    System.out.println("player only has " + this.betOnTheTable + " credits, betted all");

                valid = true;
            } else if(command == 3){
                int bet = Integer.parseInt(input.split("(\\s{1,}+)")[1]);
                this.betOnTheTable = this.player.bet(bet); //get amount from userInput and bet()
                if(this.betOnTheTable == 0) {
                    System.out.println("player has no more funds");
                    System.exit(0);
                }
                if (bet > this.betOnTheTable)
                    System.out.println("player only has " + this.betOnTheTable + " credits, betted all");
                valid = true;
            } else if(command == 7) {
                this.player.statistics();
            } else if(command != 1)
                System.out.println(input + ": illegal command");
            else
                System.out.println(this.player.getBalance());
        } while(!valid);

    }

    public void dealStage(){
        String input;
        int command;
        boolean valid;

        //@this will be of the same class as its constructor
        if(this instanceof InteractiveGame)
            this.dealer.shuffleDeck();

        if (noBetDeal) {
            this.player.setHand(this.dealer.dealFullHand());
            System.out.printf("player's hand "); this.player.showHand();
            noBetDeal = false;
        } else {
            do {
                if ((input = this.cmdHandler.getCommand(this.getClass())) == null)
                    System.exit(0);
                valid = false;
                if ((command = this.cmdHandler.validateCommand(input)) == 4) {
                    this.player.setHand(this.dealer.dealFullHand());
                    System.out.printf("player's hand "); this.player.showHand();
                    valid = true;
                } else if(command == 7){
                    this.player.statistics();
                } else if(command != 1)
                    System.out.println(input + ": illegal command");
                else
                    System.out.println(this.player.getBalance());
            } while(!valid);
        }
    }

    public void holdStage(){
        String input;
        int command;
        boolean valid;

        do{
            if((input = this.cmdHandler.getCommand(this.getClass())) == null)
                System.exit(0);

            valid = false;
            if((command = this.cmdHandler.validateCommand(input)) == 5){

                String[] buf = input.split("(\\s{1,}+)");
                int[] holdIndexes = new int[buf.length - 1];

                for(int i = 0; i < holdIndexes.length; i++) //parse indexes to int[]
                    holdIndexes[i] = Integer.parseInt(buf[i+1]);

                if(this instanceof DebugGame) { //check if there are more cards in deck in debug mode
            		if(this.dealer.cardsLeftCount() < (5 - holdIndexes.length)) {  //if so terminate program
                		System.out.println("deck only has " + this.dealer.cardsLeftCount() + " cards left");
            			continue;
            		}
        		}

                Card[] ret = this.player.hold(holdIndexes, this.dealer.dealSecondCards(5 - holdIndexes.length));
                this.dealer.receiveCards(ret);
                System.out.printf("player's hand "); this.player.showHand();
                valid = true;



            } else if(command == 6) {

                int[] decision;
                int[] holdIndexes = {};

                this.dealer.updateEvaluator(this.player.getHand());
                this.dealer.getHandRank();

                if ((decision = this.dealer.getAdvice()) != null) {
                    holdIndexes = this.dealer.indexOrderedToUnordered(decision, this.player.getHand());
                    System.out.print("player should hold cards ");
                    for (int i = 0; i < holdIndexes.length; i++)
                        System.out.print(++holdIndexes[i] + " ");
                    System.out.println();
                } else
                    System.out.println("player should hold no cards ");

            } else if(command == 7) {
                this.player.statistics();
            } else if(command != 1)
                System.out.println(input + ": illegal command");
            else
                System.out.println(this.player.getBalance());
        } while(!valid);
    }

    public void evaluationStage(){

        Evaluator.updateEvaluator(this.player.getHand());
        HandRank playerRank = this.dealer.getHandRank();
        this.player.updateBalance(this.dealer.payout(this.betOnTheTable));
        this.player.updateScoreboard(playerRank);

        if(this instanceof InteractiveGame) //Debug mode doesnt return cards played to deck
          this.dealer.receiveCards(this.player.releaseHand()); //return players cards to deck

        if(playerRank != HandRank.NON && playerRank != HandRank.PAIR)
            System.out.println("player wins with a " + playerRank + " and his credit is " + this.player.getBalance());
        else
            System.out.println("player loses and his credit is " + this.player.getBalance());

        if(this instanceof DebugGame) { //check if there are more cards in deck in debug mode
            if(this.dealer.cardsLeftCount() < 5) {  //if so terminate program
                System.out.println("no more cards to play the commands");
                System.exit(0);
            }
        }

    }


}
