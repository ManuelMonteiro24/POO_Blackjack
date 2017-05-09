package videopoker.utils;

import videopoker.utils.HandRank;
import videopoker.cards.Card;
import videopoker.cards.Hand;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class HandEvaluator{

    private Card[] hand;
    protected HandRank handRank;

    public HandEvaluator(){}

    public HandEvaluator(Hand playersHand){
        this.hand = Arrays.copyOf(playersHand.toCardArray(), playersHand.toCardArray().length);
        Arrays.sort(this.hand); //sort array of cards according to Card.compareTo() method from Comparable<T> interface
    }

    public void updateEvaluator(Hand playersHand){
        this.hand = Arrays.copyOf(playersHand.toCardArray(), playersHand.toCardArray().length);
        Arrays.sort(this.hand);
    }

    private boolean isFlush(){
        char suite = this.hand[0].getSuit();
        for(int i = 1; i < hand.length; i++)
            if(this.hand[i].getSuit() != suite)
                return false;
        return true;
    }

    /*This function needs the cards in @hand to be ordered*/
    private boolean isStraight() {
        int prev = this.hand[0].getValue();

        for(int i = 1; i < this.hand.length; i++){
            if(this.hand[i].getValue() != prev + 1) {
                if (!(prev == 1 && this.hand[i].getValue() == 10))
                    return false;
            }
            prev = this.hand[i].getValue();
        }

        return true;
    }

    /*This function needs the cards in @hand to be ordered*/
    private int valueStreak(int searchIndex){
        int streak = 0;
        int value = this.hand[searchIndex].getValue();

        for(int i = searchIndex + 1; i < this.hand.length; i++)
            if(this.hand[i].getValue() == value)
                streak++;
            else
                break;

        return streak;
    }

    public HandRank evaluate(){
        int valueStreak, value;
        HandRank currentRank = HandRank.NON;

        for (int i = 0; i < 5; i++) {
            value = this.hand[i].getValue();
            valueStreak = this.valueStreak(i);

            if(valueStreak == 3) {
                if (value == 1)
                    return (this.handRank =  HandRank.FOAK_A);
                if (value < 5)
                    return (this.handRank =  HandRank.FOAK_24);
                else
                    return (this.handRank =  HandRank.FOAK_5K);

            } else if(valueStreak == 2) {
                if (currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    return (this.handRank =  HandRank.FULLHOUSE);
                currentRank = HandRank.TOAK;

            } else if(valueStreak == 1) {
                if(currentRank == HandRank.TOAK)
                    return (this.handRank =  HandRank.FULLHOUSE);
                if(currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    return (this.handRank =  HandRank.TWOPAIR);

                if(value > 10 || value == 1)
                    currentRank = HandRank.JoB;
                else
                    currentRank = HandRank.PAIR;
            }
            i += valueStreak;
        }

        if(currentRank == HandRank.NON){ //if @currentRank is different from NON it is impossible for the @hand to be a STRAIGHT or a FLUSH
            if (this.isStraight()) {
                if (this.isFlush()) {
                    if (this.hand[hand.length - 1].getValue() == 13)
                        return (this.handRank =  HandRank.ROYAL_FLUSH);
                    else
                        return (this.handRank =  HandRank.STRAIGHT_FLUSH);
                } else
                    return (this.handRank =  HandRank.STRAIGHT);
            } else if (this.isFlush())
                return (this.handRank =  HandRank.FLUSH);
        }

        this.handRank =  currentRank;
        return currentRank;
    }

    //return the number of different high cards
    private int diffHighCards(){

      int ret =0;
      boolean[] highvalues = new boolean[4]; //J Q K A
      for(int i=0; i<this.hand.length;i++){
        if(this.hand[0].getValue() == 11) //J
          highvalues[0] = true;
        if(this.hand[i].getValue() == 12) // Q
          highvalues[1] = true;
        if(this.hand[i].getValue() == 13) //K
          highvalues[2] = true;
        if(this.hand[i].getValue() == 1) //A
          highvalues[3] = true;
      }
      for(int i = 0; i < 4; i++)
        if(highvalues[i])
          ret++;
      return ret;
    }

    private int getIndexCardOutOfSuit(){
      int index = -1;
      int flag = 0;
      char suit = this.hand[0].getSuit();

      for(int i = 1; i < this.hand.length; i++){
        if(this.hand[i].getSuit() != suit && flag == 0){
          index = i;
          break;
        }
        if(this.hand[i].getSuit() != suit && flag != 0)
          break;

      }
      return index;

    }

    public int[] indexOrderedToUnordered(int[] orderedIndexes, Hand unorderedHand){
        Card[] unorderedCards = unorderedHand.toCardArray();
        int[] unorderedIndexes = new int[orderedIndexes.length];
        int j = 0;

        for(int i = 0; i < unorderedCards.length; i++)
            for(int index : orderedIndexes)
                if(this.hand[index].getValue() == unorderedCards[i].getValue() && this.hand[index].getSuit() == unorderedCards[i].getSuit())
                    unorderedIndexes[j++] = i;

        return unorderedIndexes;
    }

    //do only after rank is set, and cards ordered
    //return cards to hold!, if null discard all
    public int[] getAdvice(){

      int[] ret, all = {1, 2, 3, 4, 5};

      evaluate();
      if(this.SF_FoaK_RF())
        return all; //check if can do this way??

      if((ret = this.fourToRF()) != null)
        return ret;

      if((ret = this.threeAces()) != null)
        return ret;

      if(this.S_F_FH())
        return all; //check if can do this way??

      if((ret = this.Toak()) != null)
        return ret;

      if((ret = this.fourToSF()) != null)
        return ret;

      if((ret = this.twoPair()) != null)
        return ret;

      if((ret = this.highPair()) != null)
        return ret;

      if((ret = this.fourToFlush()) != null)
        return ret;

      if((ret = this.threeToRF()) != null)
        return ret;

      if((ret = this.fourToOStraight()) != null)
        return ret;

      if((ret = this.lowPair()) != null)
        return ret;

      if((ret = this.unsuitedAKQJ()) != null)
        return ret;

      if((ret = this.threeToSFtype1()) != null)
        return ret;

      if((ret = this.fourToIStraight3HC()) != null)
        return ret;

      if((ret = this.suitedQJ()) != null)
        return ret;

      if((ret = this.threeToFlush2HC()) != null)
        return ret;

      if((ret = this.suited2HC()) != null)
        return ret;

      if((ret = this.fourToIStraight2HC()) != null)
        return ret;

      if((ret = this.threeToFlush2HCtoSFtype2()) != null)
        return ret;

      if((ret = this.fourToIStraight1HC()) != null)
        return ret;

      if((ret = this.unsuiedtKQJ()) != null)
        return ret;

      if((ret = this.suitedJT()) != null)
        return ret;

      if((ret = this.unsuitedQJ()) != null)
        return ret;

      if((ret = this.threeToFlush1HC()) != null)
        return ret;

      if((ret = this.suitedQT()) != null)
        return ret;

      if((ret = this.threeToSFtype3()) != null)
        return ret;

      if((ret = this.unsuitedKQKJ()) != null)
        return ret;

      if((ret = this.ace()) != null)
        return ret;

      if((ret = this.suitedKT()) != null)
        return ret;

      if((ret = this.JQK()) != null)
        return ret;

      if((ret = this.fourToIStraight()) != null)
        return ret;

      if((ret = this.threeToFlush()) != null)
        return ret;

      //Discard everything
      return null;
    }

    private boolean SF_FoaK_RF(){
      if(this.handRank == HandRank.FOAK_5K || this.handRank == HandRank.FOAK_24 || this.handRank == handRank.FOAK_A
      || this.handRank == HandRank.STRAIGHT_FLUSH || this.handRank == HandRank.ROYAL_FLUSH)
        return true;
      else
        return false;
    }

    //ERROR to corrigi
    //return cards to hold! if not return null
    private int[] fourToRF(){
      /*
      //case for 1 card out of suit
      int[] aux = this.hand;
      int[] aux1 = {this.hand[1],this.hand[2],this.hand[3],this.hand[4]};
      //create new vector?

      // no 10 in hand
      if(this.diffHighCards(aux) == 4){
        if(this.sameSuit(aux))
          return aux;
      }

      //10 in hand
      if(aux[1].getValue() == 10 || aux[0].getValue() == 10){
        if(this.diffHighCards(aux) == 3){
          if(this.sameSuit(aux))
            return aux;
        }
      }

      // no 10 in hand
      if(this.diffHighCards(aux1) == 4){
        if(this.sameSuit(aux1)
          return aux1;
      }

      //10 in hand
      if(aux1[1].getValue() == 10 || aux1[0].getValue() == 10){
        if(this.diffHighCards(aux1) == 3){
          if(this.sameSuit(aux1))
            return aux1;
        }
      }
*/
        return null;

    }

    //check if it is correct
    //return cards to hold! if not return null
    private int[] threeAces(){
      //check if its a toak
      if(this.handRank == HandRank.TOAK){
        //check if the toak is from aces
        if(this.hand[0].getValue() == 1 && this.hand[0].getValue() == this.hand[1].getValue() && this.hand[1].getValue() == this.hand[2].getValue()){
          int[] ret = {0, 1, 2};
          return ret;
        }
      }
      return null;
    }

    //return cards to hold! if not return null
    private boolean  S_F_FH(){
      if(this.handRank == HandRank.STRAIGHT || this.handRank == HandRank.FLUSH || this.handRank == handRank.FULLHOUSE)
        return true;
      else
        return false;
    }

    //return cards to hold! if not return null
    private int[] Toak(){
      if(this.handRank == HandRank.TOAK){
        //find where the Toak is
        for(int i = 0; i < 3; i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()
          && this.hand[i+1].getValue() == this.hand[i+2].getValue()){
            int[] ret = {i, i+1, i+2};
            return ret;
          }
        }
      }
      return null;
    }

    //return cards to hold! if not return null
    private int[] fourToSF(){
      //to do...
        return null;
    }

    //Check if it is correct
    //return cards to hold! if not return null
    private int[] twoPair(){
      if(this.handRank == HandRank.TWOPAIR){
        //find where the pairs are
        int[] ret = new int[4];
        int j = 0;
        for(int i = 0; i < 4; i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()){
            ret[j] = i;
            ret[j+1] = i+1;
            i++; j += 2;
          }
        }
        return ret;
      }
      return null;
    }

    //return cards to hold! if not return null
    private int[] highPair(){
      if(this.handRank == HandRank.JoB){
        //find where the pair is
        for(int i = 0; i < 4; i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()){
            int[] ret = {i, i+1};
            return ret;
          }
        }
      }
      return null;
    }

    //return cards to hold! if not return null
    private int[] fourToFlush(){
      //to do...
      return null;
    }

    //return cards to hold! if not return null
    private int[] threeToRF(){
      //to do...
      return null;
    }

    //return cards to hold! if not return null
    private int[] fourToOStraight(){
      //to do...
      return null;
    }

    //return cards to hold! if not return null
    private int[] lowPair(){
      if(this.handRank == HandRank.PAIR){
        //find where the pair is
        for(int i = 0; i < 4; i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()){
            int[] ret = {i, i+1};
            return ret;
          }
        }
      }
      return null;
    }

    //return cards to hold! if not return null
    private int[] unsuitedAKQJ(){
      if(this.diffHighCards() == 4){
        int[] ret = {0, 2, 3, 4};
        return ret;
      }
      return null;
    }

    //return cards to hold! if not return null
    private int[] threeToSFtype1(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] fourToIStraight3HC(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] suitedQJ(){
      //no pairs in this stage
      for(int i=0;i<4;i++){
        if(this.hand[i].getValue() == 11 && this.hand[i+1].getValue() == 12
        && this.hand[i].getSuit() == this.hand[i+1].getSuit()){
          int[] ret = { i, i+1 };
          return ret;
        }
      }
      return null;
    }

    //return cards to hold! if not return null
    private int[] threeToFlush2HC(){
      if(this.diffHighCards() == 2){
          for(int i = 0; i < 5; i++){
              //to do
          }
      }
      return null;
    }

    //return cards to hold! if not return null
    private int[] suited2HC(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] fourToIStraight2HC(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] threeToFlush2HCtoSFtype2(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] fourToIStraight1HC(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] unsuiedtKQJ(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] suitedJT(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] unsuitedQJ(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] threeToFlush1HC(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] suitedQT(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] threeToSFtype3(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] unsuitedKQKJ(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] ace(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] suitedKT(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] JQK(){
      //to do...
        return null;
    }
    //return cards to hold! if not return null
    private int[] fourToIStraight(){
      //to do...
        return null;
    }
    //return cards to hold! if not return null
    private int[] threeToFlush(){
      //to do...
        return null;
    }
}
