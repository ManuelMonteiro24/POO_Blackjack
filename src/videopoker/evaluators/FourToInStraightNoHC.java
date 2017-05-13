package videopoker.evaluators;

class FourToInStraightNoHC extends Evaluator{


  /**
  * Creates an instance of class FourToInStraightNoHC
  * @return instance of class FourToInStraightNoHC created
  */
  FourToInStraightNoHC(){}

    /**
    * Checks if the are 4 cards in hand that could make a Straight
    */
    @Override
    public int[] evaluate(){
      int[] indexes;
      if((indexes = fourToIStraight()) != null)
      return indexes;
      return null;
    }


  }
