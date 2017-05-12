package videopoker.evaluators;

class FourToInStraightNoHC extends Evaluator{

    FourToInStraightNoHC(){}

    @Override
    public int[] evaluate(){
        int[] indexes;
        if((indexes = fourToIStraight()) != null)
            return indexes;
        return null;
    }


}