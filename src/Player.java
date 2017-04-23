package src;

//review visibilidades
//criar class hand??
public class Player{

  private Hand hand;
  private int balance;

  Player(int balance){
    this.hand = new Hand();
    this.balance = balance;
  }

  void updateBalance(int newBalance){
    this.balance += newBalance;
  }

  int getBalance(){
    return this.balance;
  }

  void setHand(Hand hand){
      this.hand = hand;
  }

  void showHand(){
    System.out.println(this.hand.toString());
  }

  int bet(int amount){

      if(this.balance == 0)
        return 0;

      if(amount > this.balance)
        amount = this.balance;

      this.balance -= amount;
      return amount;

  }

  Card[] hold(int[] indexes, Card[] newCards){
    return this.hand.renewHand(indexes, newCards);
  }
}
