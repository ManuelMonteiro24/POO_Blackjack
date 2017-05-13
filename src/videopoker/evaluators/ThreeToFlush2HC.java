package videopoker.evaluators;

class ThreeToFlush2HC extends Evaluator{

  /**
  * Creates an instance of class ThreeToFlush2HC
  * @return instance of class ThreeToFlush2HC created
  */
  ThreeToFlush2HC(){}

    /**
    * Checks if exists three cards in the hand that could make a Flush where
    * two of them is are high Cards (J,Q,K,A)
    */
    @Override
    public int[] evaluate(){
      int[] indexes;
      if((indexes = threeToFlush()) == null)
      return null;

      int hc = 0;
      for(int i : indexes)
      if(hand[i].isHighCard()) hc++;

      return hc != 2 ? null : indexes;
    }


  }
