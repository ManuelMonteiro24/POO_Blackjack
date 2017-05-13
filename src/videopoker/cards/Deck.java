package videopoker.cards;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

//review visibilidades
public class Deck{

  public ArrayList<Card> deck;
  private static final char[] suits = {'C','D','H','S'};

  public Deck(){
    this.deck = new ArrayList<Card>(52);
    for(int i = 0; i < suits.length; i++)
      for(int j = 0; j < 13; j++)
        this.deck.add(new Card(j + 1, suits[i]));
  }

  public Deck(ArrayList<Card> customDeck){
    deck = customDeck;
    System.out.println(Arrays.toString(deck.toArray()));
  }

  public void shuffle(){
    Collections.shuffle(this.deck);
  }

  //remove card from top of deck
  public Card draw(){
    Card aux = this.deck.get(0);
    this.deck.remove(0);
    return aux;
  }

  //return played cards to the deck
  public void receiveCards(Card[] cards){
    for(int i = 0; i < cards.length; i++)
      this.deck.add(cards[i]);
    System.out.printf("");
  }

  //check if deck is empty (debug mode termination),
  //or less that 5 cards -> error in cards.txt format
  public boolean isEmpty(){
    if(deck.size() < 5)
      return true;
    else
      return false;
  }


  //Debug function
  @Override
  public String toString(){
    String listString = "";
    for (Card s : deck) {
      listString += s + " ";
    }

    return listString;
  }

}
