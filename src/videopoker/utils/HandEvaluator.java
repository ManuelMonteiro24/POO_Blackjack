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

    public HandRank evaluate() {
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
}
