package videopoker.game;

import videopoker.Player;
import videopoker.Dealer;
import videopoker.game.AbstractGame;
import videopoker.utils.CommandHandler;

public class InteractiveGame extends AbstractGame {

    public InteractiveGame(int iniBalance) {
        this.player = new Player(iniBalance);
        this.dealer = new Dealer();
        this.cmdHandler = new CommandHandler(System.in);
    }
}
