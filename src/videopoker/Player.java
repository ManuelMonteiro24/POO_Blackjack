package videopoker;

import videopoker.cards.*;
import videopoker.game.Scoreboard;
import videopoker.utils.HandRank;

public class Player{

    private Hand hand;
    private int balance;
    public Scoreboard scoreboard;

    public Player(int balance){
        this.hand = new Hand();
        this.balance = balance;
        this.scoreboard = new Scoreboard(balance);
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

    public int getBalance(){
        return this.balance;
    }

    public Hand getHand(){
        return this.hand;
    }

    public Card[] hold(int[] indexes, Card[] newCards){
        return this.hand.renewHand(indexes, newCards);
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }

    public void showHand(){
        System.out.println(this.hand.toString());
    }

    public void statistics(){
        System.out.println(this.scoreboard.toString());
    }

    public void updateBalance(int newBalance){
        this.balance += newBalance;
    }

    public void updateScoreboard(HandRank rank){
        this.scoreboard.receiveRoundInfo(rank, this.balance);
    }

}