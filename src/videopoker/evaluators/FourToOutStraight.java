package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if there are 4 cards that could make a Straight
 *
 */
class FourToOutStraight extends Evaluator{

  /**
  * Creates an instance of class FourToOutStraight
  */
  FourToOutStraight(){}

    /**
    * Method that hecks if there are 4 cards that could make a Straight
    */

    @Override
    public int[] evaluate(){
      int straight1 = 0, straight2 = 0;

      for(int i = 1; i < hand.length; i++)
      if((hand[0].getValue() + i) == hand[i].getValue())
      straight1++;
      else
      break; //if streak is broken, break

      if(straight1 == 3){
        int[] ret = {0, 1, 2, 3};
        return ret;
      }

      for(int i = 2; i < hand.length; i++)
      if((hand[1].getValue() + i - 1) == hand[i].getValue())
      straight2++;
      else
      break;

      if(straight2 == 3){
        int[] ret = {1, 2, 3, 4};
        return ret;
      }

      return null;
    }


  }
