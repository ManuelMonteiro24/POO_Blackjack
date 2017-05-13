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

public class DebugGame extends AbstractGame{

    public DebugGame(int iniBalance, String cmdFile, String cardsFile){
        this.player = new Player(iniBalance);
        this.dealer = new Dealer(getCardsFromFile(new File(cardsFile)));
        this.cmdHandler = new CommandHandler(new File(cmdFile));
    }

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
