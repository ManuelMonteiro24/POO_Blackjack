package videopoker.evaluators;

class KTSuited extends Evaluator{

  /**
  * Creates an instance of class KTSuited
  */
  KTSuited(){}

    /**
    * Checks if exists a King and a Ten of the same suit in hand
    */
    @Override
    public int[] evaluate(){
      if(hand[hand.length - 1].getValue() == 13)
      for(int i = 0; i < hand.length - 1; i++)
      if(hand[i].getValue() == 10 && hand[i].getSuit() == hand[hand.length - 1].getSuit()) {
        int[] ret = { i, hand.length - 1 };
        return ret;
      }

      return null;
    }


  }
