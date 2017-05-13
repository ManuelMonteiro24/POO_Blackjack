package videopoker.cards;

import videopoker.utils.HandRank;
import videopoker.utils.HandEvaluator;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class represents a hand of 5 cards. In videopoker,
 * the player always holds 5 cards.
 */
public class Hand{

    private Card[] hand =  new Card[5];/* 0-> nothing 1-> joB 2 -> Two Pair 3-> ToaK 4 -> Straight 5-> Flush 6->FH(pair and a ToaK) 7->FoaK(5-K) 8-> FoaK(2-4) 9->Foak(A) 10->SF 11->RF*/

    /**
     * Empty Hand object constructor.
     */
    public Hand(){}

    /**
     * Returns an instance of this class with the corresponding @hand
     * instance variable initialized with the argument @hand Card array.
     * @param hand card to array to attribute to instance variable @hand.
     */
    public Hand(Card[] hand){
        this.hand = hand;
    }

    /**
     * Updates the current @hand Card array instance variable by replacing all
     * cards which indexes are not present at @indexes, by the cards held at @newcards.
     * @param indexes array containig the indexes of the cards not to replace
     * @param newcards Card array with cards to replace
     * @return
     */
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

    /**
     * Returns the card array at @hand.
     * @return Card array @hand
     */
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

    //not using
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

    //not using
    @Override
    public int hashCode(){
    final int prime = 31;
    int result = 1;

    for(int i =0; i< 5; i++)
    result += prime * result + hand[i].hashCode();

    return result;
    }


}
