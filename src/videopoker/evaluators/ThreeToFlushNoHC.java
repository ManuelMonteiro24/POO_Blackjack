package videopoker.evaluators;

class ThreeToFlushNoHC extends Evaluator{

  /**
  * Creates an instance of class ThreeToFlushNoHC
  */
  ThreeToFlushNoHC(){}

    /**
    * Checks if exists three cards in the hand that could make a Flush where
    * three of them is are high Cards (J,Q,K,A)
    */
    @Override
    public int[] evaluate(){
      int[] indexes;
      if((indexes = threeToFlush()) != null)
      return indexes;

      return null;
    }


  }
