package videopoker.evaluators;

import videopoker.utils.HandRank;

class Foak extends Evaluator{

  /**
  * Creates an instance of class Foak
  */
  Foak(){}

    /**
    * Checks if theres is 4 cards with the same value on the hand
    */
    @Override
    public int[] evaluate(){
      if(handRank == HandRank.FOAK_5K || handRank == HandRank.FOAK_24 || handRank == HandRank.FOAK_A) {
        if (valueStreak(0) == 3) {
          int[] ret = {0, 1, 2, 3};
          return ret;
        }

        if (valueStreak(1) == 3) {
          int[] ret = {1, 2, 3, 4};
          return ret;
        }
      }
      return null;
    }


  }
