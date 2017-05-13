package videopoker.evaluators;

class AKQJUnsuited extends Evaluator{

    /**
    * Creates an instance of class AKQJUnsuited
    * @return instance of class AKQJUnsuited created
    */
    AKQJUnsuited(){}

      /**
       * Checks if there in an A,K,Q and J in the hand
       */
    @Override
    public int[] evaluate(){
        if(diffHighCards() == 4){
            int[] ret = {0, 2, 3, 4};
            return ret;
        }
        return null;
    }


}
