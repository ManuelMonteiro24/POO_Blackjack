package videopoker;

import videopoker.cards.*;
import videopoker.utils.HandRank;
import videopoker.utils.HandEvaluator;
import videopoker.evaluators.Adviser;

import java.util.ArrayList;

public class Dealer extends HandEvaluator{

    private final static int[] creditPayout = {0, 0, 1, 1, 3, 5, 7, 10, 50, 80, 160, 50, 250}; //one for each element of the HandRank enum
    private int rank;
    private Adviser adviser;
    public static Deck deck;

    public Dealer(){
        super();
        deck = new Deck();
        this.adviser = new Adviser();
    }

    public Dealer(ArrayList<Card> customDeck){
        super();
        deck = new Deck(customDeck);
        this.adviser = new Adviser();
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

    public int[] getAdvice(){
        return this.adviser.getAdvice();
    }

    //returns money for player consoante a sua mao
    public int payout(int playerbet){

        HandRank[] plays = HandRank.values();
        for (int i = 0; i < plays.length; i++)
            if (this.handRank == plays[i])
                return creditPayout[i] * playerbet;

        return 0;
    }

    //check if deck is empty (DebugGame termination)
    public boolean checkEmptyDeck(){
      return this.deck.isEmpty();
    }

    public String printDeck() {
        return this.deck.toString();
    }

}
