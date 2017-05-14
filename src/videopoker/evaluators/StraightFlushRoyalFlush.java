package videopoker.evaluators;

import videopoker.utils.HandRank;

class StraightFlushRoyalFlush extends Evaluator{

  /**
  * Creates an instance of class StraightFlushRoyalFlush
  */
  StraightFlushRoyalFlush(){}

    /**
    * Checks if exists a Straight Flush or a Royal Flush in the hand
    */
    @Override
    public int[] evaluate(){
      if(handRank == HandRank.STRAIGHT_FLUSH || handRank == HandRank.ROYAL_FLUSH) {
        int[] indexes = {0, 1, 2, 3, 4};
        return indexes;
      } else
      return null;
    }


  }
