package videopoker.evaluators;

import videopoker.cards.Card;
import videopoker.cards.Hand;
import videopoker.utils.HandRank;

import java.util.Arrays;

/**
 * Abstract class for hand evaluation. Contains two static attributes that
 * will be shared among all instances of this class.
 *
 * Classes that extend this class must all implement the method evaluate().
 * The implementation of this method is specific to the functionality of the subclass
 * extending this one.
 */
public abstract class Evaluator{

    protected static Card[] hand; /*This array will be common to all evaluators that extend this class*/
    protected static HandRank handRank;

    /**
     * Updates this class static variable @hand with the hand received from @playersHand.
     * @param playersHand new hand value to update to
     * @return instance of class Evaluator created
     */
    /*This method should be called once before the evaluation of the hand to set the hand for the evaluators*/
    public static void updateEvaluator(Hand playersHand){
        hand = Arrays.copyOf(playersHand.toCardArray(), playersHand.toCardArray().length);
        Arrays.sort(hand);
    }

    /**
     * Abstract method meant for evaluation of the @hand according to implementation specific conditions.
     * @return indexes to hold on the ordered hand
     */
    public abstract int[] evaluate();

    /**
     * Check if hand has three of the five cards necessary to complete a flush
     * @return indexes of the three cards that belong to a flush, null if no three cards belong to a flush
     */
    protected int[] threeToFlush(){
        int j1 = 1, j2 = 0, j3 = 0;
        int[] indexes2 = new int[3], indexes3 = new int[3], indexes1 = {0, -1, -1};
        char suit2 = '\0', suit3 = '\0', suit1 = hand[0].getSuit();

        //MANEL REMOVI UM IF AQUI QUE ACHO QUE NAO ERA NECESSARIO, mas fazer check

        for(int i = 1; i < hand.length; i++) {
            if(hand[i].getSuit() == suit1) {
                indexes1[j1++] = i;
            } else if(hand[i].getSuit() == suit2) {
                indexes2[j2++] = i;
            } else if(hand[i].getSuit() == suit3) {
                indexes3[j3++] = i;
            } else if(i < 2) {
                suit2 = hand[i].getSuit();
                indexes2[j2++] = i;
            } else if(i < 3) {
                suit3 = hand[i].getSuit();
                indexes3[j3++] = i;
            }
        }

        System.out.println("3toFlush ");
        if(j1 == 3)
            return indexes1;
        if(j2 == 3)
            return indexes2;
        if(j3 == 3)
            return indexes3;

        System.out.println("not 3toFlush");
        return null;
    }

    /**
     * Checks if there are any four cards from @hand that can be part of an inside straight.
     * @return indexes of the four cards the can be part of an inside straight
     */
    protected int[] fourToIStraight() {

        //A234 case
        if ((this.hand[0].getValue() == 1) && (this.hand[3].getValue() == 4)) {
            int[] ret = {0, 1, 2, 3};
            return ret;
        }

        //AJQK case
        if ((this.hand[0].getValue() == 1) && (this.hand[2].getValue() == 11)) {
            int[] ret = {0, 2, 3, 4};
            return ret;
        }

        //ATJQ, ATJK, ATQK cases
        if ((this.hand[0].getValue() == 1) && (this.hand[2].getValue() == 10)) {
            int[] ret = {0, 2, 3, 4};
            return ret;
        }

        //other cases (start with 1...4 to check higher 4TIS first)
        if ((this.hand[4].getValue() - this.hand[1].getValue()) == 4) {
            int[] ret = {1, 2, 3, 4};
            return ret;
        }

        if ((this.hand[3].getValue() - this.hand[0].getValue()) == 4) {
            int[] ret = {0, 1, 2, 3};
            return ret;
        }

        return null;
    }

    /**
     * Checks if cards referenced by @indexes can belong to a straight sequence.
     * @param indexes indexes of the cards from @hand that will be tested
     * @return true if condition holds, false otherwise
     */
    protected boolean isThreeToStraight(int[] indexes){
        int straight1 = 0, straight2 = 0, straight3 = 0, base = hand[indexes[0]].getValue();
        for(int i = 1; i < indexes.length; i++){
            if(hand[indexes[i]].getValue() >= (base - 2) && hand[indexes[i]].getValue() <= (base + 2))
                straight1++;
            if(hand[indexes[i]].getValue() >= (base - 1) && hand[indexes[i]].getValue() <= (base + 3))
                straight2++;
            if(hand[indexes[i]].getValue() > base && hand[indexes[i]].getValue() <= (base + 4))
                straight3++;
        }

        if(straight1 != 2 && straight2 != 2 && straight3 != 2)
            return false;
        return true;
    }

    /**
     * Counts how many times a given card value repeats itself in the @hand.
     * @param searchIndex  index of the card to compare for streak
     * @return  the amount of times the value of the card given by @searchIndex repeates itself
     */
    protected int valueStreak(int searchIndex){
        int streak = 0;
        int value = this.hand[searchIndex].getValue();

        for(int i = searchIndex + 1; i < this.hand.length; i++)
            if(this.hand[i].getValue() == value)
                streak++;
            else
                break;

        return streak;
    }

    /**
     * Counts the number of different high cards in @hand
     * @return number of different high cards
     */
    protected int diffHighCards(){
        int ret =0;
        boolean[] highvalues = new boolean[4]; //J Q K A

        for(int i = 0; i < hand.length; i++){
            if(hand[i].getValue() == 1) //A
                highvalues[0] = true;
            if(hand[i].getValue() == 11) //J
                highvalues[1] = true;
            if(hand[i].getValue() == 12) //Q
                highvalues[2] = true;
            if(hand[i].getValue() == 13) //K
                highvalues[3] = true;
        }
        for(int i = 0; i < 4; i++)
            if(highvalues[i])
                ret++;

        return ret;
    }

}
