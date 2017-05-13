package videopoker;

import videopoker.cards.*;
import videopoker.utils.HandRank;
import videopoker.utils.HandEvaluator;
import videopoker.evaluators.Adviser;

import java.util.ArrayList;

/**
* Class used to represent the dealer in the various game modes.
* It has all the functionalities a dealer should have like suffling
* the deck or advising about the best play to make.
*
* It extends the HandEvaluator class so this all Dealer objects can have
* the ability to correctly evaluate any given 5 card hand.
*/
public class Dealer extends HandEvaluator {

  private final static int[] creditPayout = {0, 0, 1, 1, 3, 5, 7, 10, 50, 80, 160, 50, 250}; //one for each element of the HandRank enum
  private int rank;
  public Adviser adviser;
  public Deck deck;

  /**
  * Empty Dealer constructor. Initializes the instance attributes and calls its
  * super class constructor. Returns a Dealer instance with a full 52 card deck.
  * @return the instance of the class Dealer created
  */
  public Dealer() {
    super();
    deck = new Deck();
    this.adviser = new Adviser();
  }

  /**
  * Constructor that does the same initializations as first one with the exception
  * that it instanciates a Dealer object with a custom Deck containing only the cards @customDeck.
  *
  * @param customDeck ArrayList of Card objects to initialize the deck @deck with.
  * @return the instance of the class Dealer created
  */
  public Dealer(ArrayList<Card> customDeck) {
    super();
    deck = new Deck(customDeck);
    this.adviser = new Adviser();
  }

  /**
  * Returns the cards at @usedCards to the bottom of the deck.
  *
  * @param usedcards cards to be returned to the deck.
  */
  public void receiveCards(Card[] usedcards) {
    deck.receiveCards(usedcards);
  }

  /**
  * Shuffles the deck @deck.
  */
  public void shuffleDeck() {
    deck.shuffle();
  }

  /**
  * Deals a full hand of five cards returning the corresponding Hand object.
  *
  * @return Hand object representing a full five Card hand.
  */
  public Hand dealFullHand() {
    Card[] iniCards = new Card[5];

    for (int i = 0; i < 5; i++)
    iniCards[i] = deck.draw();

    Hand playersHand = new Hand(iniCards);
    return playersHand;
  }

  /**
  * Used when the there is a need to deal more cards to the player after the first deal.
  * It deals only the number of cards specified by @nbCards.
  *
  * @param nbCards number of cards to be dealt.
  * @return Card array with the cards to be dealt.
  */
  public Card[] dealSecondCards(int nbCards) {
    Card[] iniCards = new Card[nbCards];

    for (int i = 0; i < iniCards.length; i++)
    iniCards[i] = deck.draw();

    return iniCards;
  }

  public int[] getAdvice() {
    return this.adviser.getAdvice();
  }

  /**
  * Calculates the players payout according to the bet placed at the beggining of
  * the round in the bet stage and the player's hand rank. This method uses the multipliers
  * form @creditPayout to calculate the final payout.
  *
  * @param playerbet bet placed by the player at the bet stage of the game.
  * @return amount the player should receive.
  */
  public int payout(int playerbet) {

    HandRank[] plays = HandRank.values();
    for (int i = 0; i < plays.length; i++)
    if (handRank == plays[i])
    return creditPayout[i] * playerbet;

    return 0;
  }

  /**
  * Method that checks on many cards are still left on the deck
  * @return the number of cards that are in the deck
  */
  public int cardsLeftCount() {
    return this.deck.getRemainingCardsCount();
  }

}
