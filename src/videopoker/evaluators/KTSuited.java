package videopoker.evaluators;

class KTSuited extends Evaluator{

    KTSuited(){}

    @Override
    public int[] evaluate(){
        if(hand[hand.length - 1].getValue() == 13)
            for(int i = 0; i < hand.length - 1; i++)
                if(hand[i].getValue() == 10 && hand[i].getSuit() == hand[hand.length - 1].getSuit()) {
                    int[] ret = { i, hand.length - 1 };
                    return ret;
                }

        return null;
    }


}