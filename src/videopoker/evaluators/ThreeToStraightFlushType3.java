package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if exists three cards that could make a Royal Flush, with two gaps and no high cards
 */
class ThreeToStraightFlushType3 extends Evaluator{

  /**
  * Creates an instance of class ThreeToStraightFlushType3
  */
  ThreeToStraightFlushType3(){}

    /**
    * Method that checks if exists three cards that could make a Royal Flush, with two gaps and no high cards
    */
    @Override
    public int[] evaluate(){
      int[] indexes;
      if((indexes = threeToFlush()) == null)
      return null;
      if(isThreeToStraight(indexes))
      return indexes;
      else
      return null;
    }


  }
