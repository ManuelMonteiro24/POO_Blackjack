package videopoker.evaluators;

import videopoker.utils.HandRank;

class StraightFlushFullHouse extends Evaluator{

    StraightFlushFullHouse(){}

    @Override
    public int[] evaluate(){
        if(handRank == HandRank.STRAIGHT || handRank == HandRank.FLUSH || handRank == HandRank.FULLHOUSE){
            System.out.println("SFFH");
            int[] indexes = { 0, 1, 2, 3, 4};
            return indexes;
        } else
            return null;
    }


}