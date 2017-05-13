package videopoker.evaluators;

class KQKJUnsuited extends Evaluator{

    KQKJUnsuited(){}

    @Override
    public int[] evaluate(){
        if(hand[hand.length - 1].getValue() == 13 && (hand[hand.length - 2].getValue() == 12 || hand[hand.length - 2].getValue() == 11)){
            int[] ret = {hand.length - 2, hand.length - 1};
            return ret;
        }

        return null;
    }


}