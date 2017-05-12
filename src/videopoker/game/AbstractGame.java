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

public abstract class AbstractGame implements Game{

    protected CommandHandler cmdHandler;
    protected Player player;
    protected Dealer dealer;
    protected int betOnTheTable;

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
            } else if(command == 2){ //empty bet b; bet same as previous bet, or 5 if no bet was placed before
                if(this.betOnTheTable == 0)
                    this.betOnTheTable = 5; //bet 5
                this.betOnTheTable = this.player.bet(this.betOnTheTable);
                if(this.betOnTheTable == 0) {
                    System.out.println("player has no more funds");
                    System.exit(0);
                }
                valid = true;
            } else if(command == 3){
                this.betOnTheTable = this.player.bet(Integer.parseInt(input.split("(\\s{1,}+)")[1])); //get amount from userInput and bet()
                if(this.betOnTheTable == 0) {
                    System.out.println("player has no more funds");
                    System.exit(0);
                }
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

                Card[] ret = this.player.hold(holdIndexes, this.dealer.dealSecondCards(5 - holdIndexes.length));
                this.dealer.receiveCards(ret);
                System.out.printf("player's hand "); this.player.showHand();
                valid = true;

            } else if(command == 6) {

                Evaluator.updateEvaluator(this.player.getHand());
                dealer.getHandRank(); //update Evaluator static variable @handRank
                int[] advice = this.dealer.getAdvice();
                if(advice != null) {
                    advice = this.dealer.indexOrderedToUnordered(advice, this.player.getHand());
                    System.out.printf("player should hold cards ");
                    for(int index : advice)
                        System.out.printf(++index + " ");
                    System.out.printf("\n");
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
        this.dealer.receiveCards(this.player.releaseHand()); //return players cards to deck

        if(playerRank != HandRank.NON && playerRank != HandRank.PAIR)
            System.out.println("player wins with a " + playerRank + " and his credit is " + this.player.getBalance());
        else
            System.out.println("player loses and his credit is " + this.player.getBalance());

    }


}
