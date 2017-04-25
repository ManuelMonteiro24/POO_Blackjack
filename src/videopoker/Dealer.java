package videopoker;

import videopoker.cards.*;

import java.util.ArrayList;

public class Dealer{

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

}
