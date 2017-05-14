package videopoker.utils;

import videopoker.utils.HandRank;
import videopoker.cards.Card;
import videopoker.cards.Hand;
import videopoker.evaluators.Evaluator;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which contains methods and tools for testing and
 * finding a player's hand rank.
 *
 * This classe can be used as an instanciated object or as super
 * class for other classes that could use this class's functionalities.
 */
public class HandEvaluator extends Evaluator {


    public HandEvaluator() {}

    /**
     * Checks current value @hand for any of the plays listed for videopoker double bonus 10/7.
     *
     * @return HandRank value of @hand
     */
    public HandRank getHandRank() {
        int valueStreak, value;
        HandRank currentRank = HandRank.NON;

        for (int i = 0; i < 5; i++) {
            value = hand[i].getValue();
            valueStreak = this.valueStreak(i);

            if (valueStreak == 3) {
                if (value == 1)
                    return (handRank = HandRank.FOAK_A);
                if (value < 5)
                    return (handRank = HandRank.FOAK_24);
                else
                    return (handRank = HandRank.FOAK_5K);

            } else if (valueStreak == 2) {
                if (currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    return (handRank = HandRank.FULLHOUSE);
                currentRank = HandRank.TOAK;

            } else if (valueStreak == 1) {
                if (currentRank == HandRank.TOAK)
                    return (handRank = HandRank.FULLHOUSE);
                if (currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    return (handRank = HandRank.TWOPAIR);

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
                    if (hand[hand.length - 1].getValue() == 13 && hand[0].getValue() == 1)
                        return (handRank = HandRank.ROYAL_FLUSH);
                    else
                        return (handRank = HandRank.STRAIGHT_FLUSH);
                } else
                    return (handRank = HandRank.STRAIGHT);
            } else if (this.isFlush())
                return (handRank = HandRank.FLUSH);
        }

        handRank = currentRank;
        return currentRank;
    }

    /**
     * Checks if @hand is a flush.
     *
     * @return true if it's a flush, false otherwise
     */
    private boolean isFlush() {
        char suite = hand[0].getSuit();
        for (int i = 1; i < hand.length; i++)
            if (hand[i].getSuit() != suite)
                return false;
        return true;
    }

    /**
     * Checks if @hand is a straight.
     *
     * @return true if it's a straight, false otherwise
     */
    private boolean isStraight() {
        int prev = hand[0].getValue();

        for (int i = 1; i < hand.length; i++) {
            if (hand[i].getValue() != prev + 1) {
                if (!(prev == 1 && hand[i].getValue() == 10))
                    return false;
            }
            prev = hand[i].getValue();
        }

        return true;
    }

    /**
     * Used to translate transform the indexes referencing cards from an ordered array
     * into indexes that reference those same cards in the player's original unordered array.
     *
     * @param orderedIndexes indexes referencing the cards in an ordered array.
     * @param unorderedHand the player's original ordered cards.
     * @return the indexes of the players unordered Hand cards to hold
     */
    public int[] indexOrderedToUnordered(int[] orderedIndexes, Hand unorderedHand) {
        Card[] unorderedCards = unorderedHand.toCardArray();
        int[] unorderedIndexes = new int[orderedIndexes.length];
        int j = 0;

        for (int i = 0; i < unorderedCards.length; i++)
            for (int index : orderedIndexes)
                if (hand[index].getValue() == unorderedCards[i].getValue() && hand[index].getSuit() == unorderedCards[i].getSuit())
                    unorderedIndexes[j++] = i;

        return unorderedIndexes;
    }

    @Override
    public int[] evaluate() {
        return null;
    }

}
