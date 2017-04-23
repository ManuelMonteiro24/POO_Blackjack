package src;

import java.util.Arrays;


public class Hand{

  //cartas na mao estao sempre ordenadas??
  private Card[] hand =  new Card[5];

  Hand(){
  }

  Hand(Card[] hand){
    this.hand = hand;
  }

  Card[] renewHand(int[] indexes, Card[] newcards){
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

  @Override
  public String toString(){
    String ret = "";
    for(Card card : this.hand)
      ret = ret.concat(card.toString() + " ");

    return ret;
  }

  @Override
  public boolean equals(Object obj){
    if (this==obj)
      return true;
    if (obj==null)
      return false;
    if(!(obj instanceof Hand))
      return false;
    Hand aux = (Hand) obj;
    for(int i=0;i<5;i++)
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
