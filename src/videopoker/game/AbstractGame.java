package videopoker.game;

import videopoker.game.Game;
import videopoker.cards.*;
import videopoker.Player;
import videopoker.Dealer;
import videopoker.utils.CommandHandler;

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
        String userInput;
        int command;
        boolean valid;
        do{
            userInput = cmdHandler.getCommand(this.getClass());
            valid = false;
            if((command = cmdHandler.validateCommand(userInput)) == 2){ //empty bet b; bet same as previous bet, or 5 if no bet was placed before
                if(betOnTheTable == 0)
                    betOnTheTable = 5; //bet 5
                valid = true;
            } else if(command == 3){
                betOnTheTable = Integer.parseInt(userInput.split("(\\s{1,}+)")[1]); //get amount from userInput and bet()
                valid = true;
            } else if(command != 1)
                System.out.println(userInput + ": illegal command");
            else
                System.out.println(player.getBalance());
        } while(!valid);

        player.bet(betOnTheTable);
        if(this instanceof InteractiveGame)
            dealer.shuffleDeck();

    }

    public void dealStage(){
        String userInput;
        int command;
        boolean valid;

        do{
            userInput = cmdHandler.getCommand(this.getClass());
            valid = false;
            if((command = cmdHandler.validateCommand(userInput)) == 4){
                player.setHand(dealer.dealFullHand());
                System.out.printf("player's hand "); player.showHand();
                valid = true;
            } else if(command != 1)
                System.out.println(userInput + ": illegal command");
            else
                System.out.println(player.getBalance());
        } while(!valid);
    }

    public void holdStage(){
        String userInput;
        int command;
        boolean valid;

        do{
            userInput = cmdHandler.getCommand(this.getClass());
            valid = false;
            if((command = cmdHandler.validateCommand(userInput)) == 5){
                String[] buf = userInput.split("(\\s{1,}+)");
                int[] holdIndexes = new int[buf.length - 1];
                for(int i = 0; i < holdIndexes.length; i++) //parse indexes to int[]
                    holdIndexes[i] = Integer.parseInt(buf[i+1]);

                Card[] ret = player.hold(holdIndexes, dealer.dealSecondCards(5 - holdIndexes.length));
                System.out.printf("player's hand "); player.showHand();
                dealer.receiveCards(ret);
                valid = true;
            } else if(command != 1)
                System.out.println(userInput + ": illegal command");
            else
                System.out.println(player.getBalance());
        } while(!valid);
    }

    public void evaluationStage(){

    }


}
