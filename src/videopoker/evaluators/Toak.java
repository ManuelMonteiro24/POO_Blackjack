package videopoker.evaluators;

import videopoker.utils.HandRank;

class Toak extends Evaluator{

    Toak(){}

    @Override
    public int[] evaluate(){
        if(handRank == HandRank.TOAK){
            //find where the Toak is
            for(int i = 0; i < 3; i++){
                if(hand[i].getValue() == hand[i+1].getValue()
                        && hand[i+1].getValue() == hand[i+2].getValue()){
                    int[] ret = {i, i+1, i+2};
                    return ret;
                }
            }
        }
        return null;
    }


}