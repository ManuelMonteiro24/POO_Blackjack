package videopoker.evaluators;

class AKQJUnsuited extends Evaluator{

    AKQJUnsuited(){}

    @Override
    public int[] evaluate(){
        if(diffHighCards() == 4){
            int[] ret = {0, 2, 3, 4};
            return ret;
        }
        return null;
    }


}