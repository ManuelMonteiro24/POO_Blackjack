package videopoker.evaluators;

class JTSuited extends Evaluator{

  /**
  * Creates an instance of class JTSuited
  * @return instance of class JTSuited created
  */
  JTSuited(){}

    /**
    * Checks if exists a Jack and Queen of the same suit in the hand
    */
    @Override
    public int[] evaluate(){
      for(int i = 0; i < 4; i++){
        if(hand[i].getValue() == 10 && hand[i+1].getValue() == 11
        && hand[i].getSuit() == hand[i+1].getSuit()){
          int[] ret = { i, i+1 };
          return ret;
        }
      }
      return null;
    }


  }
