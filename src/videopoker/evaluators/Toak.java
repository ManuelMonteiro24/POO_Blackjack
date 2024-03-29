package videopoker.evaluators;

import videopoker.utils.HandRank;

/**
 * Subclass of Evaluator
 *
 * Checks if exists three cards with the same value in the hand
 */
class Toak extends Evaluator{

  /**
  * Creates an instance of class Toak
  */
  Toak(){}

    /**
    * Method that checks if exists three cards with the same value in the hand
    */
    @Override
    public int[] evaluate(){
      if(handRank == HandRank.TOAK){
        //find where the Toak is
        for(int i = 0; i < 3; i++){
          if(hand[i].getValue() == hand[i+1].getValue()
          && hand[i+1].getValue() == hand[i+2].getValue()){
            int[] ret = {i, i+1, i+2};
            return ret;
          }
        }
      }
      return null;
    }


  }
