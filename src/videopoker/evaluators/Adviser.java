package videopoker.evaluators;

import java.util.ArrayList;

/**
 * Class meant for advising the player on which is play
 * given a certain hand.
 *
 * This class has an ArrayList @evaluators that contain all the
 * different Evaluator subclasses that will be used for evaluation
 * of the players hand.
 *
 */
public class Adviser{

    private ArrayList<Evaluator> evaluators;

    /**
     * Adviser constructor initializes the @evaluators list with all the different evaluators.
     * The order in which they're added to list is important for it will be same order that the
     * the static variable @hand shared by all Evaluator instances will be evaluated.
     * @return instance of class Adviser created
     */
    public Adviser(){
        this.evaluators = new ArrayList<Evaluator>();

        this.evaluators.add(new StraightFlushRoyalFlush());
        this.evaluators.add(new Foak());
        this.evaluators.add(new FourToRoyalFlush());
        this.evaluators.add(new ThreeAces());
        this.evaluators.add(new StraightFlushFullHouse());
        this.evaluators.add(new Toak());
        this.evaluators.add(new FourToStraightFlush());
        this.evaluators.add(new TwoPair());
        this.evaluators.add(new HighPair());
        this.evaluators.add(new FourToFlush());
        this.evaluators.add(new ThreeToRoyalFlush());
        this.evaluators.add(new FourToOutStraight());
        this.evaluators.add(new LowPair());
        this.evaluators.add(new AKQJUnsuited());
        this.evaluators.add(new ThreeToStraightFlushType1());
        this.evaluators.add(new FourToInStraight3HC());
        this.evaluators.add(new QJSuited());
        this.evaluators.add(new ThreeToFlush2HC());
        this.evaluators.add(new TwoSuitedHC());
        this.evaluators.add(new FourToInStraight2HC());
        this.evaluators.add(new ThreeToStraightFlushType2());
        this.evaluators.add(new FourToInStraight1HC());
        this.evaluators.add(new KQJUnsuited());
        this.evaluators.add(new JTSuited());
        this.evaluators.add(new QJUnsuited());
        this.evaluators.add(new ThreeToFlush1HC());
        this.evaluators.add(new QTSuited());
        this.evaluators.add(new ThreeToStraightFlushType3());
        this.evaluators.add(new KQKJUnsuited());
        this.evaluators.add(new Ace());
        this.evaluators.add(new KTSuited());
        this.evaluators.add(new JackQueenKing());
        this.evaluators.add(new FourToInStraightNoHC());
        this.evaluators.add(new ThreeToFlushNoHC());

    }

    /**
     * Verifies current value @hand and checks for the best cards to hold according to a perfect strategy
     * for maximazing the earnings in videopoker double bonus 10/7.
     * Runs through all the evaluators from @evaluators and returns the first non-null value it receives
     * from them.
     *
     * @return indexes advised to hold
     */
    public int[] getAdvice(){
        int[] indexes;
        for(Evaluator evaluator : this.evaluators) {
            if((indexes = evaluator.evaluate()) != null)
                return indexes;
        }

        return null;
    }
}
