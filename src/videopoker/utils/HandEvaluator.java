package videopoker.utils;

import videopoker.utils.HandRank;
import videopoker.cards.Card;
import videopoker.cards.Hand;

import java.util.Arrays;

public class HandEvaluator{

    private Card[] hand;

    public HandEvaluator(){}

    public HandEvaluator(Hand playersHand){
        this.hand = playersHand.toCardArray();
        Arrays.sort(this.hand); //sort array of cards according to Card.compareTo() method from Comparable<T> interface
    }

    public void update(Hand playersHand){
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
                if (prev != 1 && this.hand[i].getValue() != 10)
                    return false;
            }
            prev = this.hand[i].getValue();
        }

        /*for (int i = 1; i < this.hand.length; i++)
            if (this.hand[i].getValue() != comp) { //Ace will be out of order because it can assume HIGH or LOW values
                if (comp == 2 && this.hand[i].getValue() != 10)
                    return false;
                else
                    comp = 10;
            }
*/

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

        for(int i = 0; i < 5; i++) {
            value = this.hand[i].getValue();
            valueStreak = this.valueStreak(i);

            if(valueStreak == 3) {
                if (value == 1)
                    return HandRank.FOAK_A;
                if (value < 5)
                    return HandRank.FOAK_24;
                else
                    return HandRank.FOAK_5K;

            } else if(valueStreak == 2) {
                if (currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    return HandRank.FULLHOUSE;
                currentRank = HandRank.TOAK;

            } else if(valueStreak == 1) {

                if(currentRank == HandRank.TOAK)
                    return HandRank.FULLHOUSE;
                if(currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    return HandRank.TWOPAIR;

                if(value > 10 || value == 1)
                    currentRank = HandRank.JoB;
                else
                    currentRank = HandRank.PAIR;
            }
            i += valueStreak;
        }

        if(currentRank == HandRank.NON) { //if @currentRank is different from NON it is impossible for the @hand to be a STRAIGHT or a FLUSH
            if(this.isStraight()) {
                if (this.isFlush()) {
                    if (this.hand[hand.length  - 1].getValue() == 13)
                        return HandRank.ROYAL_FLUSH;
                    else
                        return HandRank.STRAIGHT_FLUSH;
                } else
                    return HandRank.STRAIGHT;
            } else if(this.isFlush())
                return HandRank.FLUSH;
        }

        return currentRank;
    }
}