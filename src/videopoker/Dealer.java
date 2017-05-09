package videopoker;

import videopoker.cards.*;
import videopoker.utils.HandRank;
import videopoker.utils.HandEvaluator;

import java.util.ArrayList;

public class Dealer extends HandEvaluator{

    private final static int[][] creditPayout = { {0, 0, 1, 1, 3, 5, 7, 10, 50, 80, 160, 50, 250}, //one for each element of the HandRank enum
                                                  {0, 0, 2, 2, 6, 10, 14, 20, 100, 160, 320, 100, 500},
                                                  {0, 0, 3, 3, 9, 15, 21, 30, 150, 240, 480, 150, 750},
                                                  {0, 0, 4, 4, 12, 20, 28, 40, 200, 320, 640, 200, 1000},
                                                  {0, 0, 5, 5, 15, 25, 35, 50, 250, 400, 800, 250, 4000}};
    private int rank;
    static Deck deck;

    public Dealer(){
        super();
        deck = new Deck();
    }

    public Dealer(ArrayList<Card> customDeck){
        deck = new Deck(customDeck);
    }

    public void receiveCards(Card[] usedcards){
        deck.receiveCards(usedcards);
    }

    public void shuffleDeck(){
        deck.shuffle();
    }

    public Hand dealFullHand(){
        Card[] iniCards = new Card[5];

        for(int i = 0; i < 5; i++)
            iniCards[i] = deck.draw();
        Hand playersHand = new Hand(iniCards);
        return playersHand;
    }

    public Card[] dealSecondCards(int nbCards){
        Card[] iniCards = new Card[nbCards];

        for(int i = 0; i < iniCards.length; i++)
            iniCards[i] = deck.draw();

        return iniCards;
    }

    //returns money for player consoante a sua mao
    public int payout(int playerbet){

        HandRank[] plays = HandRank.values();
        for (int i = 0; i < plays.length; i++)
            if (this.handRank == plays[i])
                return creditPayout[playerbet-1][i] * playerbet;

        return 0;
    }

}
