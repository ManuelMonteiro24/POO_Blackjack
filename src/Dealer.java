package src;

public class Dealer{

  static Deck deck;

  Dealer(){
    deck = new Deck();
  }

  void rcvCards(Card[] usedcards){
    deck.rcvCards(usedcards);
  }

  Hand dealFullHand(Deck deck){

    Card[] iniCards = new Card[5];

    for(int i = 0; i < 5; i++)
      iniCards[i] = deck.draw();

    return new Hand(iniCards);
  }

  Card[] dealSecondCards(Deck deck, int nbCards){
    Card[] iniCards = new Card[nbCards];

    for(int i = 0; i < iniCards.length; i++)
      iniCards[i] = deck.draw();

    return iniCards;
  }

}
