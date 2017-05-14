package videopoker.evaluators;

class FourToInStraight1HC extends Evaluator{

  /**
  * Creates an instance of class FourToInStraight1HC
  */
  FourToInStraight1HC(){}

    /**
    * Checks if the are 4 cards in hand that could make a Straight
    * in which one of them are an High card (J,Q,K,A)
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

      if(highCards == 1)
      return indexes;

      return null;

    }


  }
