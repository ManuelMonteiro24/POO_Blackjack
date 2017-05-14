package videopoker.evaluators;

import java.util.Arrays;

class FourToRoyalFlush extends Evaluator{

  /**
  * Creates an instance of class FourToRoyalFlush
  */
  FourToRoyalFlush(){}

    /**
    * Checks if there are 4 cards that could make a Royal flush
    */

    @Override
    public int[] evaluate(){
      int checkSuits = 1;
      char suit2 = '\0', suit1 = hand[0].getSuit();
      for(int i = 1; i < hand.length; i++)
      if(hand[i].getSuit() != suit1)
      if(hand[i].getSuit() != suit2) {
        if (suit2 == '\0')
        suit2 = hand[i].getSuit();
        checkSuits++;
      }

      if(checkSuits > 2)
      return null;

      int[] indexes1 = {-1, -1, -1, -1, -1}, indexes2 = {-1, -1, -1, -1, -1};
      for(int i = 0; i < hand.length; i++){
        switch(hand[i].getValue()){
          case 1:
          if(hand[i].getSuit() == suit1)
          indexes1[0] = i;
          else
          indexes2[0] = i;
          break;
          case 10:
          if(hand[i].getSuit() == suit1)
          indexes1[1] = i;
          else
          indexes2[1] = i;
          break;
          case 11:
          if(hand[i].getSuit() == suit1)
          indexes1[2] = i;
          else
          indexes2[2] = i;
          break;
          case 12:
          if(hand[i].getSuit() == suit1)
          indexes1[3] = i;
          else
          indexes2[3] = i;
          break;
          case 13:
          if(hand[i].getSuit() == suit1)
          indexes1[4] = i;
          else
          indexes2[4] = i;
          break;
          default:
          break;
        }
      }

      Arrays.sort(indexes1);
      Arrays.sort(indexes2);

      if(indexes1[1] != -1){
        return Arrays.copyOfRange(indexes1, 1, indexes1.length);
      }

      if(indexes2[1] != -1) {
        return Arrays.copyOfRange(indexes2, 1, indexes2.length);
      }

      return null;

    }


  }
