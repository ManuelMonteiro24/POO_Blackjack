package videopoker.evaluators;

import videopoker.utils.HandRank;

class Foak extends Evaluator{

    Foak(){}

    @Override
    public int[] evaluate(){
        if(handRank == HandRank.FOAK_5K || handRank == HandRank.FOAK_24 || handRank == handRank.FOAK_A) {
            if (valueStreak(0) == 3) {
                int[] ret = {0, 1, 2, 3};
                return ret;
            }

            if (valueStreak(1) == 3) {
                int[] ret = {1, 2, 3, 4};
                return ret;
            }
        }
        return null;
    }


}