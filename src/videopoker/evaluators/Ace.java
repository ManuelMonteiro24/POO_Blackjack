package videopoker.evaluators;

class Ace extends Evaluator{

  /**
  * Creates an instance of class Ace
  * @return instance of class Ace created
  */
  Ace(){}


    /**
    * Checks if there is an Ace in the Hand
    */
    @Override
    public int[] evaluate(){
      int[] ret = new int[1];
      for(int i = 0; i < hand.length; i++)
      if(hand[i].getValue() == 1){
        ret[0] = i;
        return ret;
      }


      return null;
    }


  }
