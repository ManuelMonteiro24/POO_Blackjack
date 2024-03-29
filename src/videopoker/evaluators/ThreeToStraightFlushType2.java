package videopoker.evaluators;

/**
 * Subclass of Evaluator
 *
 * Checks if exists three cards that could make a Royal Flush, with one gap,
 * or with two gaps and one High Card (A,J,Q,K), or any ace-low, or 234 suited
 */
class ThreeToStraightFlushType2 extends Evaluator{

  /**
  * Creates an instance of class ThreeToStraightFlushType2
  */
  ThreeToStraightFlushType2(){}

    /**
    * Method that checks if exists three cards that could make a Royal Flush, with one gap,
    * or with two gaps and one High Card (A,J,Q,K), or any ace-low, or 234 suited
    */
    @Override
    public int[] evaluate(){
      int i, jumps = 0, highCards = 0;
      int[] indexes;

      // check 3 cards from same suit
      if((indexes = threeToFlush()) == null)
      return null;

      // check if it is a possibility do a straight
      if(!isThreeToStraight(indexes))
      return null;

      /*now we know it's a 3 to a straight flush*/
      //check if it is type 1
      for(i = 0; i < indexes.length; i++)
      if (hand[indexes[i]].isHighCard()) // J,Q,K,A
      highCards++;

      //ace low or 234 suited, its for the 3 to SF (type 2)
      if(hand[indexes[0]].getValue() == 1 || (hand[indexes[0]].getValue() == 2 && hand[indexes[2]].getValue() == 4)){
        return indexes;
      }

      //Count Jumps
      if((hand[indexes[0]].getValue() + 1) != hand[indexes[1]].getValue())
      jumps += (hand[indexes[1]].getValue() - hand[indexes[0]].getValue() - 1);
      if((hand[indexes[1]].getValue() + 1) != hand[indexes[2]].getValue())
      jumps += (hand[indexes[2]].getValue() - hand[indexes[1]].getValue() - 1);

      //Check type 2 conditions
      if(jumps ==2 && highCards == 1){
        System.out.println("3toSF1 jumps "+jumps+" highCards "+highCards);
        return indexes;
      }
      if(jumps == 1){
        System.out.println("3toSF1 jumps "+jumps+" highCards "+highCards);
        return indexes;
      }
      return null;
    }


  }
