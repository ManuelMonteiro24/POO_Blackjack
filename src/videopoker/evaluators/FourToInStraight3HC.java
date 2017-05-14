package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if the are 4 cards in hand that could make a Straight
 * in which three of them are High cards (J,Q,K,A)
 *
 */
class FourToInStraight3HC extends Evaluator{

  /**
  * Creates an instance of class FourToInStraight3HC
  */
  FourToInStraight3HC(){}

    /**
    * Method that checks if the are 4 cards in hand that could make a Straight
    * in which three of them are High cards (J,Q,K,A)
    */
    @Override
    public int[] evaluate(){
      int highCards =0;
      int[] indexes;

      if((indexes = fourToIStraight()) == null)
      return null;

      for(int i = 0; i < indexes.length; i++)
      if (hand[indexes[i]].isHighCard()) // J,Q,K,A
      highCards++;

      if(highCards == 3)
      return indexes;
      else
      return null;
    }


  }
