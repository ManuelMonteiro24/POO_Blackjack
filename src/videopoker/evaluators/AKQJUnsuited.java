package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if there in an A,K,Q and J in the hand
 */

class AKQJUnsuited extends Evaluator{

  /**
  * Creates an instance of class AKQJUnsuited
  */
  AKQJUnsuited(){}

    /**
    * Method that checks if there in an A,K,Q and J in the hand
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
