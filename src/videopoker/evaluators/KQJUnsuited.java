package videopoker.evaluators;

class KQJUnsuited extends Evaluator{

  /**
  * Creates an instance of class KQJUnsuited
  */
  KQJUnsuited(){}

    /**
    * Checks if exists a Jack, Queen and King in the hand
    */
    @Override
    public int[] evaluate(){
      int[] check = {-1, -1, -1}; //0J, 1Q, 2K
      for(int i = 0; i < hand.length - 2; i++)
      if(hand[i].getValue() == 11 && hand[i+1].getValue() == 12 && hand[i+2].getValue() == 13){
        int[] ret = {i, i+1, i+2};
        return ret;
      }

      return null;
    }


  }
