package videopoker.evaluators;

import videopoker.utils.HandRank;

class StraightFlushRoyalFlush extends Evaluator{

    StraightFlushRoyalFlush(){}

    @Override
    public int[] evaluate(){
        if(handRank == HandRank.STRAIGHT_FLUSH || handRank == HandRank.ROYAL_FLUSH) {
            int[] indexes = {0, 1, 2, 3, 4};
            return indexes;
        } else
            return null;
    }


}