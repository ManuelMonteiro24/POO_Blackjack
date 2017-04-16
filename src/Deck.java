package src;

import java.util.Collections;
import java.util.ArrayList;

//review visibilidades
public class Deck{

  //What is the best data structure???
  private ArrayList<Card> deck = new ArrayList<Card>(52);

  Deck(){
    char[] aux = {'C','D','H','S'};

    for(int i = 0; i < aux.length; i++)
      for(int j = 0; j < 13; j++)
        this.deck.add(new Card(j,aux[i]));
  }

  void shuffle(){
    Collections.shuffle(this.deck);
  }

  Card draw(){
    Card aux = this.deck.get(0); //remove card from top of deck
    this.deck.remove(0);
    return aux;
  }

  void rcvCards(Card[] cards){

    for(int i = 0; i <cards.length ; i++)
      this.deck.add(cards[i]);
  }

}
