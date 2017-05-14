package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if exists three cards in the hand that could make a Flush where
 * one of them is a high Card (J,Q,K,A)
 */
class ThreeToFlush1HC extends Evaluator{

  /**
  * Creates an instance of class ThreeToFlush1HC
  */
  ThreeToFlush1HC(){}

    /**
    * Method that checks if exists three cards in the hand that could make a Flush where
    * one of them is a high Card (J,Q,K,A)
    */
    @Override
    public int[] evaluate(){
      int highCards =0;
      int[] indexes;

      // check 3 cards from same suit
      if((indexes = threeToFlush()) == null)
      return null;

      //Check for 1 HC
      for(int i = 0; i < indexes.length; i++)
      if (hand[indexes[i]].isHighCard()) // J,Q,K,A
      highCards++;

      if(highCards == 1){
        return indexes;
      }else
      return null;
    }


  }
