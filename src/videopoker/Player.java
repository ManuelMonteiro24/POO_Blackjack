package videopoker;

import videopoker.cards.*;

public class Player{

  private Hand hand;
  private int balance;

  public Player(int balance){
    this.hand = new Hand();
    this.balance = balance;
  }

  public void updateBalance(int newBalance){
    this.balance += newBalance;
  }

  public int getBalance(){
    return this.balance;
  }

  public void setHand(Hand hand){
      this.hand = hand;
  }

  public void showHand(){
    System.out.println(this.hand.toString());
  }

  //Player can only bet 1,2,3,4 or 5 credits others values are invalid
  //make this check where the method is called
  public int bet(int amount){

      if(this.balance == 0)
        return 0;

      if(amount > this.balance)
        amount = this.balance;

      this.balance -= amount;
      return amount;

  }

  public Card[] hold(int[] indexes, Card[] newCards){
    return this.hand.renewHand(indexes, newCards);
  }
}
