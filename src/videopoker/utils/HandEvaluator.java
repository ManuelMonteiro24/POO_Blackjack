package videopoker.utils;

import videopoker.utils.HandRank;
import videopoker.cards.Card;
import videopoker.cards.Hand;

import java.util.Arrays;
import java.util.ArrayList;

public class HandEvaluator{

    private Card[] hand;
    protected HandRank handRank;

    public HandEvaluator(){}

    public HandEvaluator(Hand playersHand){
        this.hand = playersHand.toCardArray();
        Arrays.sort(this.hand); //sort array of cards according to Card.compareTo() method from Comparable<T> interface
    }

    public void updateEvaluator(Hand playersHand){
        this.hand = playersHand.toCardArray();
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

            if (valueStreak == 3) {
                if (value == 1)
                    currentRank =  HandRank.FOAK_A;
                if (value < 5)
                    currentRank =  HandRank.FOAK_24;
                else
                    currentRank =  HandRank.FOAK_5K;

            } else if (valueStreak == 2) {
                if (currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    currentRank =  HandRank.FULLHOUSE;
                currentRank = HandRank.TOAK;

            } else if (valueStreak == 1) {

                if (currentRank == HandRank.TOAK)
                    currentRank =  HandRank.FULLHOUSE;
                if (currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    currentRank =  HandRank.TWOPAIR;

                if (value > 10 || value == 1)
                    currentRank = HandRank.JoB;
                else
                    currentRank = HandRank.PAIR;
            }
            i += valueStreak;
        }

        if (currentRank == HandRank.NON) { //if @currentRank is different from NON it is impossible for the @hand to be a STRAIGHT or a FLUSH
            if (this.isStraight()) {
                if (this.isFlush()) {
                    if (this.hand[hand.length - 1].getValue() == 13)
                        currentRank =  HandRank.ROYAL_FLUSH;
                    else
                        currentRank =  HandRank.STRAIGHT_FLUSH;
                } else
                    currentRank =  HandRank.STRAIGHT;
            } else if (this.isFlush())
                currentRank =  HandRank.FLUSH;
        }


        this.handRank =  currentRank;
        return currentRank;
    }

    //check if all cards are from same suit
    private boolean sameSuit(){

      char suit = this.hand[0].getSuit();

      for(int i=1; i< this.hand.length;++){
        if(this.hand[i].getSuit() != suit)
          return false;
      }
      return true;
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
      for(int i=0;i<4;i++)
        if(highvalues[i])
          ret++;
      return ret;
    }

    private int getIndexCardOutOfSuit(){
      int index;
      int flag = 0;
      Char suit = cards[0].getSuit();

      for(int i=0;i<this.hand.length;i++){
        if(this.hand[i].getSuit() != suit && flag == 0){
          flag=1;
          index = i;
        }
        if(this.hand[i].getSuit() != suit && flag != 0){
          return NULL;
        }
      }
      return index;

    }
    //do only after rank is set, and cards ordered
    //return cards to hold!, if null discard all
    public Card[] getAdivce(){

      Card[] ret;

      if(this.SF_FoaK_RF())
        return this.hand; //check if can do this way??

      if(ret = this.fourtoRF() != NULL)
        return ret;

      if(ret = this.threeAces() != NULL)
        return ret;

      if(this.S_F_FH())
        return this.hand; //check if can do this way??

      if(ret = this.Toak() != NULL)
        return ret;

      if(ret = this.fourToSF() != NULL)
        return ret;

      if(ret = this.twoPair() != NULL)
        return ret;

      if(ret = this.highPair() != NULL)
        return ret;

      if(ret = this.fourToFlush() != NULL)
        return ret;

      if(ret = this.threeToRF() != NULL)
        return ret;

      if(ret = this.fourToOStraight() != NULL)
        return ret;

      if(ret = this.lowPair() != NULL)
        return ret;

      if(ret = this.unsuitedAKQJ() != NULL)
        return ret;

      if(ret = this.threeToSFtype1() != NULL)
        return ret;

      if(ret = this.fourToIStraight3HC() != NULL)
        return ret;

      if(ret = this.suitedQJ() != NULL)
        return ret;

      if(ret = this.threeToFlush2HC() != NULL)
        return ret;

      if(ret = this.suited2HC() != NULL)
        return ret;

      if(ret = this.fourToIStraight2HC() != NULL)
        return ret;

      if(ret = this.threeToFlush2HCtoSFtype2() != NULL)
        return ret;

      if(ret = this.fourToIStraight1HC() != NULL)
        return ret;

      if(ret = this.unsuiedtKQJ() != NULL)
        return ret;

      if(ret = this.suitedJT() != NULL)
        return ret;

      if(ret = this.unsuitedQJ() != NULL)
        return ret;

      if(ret = this.threeToFlush1HC() != NULL)
        return ret;

      if(ret = this.suitedQT() != NULL)
        return ret;

      if(ret = this.threeToSFtype3() != NULL)
        return ret;

      if(ret = this.unsuitedKQKJ() != NULL)
        return ret;

      if(ret = this.ace() != NULL)
        return ret;

      if(ret = this.suitedKT() != NULL)
        return ret;

      if(ret = this.JQK() != NULL)
        return ret;

      if(ret = this.fourToIStraight() != NULL)
        return ret;

      if(ret = this.threeToFlush() != NULL)
        return ret;

      //Discard everything
      return NULL;
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
    private Card[] fourToRF(){
      /*
      //case for 1 card out of suit
      Card[] aux = this.hand;
      Card[] aux1 = {this.hand[1],this.hand[2],this.hand[3],this.hand[4]};
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

        return NULL;
        */
    }

    //check if it is correct
    //return cards to hold! if not return null
    private Card[] threeAces(){
      //check if is as toak
      if(this.handRank == HandRank.Toak){
        //check if the toak is from aces
        if(this.hand[0].getValue() == 1 && this.hand[0].getValue() == this.hand[1].getValue()
        && this.hand[1].getValue() == this.hand[2].getValue()){
          Card[] ret = {this.hand[0],this.hand[1],this.hand[2]};
          return ret;
        }
      }
      return NULL;
    }

    //return cards to hold! if not return null
    private boolean  S_F_FH(){
      if(this.handRank == HandRank.STRAIGHT || this.handRank == HandRank.FLUSH || this.handRank == handRank.FULLHOUSE)
        return true;
      else
        return false;
    }

    //return cards to hold! if not return null
    private Card[] Toak(){
      if(this.handRank == HandRank.Toak){
        //find where the Toak is
        for(int i=0; i<3;i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()
          && this.hand[i+1].getValue() == this.hand[i+2].getValue()){
            Card[] ret = {this.hand[i],this.hand[i+1],this.hand[i+2]};
            return ret;
          }
        }
      }
      return NULL;
    }

    //return cards to hold! if not return null
    private Card[] fourToSF(){
      //to do...
    }

    //Check if it is correct
    //return cards to hold! if not return null
    private Card[] twoPair(){
      if(this.handRank == HandRank.TWOPAIR){
        //find where the pairs are
        List<Card> list = new ArrayList<Card>();
        for(int i=0; i<4;i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()){
            list.add(this.hand[i]);
            list.add(this.hand[i+1]);
            i++;
          }
        }
        return list.toArray(new Card[list.size()]);
      }else{
        return NULL;
      }
    }

