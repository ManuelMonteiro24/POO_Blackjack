package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if exists a King and a Queen or a King or a Jack in the hand
 */
class KQKJUnsuited extends Evaluator{

  /**
  * Creates an instance of class KQKJUnsuited
  */
  KQKJUnsuited(){}

    /**
    * Method that checks if exists a King and a Queen or a King or a Jack in the hand
    */
    @Override
    public int[] evaluate(){
      if(hand[hand.length - 1].getValue() == 13 && (hand[hand.length - 2].getValue() == 12 || hand[hand.length - 2].getValue() == 11)){
        int[] ret = {hand.length - 2, hand.length - 1};
        return ret;
      }

      return null;
    }


  }
