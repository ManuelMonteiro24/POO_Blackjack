package videopoker.game;

import videopoker.game.Game;
import videopoker.cards.*;
import videopoker.Player;
import videopoker.Dealer;
import videopoker.utils.CommandHandler;
import videopoker.utils.HandEvaluator;
import videopoker.utils.HandRank;
import videopoker.game.Scoreboard;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AbstractGame implements Game{

    protected static CommandHandler cmdHandler;
    protected static Player player;
    protected static Dealer dealer;
    protected static int betOnTheTable;

    public void betStage(){
        String input;
        int command;
        boolean valid;
        do{
            if((input = cmdHandler.getCommand(this.getClass())) == null)
                System.exit(0);
            valid = false;
            if((command = cmdHandler.validateCommand(input)) == 8) {
                System.out.println("player quit the game with credit " + player.getBalance());
                System.exit(0);
            } else if(command == 2){ //empty bet b; bet same as previous bet, or 5 if no bet was placed before
                if(betOnTheTable == 0)
                    betOnTheTable = 5; //bet 5
                betOnTheTable = player.bet(betOnTheTable);
                if(betOnTheTable == 0) {
                    System.out.println("player has no more funds");
                    System.exit(0);
                }
                valid = true;
            } else if(command == 3){
                betOnTheTable = player.bet(Integer.parseInt(input.split("(\\s{1,}+)")[1])); //get amount from userInput and bet()
                if(betOnTheTable == 0) {
                    System.out.println("player has no more funds");
                    System.exit(0);
                }
                valid = true;
            } else if(command == 7) {
                player.statistics();
            } else if(command != 1)
                System.out.println(input + ": illegal command");
            else
                System.out.println(player.getBalance());
        } while(!valid);

    }

    public void dealStage(){
        String input;
        int command;
        boolean valid;

        //@this will be of the same class as its constructor
        if(this instanceof InteractiveGame)
            dealer.shuffleDeck();

        do {
            if ((input = cmdHandler.getCommand(this.getClass())) == null)
                System.exit(0);
            valid = false;
            if ((command = cmdHandler.validateCommand(input)) == 4) {
                player.setHand(dealer.dealFullHand());
                System.out.printf("player's hand "); player.showHand();
                valid = true;
            } else if(command == 7){
                player.statistics();
            } else if(command != 1)
                System.out.println(input + ": illegal command");
            else
                System.out.println(player.getBalance());
        } while(!valid);
    }

    public void holdStage(){
        String input;
        int command;
        boolean valid;

        do{
            if((input = cmdHandler.getCommand(this.getClass())) == null)
                System.exit(0);

            valid = false;
            if((command = cmdHandler.validateCommand(input)) == 5){
                String[] buf = input.split("(\\s{1,}+)");
                int[] holdIndexes = new int[buf.length - 1];
                for(int i = 0; i < holdIndexes.length; i++) //parse indexes to int[]
                    holdIndexes[i] = Integer.parseInt(buf[i+1]);

                Card[] ret = player.hold(holdIndexes, dealer.dealSecondCards(5 - holdIndexes.length));
                System.out.printf("player's hand "); player.showHand();
                dealer.receiveCards(ret);
                valid = true;

            } else if(command == 6) {

                dealer.updateEvaluator(player.getHand());
                int[] advice = dealer.getAdvice();
                if(advice != null) {
                    advice = dealer.indexOrderedToUnordered(advice, player.getHand());
                    System.out.printf("player should hold cards ");
                    for(int index : advice)
                        System.out.printf(++index + " ");
                    System.out.printf("\n");
                } else
                    System.out.println("player should hold no cards ");

            } else if(command == 7) {
                player.statistics();
            } else if(command != 1)
                System.out.println(input + ": illegal command");
            else
                System.out.println(player.getBalance());
        } while(!valid);
    }

    public void evaluationStage(){

        dealer.updateEvaluator(player.getHand());
        HandRank playerRank = dealer.evaluate();
        player.updateBalance(dealer.payout(betOnTheTable));
        player.updateScoreboard(playerRank);

        if(playerRank != HandRank.NON && playerRank != HandRank.PAIR)
            System.out.println("player wins with a " + playerRank + " and his credit is " + player.getBalance());
        else
            System.out.println("player loses and his credit is " + player.getBalance());
    }


}
