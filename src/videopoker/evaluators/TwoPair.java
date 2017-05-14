package videopoker.evaluators;

import videopoker.utils.HandRank;

class TwoPair extends Evaluator{

  /**
  * Creates an instance of class TwoPair
  */
  TwoPair(){}

    /**
    * Checks if exists two pairs in the hand
    */
    @Override
    public int[] evaluate(){
      if(handRank == HandRank.TWOPAIR){
        //find where the pairs are
        int[] ret = new int[4];
        int j = 0;
        for(int i = 0; i < 4; i++){
          if(hand[i].getValue() == hand[i+1].getValue()){
            ret[j] = i;
            ret[j+1] = i+1;
            i++; j += 2;
          }
        }
        return ret;
      }
      return null;
    }


  }
