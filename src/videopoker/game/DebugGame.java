package videopoker.game;

import videopoker.Player;
import videopoker.Dealer;
import videopoker.cards.Deck;
import videopoker.cards.Card;
import videopoker.game.AbstractGame;
import videopoker.utils.CommandHandler;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

/**
 * Subclass of AbstractGame.
 * Uses the AbstractGame implementation of the Game interface for
 * handling the actions taken at each videopoker round stage.
 *
 * In this game mode, the commands as well as the deck of cards to use
 * are read from a file using the CommandHandler object and the getCardsFromFile()
 * instance method.
 */
public class DebugGame extends AbstractGame{

    /**
     * DebugGame constructor that instanciates a Player, a Dealer with a specific set
     * of cards to use in the deck and a CommandHandler that will read and validates
     * commmands read from a file.
     */
    public DebugGame(int iniBalance, String cmdFile, String cardsFile){
        this.player = new Player(iniBalance);
        this.dealer = new Dealer(getCardsFromFile(new File(cardsFile)));
        this.cmdHandler = new CommandHandler(new File(cmdFile));
    }

    /**
     * Reads a file containing the set of cards to use in the this game's deck and
     * creates and returns the corresponding Card class instances.
     *
     * @param cards File instance of the file containing the set of cards to use in the deck.
     * @return array of Card objects representing the cards read from the file.
     */
    private ArrayList<Card> getCardsFromFile(File cards){
        char[] buf;
        int cardNum;
        ArrayList<Card> deck = new ArrayList<Card>();
        Pattern cardFormat = Pattern.compile("[\\p{Alpha}\\p{Digit}][HSJD]");

        try {
            Scanner scan = new Scanner(cards);
            while (scan.hasNext()) {
                buf = scan.next().toCharArray();

                switch(buf[0]){
                    case 'A':
                        cardNum = 1;
                        break;
                    case 'T':
                        cardNum = 10;
                        break;
                    case 'J':
                        cardNum = 11;
                        break;
                    case 'Q':
                        cardNum = 12;
                        break;
                    case 'K':
                        cardNum = 13;
                        break;
                    default:
                        cardNum = (int) buf[0] - 48;
                        break;
                }

                deck.add(new Card(cardNum, buf[1]));
            }
        }catch(FileNotFoundException fnfex){
            fnfex.printStackTrace();
        }

        return deck;
    }


}
