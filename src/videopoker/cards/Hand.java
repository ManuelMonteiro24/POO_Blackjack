package videopoker.cards;

import videopoker.utils.HandRank;

import java.util.Arrays;
import java.util.Collections;

public class Hand{

    private Card[] hand =  new Card[5];/* 0-> nothing 1-> joB 2 -> Two Pair 3-> ToaK 4 -> Straight 5-> Flush 6->FH(pair and a ToaK) 7->FoaK(5-K) 8-> FoaK(2-4) 9->Foak(A) 10->SF 11->RF*/
    private HandRank rank;

    public Hand(){}

    public Hand(Card[] hand){
    this.hand = hand;
    }

    public Card[] renewHand(int[] indexes, Card[] newcards){
    int aux = 0;
    boolean found = false;
    Card[] ret = new Card[newcards.length];
    Arrays.sort(indexes);

    for(int i = 0; i < hand.length; i++){
      for(int j = 0; j < indexes.length; j++)
        if(indexes[j] == (i + 1)){ //indexes start at 1
          found = true;
          break;
        }

      if(!found){
          ret[aux] = this.hand[i];
          this.hand[i] = newcards[aux++];
      } else
          found = false;
    }
    return ret;
    }

    public void setRank(HandRank rank){
    this.rank = rank;
    }

    public HandRank getRank(){
    return this.rank;
    }

    public Card[] toCardArray(){
        return this.hand;
    }

    @Override
    public String toString(){
    String ret = "";
    for(Card card : this.hand)
    ret = ret.concat(card.toString() + " ");

    return ret;
    }

    //vale a pena fazer???
    @Override
    public boolean equals(Object obj){
    if(this == obj)
    return true;

    if(obj == null)
    return false;

    if(!(obj instanceof Hand))
    return false;

    Hand aux = (Hand) obj;
    for(int i = 0; i < 5; i++)
    if(!(this.hand[i].equals(aux.hand[i])))
    return false;

    return true;
    }

    //vale a pena fazer??? passar para um primo mais alto???
    @Override
    public int hashCode(){
    final int prime = 31;
    int result = 1;

    for(int i =0; i< 5; i++)
    result += prime * result + hand[i].hashCode();

    return result;
    }


}
