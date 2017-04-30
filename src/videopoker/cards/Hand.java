package videopoker.cards;

import java.util.Arrays;
import java.util.Collections;

public class Hand{

  //cartas na mao estao sempre ordenadas??
  private Card[] hand =  new Card[5];

  // 0-> nothing 1-> joB 2 -> Two Pair 3-> ToaK 4 -> Straight 5-> Flush
  // 6->FH(pair and a ToaK) 7->FoaK(5-K) 8-> FoaK(2-4) 9->Foak(A) 10->SF 11->RF
  private int rank;

  public Hand(){
  }

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

  private void evalRank(){

    Card [] sortedHand = new Card[5];

  	for(int i = 0; i < 5; i++)
  	    sortedHand[i] = this.hand[i];

  	this.sort(sortedHand);

  	int handRank = 0;  //assumes it has nothing

  	//Pairs
  	for(int i = 0; i < 4; i++){
      if(sortedHand[i].getValue() == sortedHand[i+1].getValue() && sortedHand[i].getValue() >= 11){
          handRank++; //Jacks or Better
          i++;
      }
      if(sortedHand[i].getValue() == sortedHand[i+1].getValue() && handRank == 1)
          handRank++; // Two Pairs
    }

  	//3 of a kind or full house
  	for(int i = 0; i < 3; i++)
  	    if(sortedHand[i].getValue() == sortedHand[i+1].getValue() && sortedHand[i+1].getValue() == sortedHand[i+2].getValue()) {
  		      handRank = 3; // Three of a kind
        		if(i==0 && sortedHand[3].getValue()==sortedHand[4].getValue() || i==2 && sortedHand[0].getValue() == sortedHand[1].getValue())
  		        handRank = 6; //Full house
  	    }

  	//check for 4 of a kind
  	for(int i = 0; i < 2; i++)
  	    if(sortedHand[i].getValue() == sortedHand[i+1].getValue() && sortedHand[i+1].getValue() == sortedHand[i+2].getValue() &&
  	       sortedHand[i+2].getValue() == sortedHand[i+3].getValue()){
             if(sortedHand[i].getValue() <= 4)
              handRank = 8;
             else if(sortedHand[i].getValue() <= 13)
              handRank = 7;
             else
              handRank = 9;
           }

  	//check for straight (if we haven't already found anything)
  	if(handRank == 0)
  	    if((sortedHand[4].getValue() - sortedHand[0].getValue() == 4) ||
  	       (sortedHand[3].getValue() - sortedHand[0].getValue() == 3 && sortedHand[4].getValue() == 14 && sortedHand[0].getValue() == 2))
  		         handRank = 4;

  	//check for flush (if we haven't already found any pairs)
  	boolean flush;
  	if(handRank == 0 || handRank == 4) {
  	    flush = true;
  	    for(int i = 0; i < 4; i++)
  		    if(sortedHand[i].getSuit() != sortedHand[i+1].getSuit())
  		      flush = false;
  	      if(flush && handRank == 4)
  		      handRank = 10; //straight flush!
  	      else if(flush)
  		      handRank = 5; // Flush
  	}

  	//check for royal flush
  	if(handRank == 10 && sortedHand[4].getValue() == 14 && sortedHand[0].getValue() == 10)
  	    handRank = 11; //royal flush!

    this.setRank(rank);
    return;
  }

  public void setRank(int rank){
    this.rank = rank;
  }

  public int getRank(){
    return this.rank;
  }

  //use sort for quicker evalution, but we dont want to change order of cards to
  //player so that the cards show always, eu acho que isto se consegue fazer melhor
  private void sort(Card[] aux) {
	   Card temp;
	   int minIndex;
	   for(int i = 0; i < aux.length; i++) {
	      minIndex = i;
	      for(int j = i; j < aux.length; j++) {
		     if(aux[minIndex].compareTo(aux[j]) == 1)
		       minIndex = j;
	        }
	    //swap the elements at i and j
	       temp = aux[minIndex];
	       aux[minIndex] = aux[i];
	       aux[i] = temp;
	   }
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
