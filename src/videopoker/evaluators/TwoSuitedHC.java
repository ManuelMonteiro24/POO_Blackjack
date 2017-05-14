package videopoker.evaluators;

class TwoSuitedHC extends Evaluator{

  /**
  * Creates an instance of class TwoSuitedHC
  */
  TwoSuitedHC(){}

    /**
    * Checks if exists two High Cards (J,Q,K,A) from the same suit in the hand
    */
    @Override
    public int[] evaluate(){
      for(int i = 0; i < hand.length; i++)
      for(int j = i+1; j < hand.length; j++)
      if(hand[i].getSuit() == hand[j].getSuit() && hand[i].isHighCard() && hand[j].isHighCard()){
        int ret[] = {i, j};
        return ret;
      }

      return null;
    }


  }
