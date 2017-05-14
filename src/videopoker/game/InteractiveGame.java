package videopoker.game;

import videopoker.Player;
import videopoker.Dealer;
import videopoker.game.AbstractGame;
import videopoker.utils.CommandHandler;

/**
* Subclass of AbstractGame.
* Uses the AbstractGame implementation of the Game interface for
* handling the actions taken at each videopoker round stage.
*
* In this game mode, the commands are received through the standard input
* directly from the user
*/
public class InteractiveGame extends AbstractGame {

  /**
  * InteractiveGame constructor that instanciates a Player, a Dealer with a
  * complete 52 card deck and a CommandHandler that will read and validate
  * commmands read from the standard input.
  * @param  iniBalance    player initial balance
  */
  public InteractiveGame(int iniBalance) {
    this.player = new Player(iniBalance);
    this.dealer = new Dealer();
    this.cmdHandler = new CommandHandler(System.in);
  }
}
