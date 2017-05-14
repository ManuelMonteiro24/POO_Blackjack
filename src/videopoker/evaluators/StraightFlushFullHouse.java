package videopoker.evaluators;

import videopoker.utils.HandRank;

/**
 * Subclass of Evaluator
 * Checks if exists a Straight a Flush or a FullHouse in the hand
 */
class StraightFlushFullHouse extends Evaluator{

  /**
  * Creates an instance of class StraightFlushFullHouse
  */
  StraightFlushFullHouse(){}

    /**
    * Method that checks if exists a Straight a Flush or a FullHouse in the hand
    */
    @Override
    public int[] evaluate(){
      if(handRank == HandRank.STRAIGHT || handRank == HandRank.FLUSH || handRank == HandRank.FULLHOUSE){
        int[] indexes = { 0, 1, 2, 3, 4};
        return indexes;
      } else
      return null;
    }


  }
