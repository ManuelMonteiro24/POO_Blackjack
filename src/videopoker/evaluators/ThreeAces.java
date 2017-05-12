package videopoker.evaluators;

import videopoker.utils.HandRank;

class ThreeAces extends Evaluator{

    ThreeAces(){}

    @Override
    public int[] evaluate(){
        //check if its a toak
        if(handRank == HandRank.TOAK || handRank == HandRank.FULLHOUSE){
            //check if the toak is from aces
            if(hand[0].getValue() == 1 && hand[0].getValue() == hand[1].getValue() && hand[1].getValue() == hand[2].getValue()){
                int[] ret = {0, 1, 2};
                System.out.println("3aces");
                return ret;
            }
        }
        return null;
    }


}