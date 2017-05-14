package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if exists a Queen and a Jack from the same suit in the hand
 */
class QJSuited extends Evaluator{

  /**
  * Creates an instance of class QJSuited
  */
  QJSuited(){}

    /**
    * Method that checks if exists a Queen and a Jack from the same suit in the hand
    */
    @Override
    public int[] evaluate(){
      //no pairs in  stage
      for(int i = 0; i < 4; i++){
        if(hand[i].getValue() == 11 && hand[i+1].getValue() == 12
        && hand[i].getSuit() == hand[i+1].getSuit()){
          int[] ret = { i, i+1 };
          return ret;
        }
      }
      return null;
    }


  }
