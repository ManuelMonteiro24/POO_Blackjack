package src;

import java.util.Scanner;

public class Game{

  Scanner scanner = new Scanner(System.in);
  static Player player;
  static Dealer dealer;
  static int bet;

  Game(int iniBalance){
    player = new Player(iniBalance);
    dealer = new Dealer();
  }

  void initRound(){

  }

  void 
  private void rcvCmd(){

    String playerInput;
    boolean valid;

    do{
      valid = true;
      playerInput = scanner.nextLine();

      switch(playerInput.charAt(0)){

        case 'b':
              break;
        case '$':
              break;
        case 'd':
              break;
        case 'h':
              break;
        case 'a':
              break;
        case 's':
              break;
        default:
              System.out.println("ERROR: invalid input");
              valid = false;
              break;
      }

    } while(!valid);
  }

}
