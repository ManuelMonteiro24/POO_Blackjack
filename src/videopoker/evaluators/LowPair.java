package videopoker.evaluators;

import videopoker.utils.HandRank;

/**
 * Subclass of Evaluator
 *
 * Checks if exists a pair (without highcards J,Q,K,A) in the hand
 */
class LowPair extends Evaluator{

  /**
  * Creates an instance of class LowPair
  */
  LowPair(){}

    /**
    * Method that checks if exists a pair (without highcards J,Q,K,A) in the hand
    */
    @Override
    public int[] evaluate(){
      if(handRank == HandRank.PAIR){
        //find where the pair is
        for(int i = 0; i < hand.length - 1; i++){
          if(hand[i].getValue() == hand[i+1].getValue()){
            int[] ret = {i, i+1};
            return ret;
          }
        }
      }
      return null;
    }


  }
