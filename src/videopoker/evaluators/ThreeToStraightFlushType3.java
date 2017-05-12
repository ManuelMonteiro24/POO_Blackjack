package videopoker.evaluators;

class ThreeToStraightFlushType3 extends Evaluator{

    ThreeToStraightFlushType3(){}

    @Override
    public int[] evaluate(){
        int[] indexes;
        if((indexes = threeToFlush()) == null)
            return null;
        if(isThreeToStraight(indexes))
            return indexes;
        else
            return null;
    }


}