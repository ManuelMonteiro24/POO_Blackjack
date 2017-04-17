package src;

import java.util.Scanner;

public class Game{

  Scanner scanner = new Scanner(System.in);


  String rcvCmd(){

    String playerInput, aux;

    playerInput =(scanner.nextLine());

    switch(playerInput.charAt(0)){

      case b:
            break;
      case $:
            break;
      case d:
            break;
      case h:
            break;
      case a:
            break;
      case s:
            break;
      default:
            //Invalid command
            break;
    }
  }

}
