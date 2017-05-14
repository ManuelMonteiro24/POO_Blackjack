package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if there is an Ace in the Hand
 */

class Ace extends Evaluator{

  /**
  * Creates an instance of class Ace
  */
  Ace(){}


    /**
    * Method that checks if there is an Ace in the Hand
    */
    @Override
    public int[] evaluate(){
      int[] ret = new int[1];
      for(int i = 0; i < hand.length; i++)
      if(hand[i].getValue() == 1){
        ret[0] = i;
        return ret;
      }


      return null;
    }


  }
