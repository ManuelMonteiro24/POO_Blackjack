package videopoker.utils;

import videopoker.utils.HandRank;
import videopoker.cards.Card;
import videopoker.cards.Hand;

import java.util.Arrays;

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

    //do only after rank is set
    //return cards to hold!, if null discard all
    public Card[] getAdivce(){

      Card[] ret;

      if(this.SF_FoaK_RF())
        return hand;

      if(ret = this.fourtoRF() != NULL)
        return ret;

      if(ret = this.threeAces() != NULL)
        return ret;

      if(ret = this.SF_FH() != NULL)
        return ret;

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

    //return cards to hold! if not return null
    private Card[] fourToRF(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] threeAces(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] SF_FH(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] Toak(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] fourToSF(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] twoPair(){
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] highPair(){
      //to do...
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
      //to do...
    }

    //return cards to hold! if not return null
    private Card[] unsuitedAKQJ(){
      //to do...
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
