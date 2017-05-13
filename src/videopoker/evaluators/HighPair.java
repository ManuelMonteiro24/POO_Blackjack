package videopoker.evaluators;

import videopoker.utils.HandRank;

class HighPair extends Evaluator{

  /**
  * Creates an instance of class HighPair
  * @return instance of class HighPair created
  */
  HighPair(){}

    /**
    * Check if there is a pair of Jacks, Queens , King or Aces in hand
    */
    @Override
    public int[] evaluate(){
      if(handRank == HandRank.JoB){
        //find where the pair is
        for(int i = 0; i < 4; i++){
          if(hand[i].getValue() == hand[i+1].getValue()){
            int[] ret = {i, i+1};
            return ret;
          }
        }
      }
      return null;
    }


  }