    //return cards to hold! if not return null
    private Card[] highPair(){
      if(this.handRank == HandRank.JoB){
        //find where the pair is
        for(int i=0; i<4;i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()){
            Card[] ret = {this.hand[i],this.hand[i+1]};
            return ret;
          }
        }
      }else
        return NULL;
    }

    //return cards to hold! if not return null
    private Card[] fourToFlush(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] threeToRF(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] fourToOStraight(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] lowPair(){
      if(this.handRank == HandRank.PAIR){
        //find where the pair is
        for(int i=0; i<4;i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()){
            Card[] ret = {this.hand[i],this.hand[i+1]};
            return ret;
          }
        }
      }else
        return NULL;
    }

    //return cards to hold! if not return null
    private Card[] unsuitedAKQJ(){
      if(this.diffHighCards() == 4){
        Card[] ret = {this.hand[0],this.hand[2],this.hand[3],this.hand[4]};
        return ret;
      }else
        return NULL;
    }

    //return cards to hold! if not return null
    private Card[] threeToSFtype1(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] fourToIStraight3HC(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] suitedQJ(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] threeToFlush2HC(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] suited2HC(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] fourToIStraight2HC(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] threeToFlush2HCtoSFtype2(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] fourToIStraight1HC(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] unsuiedtKQJ(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] suitedJT(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] unsuitedQJ(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] threeToFlush1HC(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] suitedQT(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] threeToSFtype3(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] unsuitedKQKJ(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] ace(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] suitedKT(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] JQK(){
      //to do...
    }
    //return cards to hold! if not return null
    private Card[] fourToIStraight(){
      //to do...
    }
    //return cards to hold! if not return null
    private Card[] threeToFlush(){
      //to do...
    }
}
