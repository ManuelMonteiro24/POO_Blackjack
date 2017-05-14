package videopoker.evaluators;


/**
 * Subclass of Evaluator
 *
 * Checks if the are 4 cards in hand that could make a Straight
 */
class FourToInStraightNoHC extends Evaluator{


  /**
  * Creates an instance of class FourToInStraightNoHC
  */
  FourToInStraightNoHC(){}

    /**
    * Method that checks if the are 4 cards in hand that could make a Straight
    */
    @Override
    public int[] evaluate(){
      int[] indexes;
      if((indexes = fourToIStraight()) != null)
      return indexes;
      return null;
    }


  }
