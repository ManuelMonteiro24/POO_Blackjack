package videopoker.evaluators;

import videopoker.utils.HandRank;

class LowPair extends Evaluator{

    LowPair(){}

    @Override
    public int[] evaluate(){
        if(handRank == HandRank.PAIR){
            //find where the pair is
            for(int i = 0; i < hand.length - 1; i++){
                if(hand[i].getValue() == hand[i+1].getValue()){
                    int[] ret = {i, i+1};
                    return ret;
                }
            }
        }
        return null;
    }


}