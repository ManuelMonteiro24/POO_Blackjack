package src;

import java.util.Collections;
import java.util.ArrayList;

//review visibilidades
public class Deck{

  private ArrayList<Card> deck = new ArrayList<Card>(52);
  private static final char[] suits = {'C','D','H','S'};

  Deck(){
    for(int i = 0; i < suits.length; i++)
      for(int j = 0; j < 13; j++)
        this.deck.add(new Card(j + 1, suits[i]));
  }

  void shuffle(){
    Collections.shuffle(this.deck);
  }

  //remove card from top of deck
  Card draw(){
    Card aux = this.deck.get(0);
    this.deck.remove(0);
    return aux;
  }

  //return played cards to the deck
  void receiveCards(Card[] cards){
    for(int i = 0; i < cards.length; i++)
      this.deck.add(cards[i]);
  }  

}
