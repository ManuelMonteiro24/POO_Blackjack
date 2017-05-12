package videopoker.evaluators;

class ThreeToFlushNoHC extends Evaluator{

    ThreeToFlushNoHC(){}

    @Override
    public int[] evaluate(){
        int[] indexes;
        if((indexes = threeToFlush()) != null)
            return indexes;

        return null;
    }


}
