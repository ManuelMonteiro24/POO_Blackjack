package videopoker.evaluators;

import videopoker.utils.HandRank;

class HighPair extends Evaluator{

    HighPair(){}

    @Override
    public int[] evaluate(){
        if(handRank == HandRank.JoB){
            //find where the pair is
            for(int i = 0; i < 4; i++){
                if(hand[i].getValue() == hand[i+1].getValue()){
                    int[] ret = {i, i+1};
                    return ret;
                }
            }
        }
        return null;
    }


}