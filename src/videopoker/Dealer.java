package videopoker;

import videopoker.cards.*;

import java.util.ArrayList;

public class Dealer{

  private final static int[] creditPayout = {1,1,3,5,7,10,50,80,160,50,250};

  static Deck deck;

  public Dealer(){
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

    return new Hand(iniCards);
  }

  public Card[] dealSecondCards(int nbCards){
    Card[] iniCards = new Card[nbCards];

    for(int i = 0; i < iniCards.length; i++)
      iniCards[i] = deck.draw();

    return iniCards;
  }

  //returns money for player consoante a sua mao
  public int payout(Hand playerHand, int playerbet){

    //caso a bet nao seja 1,2,3,4 ou 5 dar erro fazer verificacao noutro lado

    //player loses bet
    if(playerHand.getRank() == 0)
      return 0;
    else
      return creditPayout[playerHand.getRank()]*playerbet;

  }

}
