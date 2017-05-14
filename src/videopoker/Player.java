package videopoker;

import videopoker.cards.*;
import videopoker.game.Scoreboard;
import videopoker.utils.HandRank;

import java.util.Arrays;

/**
* Class used to represent the player in the various game modes.
* It has all the functionalities a player should have like betting.
*
* It has a Scoreboard object to maintain track of the players statistics and
* gains.
*/
public class Player{

  private Hand hand;
  private int balance;
  public Scoreboard scoreboard;

  /**
  * Class constructor that initializes all instance variables.
  * @param balance initial player balance.
  */
  public Player(int balance){
    this.hand = new Hand();
    this.balance = balance;
    this.scoreboard = new Scoreboard(balance);
  }

  /**
  * Verifies if the player can actualy bet the amount specified by @bet.
  * If the player's balance is smaller than the bet it wishes to place, then
  * the remaining player balance is betted.
  *
  * @param amount amount to bet.
  * @return amount actualy betted.
  */
  public int bet(int amount){

    if(this.balance == 0)
    return 0;

    if(amount > this.balance)
    amount = this.balance;

    this.balance -= amount;
    return amount;

  }

  public int getBalance(){
    return this.balance;
  }

  public Hand getHand(){
    return this.hand;
  }

  /**
  * Calls the method renewHand() from the Hand class and updates the player's @hand
  * according to indexes of the cards to hold, @indexes, and the cards that will replace
  * the non-holded cards, @newCards.
  *
  * @param indexes indexes of cards to hold.
  * @param newCards Card array with replacement cards.
  * @return Card array with the discarded cards from the players hand.
  */
  public Card[] hold(int[] indexes, Card[] newCards){
    return this.hand.renewHand(indexes, newCards);
  }

  /**
  * Sets a new value for the @hand instance variable.
  * @param hand receives the player current hand
  */
  public void setHand(Hand hand){
    this.hand = hand;
  }

  /**
  * Returns all the cards the player has in its @hand.
  * @return the players current hand.
  */
  public Card[] releaseHand(){
    return this.hand.toCardArray();
  }

  /**
  * Prints to the terminal the players hand by evoquing the toString() method
  * of the Hand class.
  */
  public void showHand(){
    System.out.println(this.hand.toString());
  }

  /**
  * Prints to the terminal the player's statistics calculated and maintained by this
  * Scoreboard instance variable, @scoreboard.
  */
  public void statistics(){
    System.out.println(this.scoreboard.toString());
  }

  /**
  * Updates de player's balance adding to it the amount specified by @newBalance.
  * @param newBalance amount to be added to player's current balance.
  */
  public void updateBalance(int newBalance){
    this.balance += newBalance;
  }

  /**
  * Updates this Scoreboard instance, @scoreboard, based on the rank of the players current @hand.
  * @param rank HandRank value of the players current @hand.
  */
  public void updateScoreboard(HandRank rank){
    this.scoreboard.receiveRoundInfo(rank, this.balance);
  }

}
