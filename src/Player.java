package src;

//review visibilidades
//criar class hand??
public class Player{

  private Card[] hand = new Card[5];
  private int balance;

  Player(int balance){
    this.balance = balance;
  }

  void setBalance(int newBalance){
    this.balance = newBalance;
  }

  int getBalance(){
    return this.balance;
  }

  void getHand(Card[] hand){
    this.hand = hand;
  }

  void replaceCard(int index, Card newcard){
    hand[index] = newcard;
  }


  @Override
  public String toString(){
    String aux = "players's hand ";
    //Condition for empty hand
    /*if(hand == )
      return "Empty hand";
    */
    for(int i = 0; i < 5; i++)
      aux += hand[i].toString();

    return aux;
  }


}
