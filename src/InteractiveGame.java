package src;

import src.*;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class InteractiveGame implements Game{

  static Scanner scanner;
  static Player player;
  static Dealer dealer;
  static int betOnTheTable;
  private static final String[] regexs = {"\\$(\\s*?)", "b(\\s*?)", "b(\\s{1,}+([0-5]?(\\s*?)))", "d(\\s*?)", "h(\\s{1,}+([1-5](\\s*?))){0,6}", "a(\\s*?)", "s(\\s*?)"};
  private static Pattern[] patterns;

  public InteractiveGame(int iniBalance, Scanner scan){
    scanner = scan;
    player = new Player(iniBalance);
    dealer = new Dealer();
    /*Initialize patterns with each regex @regexs for posterior comparison*/
    patterns = new Pattern[regexs.length];
    for(int i = 0; i < regexs.length; i++)
      patterns[i] = Pattern.compile(regexs[i]);
  }

  public static void main(String args[]){
      InteractiveGame n =  new InteractiveGame(10, new Scanner(System.in));
      n.betStage();
      n.dealStage();
      n.holdStage();
  }

  public void betStage(){
    String userInput;
    int command;
    boolean valid;
    do{
        userInput = scanner.nextLine();
        valid = false;
        if((command = handleCommand(userInput)) == 1){ //empty bet b; bet same as previous bet, or 5 if no bet was placed before
            if(betOnTheTable == 0)
                betOnTheTable = 5; //bet 5
            valid = true;
        } else if(command == 2){
            betOnTheTable = Integer.parseInt(userInput.split("(\\s{1,}+)")[1]); //get amount from userInput and bet()
            valid = true;
        } else if(command != 0)
            System.out.println(userInput + ": illegal command");
    } while(!valid);

    player.bet(betOnTheTable);
    dealer.shuffleDeck();
  }

  public void dealStage(){
      String userInput;
      int command;
      boolean valid;

      do{
          userInput = scanner.nextLine();
          valid = false;
          if((command = handleCommand(userInput)) == 3){
              player.setHand(dealer.dealFullHand());
              System.out.printf("hand: ");
              player.showHand();
              valid = true;
          } else if(command != 0)
              System.out.println(userInput + ": illegal command");
      } while(!valid);
  }

  public void holdStage(){
      String userInput;
      int command;
      boolean valid;

      do{
          userInput = scanner.nextLine();
          valid = false;
          if((command = handleCommand(userInput)) == 4){
              String[] buf = userInput.split("(\\s{1,}+)");
              int[] holdIndexes = new int[buf.length - 1];
              for(int i = 0; i < holdIndexes.length; i++) //parse indexes to int[]
                holdIndexes[i] = Integer.parseInt(buf[i+1]);

              Card[] ret = player.hold(holdIndexes, dealer.dealSecondCards(5 - holdIndexes.length));
              player.showHand();
              dealer.receiveCards(ret);
              valid = true;
          } else if(command != 0)
              System.out.println(userInput + ": illegal command");
      } while(!valid);
  }

  private int handleCommand(String cmd){
    int match = -1;
    for(Pattern pattern : patterns){
      if(pattern.matcher(cmd).matches()){
          if(++match == 0) //$ commad can be executed at any stage of the game
            System.out.println(player.getBalance());
        return match; //was already updated in the above if()
      }
      match++;
    }

    return match;
  }

}
