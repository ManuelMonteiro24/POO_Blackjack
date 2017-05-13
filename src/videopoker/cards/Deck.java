package videopoker.cards;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class Deck represents a deck of cards and implements related methods.
 * Instances of this class have all the functions a normal deck would.
 */
public class Deck{

  public ArrayList<Card> deck;
  private static final char[] suits = {'C','D','H','S'};

  /**
   * This Deck constructor initializes a deck with 52 distinct cards
   * represented by a unique value/suit combination.
   */
  public Deck(){
    this.deck = new ArrayList<Card>(52);
    for(int i = 0; i < suits.length; i++)
      for(int j = 0; j < 13; j++)
        this.deck.add(new Card(j + 1, suits[i]));
  }

  /**
   * This deck constructor instanciates a new Deck object initialized
   * with and external set of cards from @customDeck.
   * @param customDeck deck to be used in this Deck instance.
   */
  public Deck(ArrayList<Card> customDeck){
    deck = customDeck;
    System.out.println(Arrays.toString(deck.toArray()));
  }

  /**
   * Shuffles the deck.
   */
  public void shuffle(){
    Collections.shuffle(this.deck);
  }

    /**
     * Draws a card from the top of the @deck.
     *
     * @return the first card currently at the top of the deck.
     */
    public Card draw(){
        Card aux = this.deck.get(0);
        this.deck.remove(0);
        return aux;
    }

    /**
     * Receives an array of cards to add back to the @deck.
     * @param cards cards to add to the existing @deck.
     */
    public void receiveCards(Card[] cards){
        for(int i = 0; i < cards.length; i++)
            this.deck.add(cards[i]);
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
        for (Card s : deck)
            listString += s + " ";

        return listString;
    }

}
