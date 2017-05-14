package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if exists a Queen and a Jack in the hand
 */
class QJUnsuited extends Evaluator{

  /**
  * Creates an instance of class QJUnsuited
  */
  QJUnsuited(){}

    /**
    * Method that checks if exists a Queen and a Jack in the hand
    */
    @Override
    public int[] evaluate(){
      for(int i = 0; i < hand.length - 1; i++){
        if(hand[i].getValue() == 11 && hand[i+1].getValue() == 12){
          int[] ret = { i, i+1 };
          return ret;
        }
      }
      return null;
    }


  }
