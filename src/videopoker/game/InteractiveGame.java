package videopoker.game;

import videopoker.Player;
import videopoker.Dealer;
import videopoker.game.AbstractGame;
import videopoker.utils.CommandHandler;

public class InteractiveGame extends AbstractGame {

    public InteractiveGame(int iniBalance) {
        player = new Player(iniBalance);
        dealer = new Dealer();
        cmdHandler = new CommandHandler(System.in);
    }
}
