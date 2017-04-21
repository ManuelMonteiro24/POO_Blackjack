package src;

public interface Player{

  default void setBalance(int newBalance){
    this.balance = newBalance;
  }

  default int getBalance(){
    return this.balance;
  }
}
