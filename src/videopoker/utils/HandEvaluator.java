package videopoker.utils;

import videopoker.utils.HandRank;
import videopoker.cards.Card;
import videopoker.cards.Hand;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class HandEvaluator{

    private Card[] hand;
    protected HandRank handRank;

    public HandEvaluator(){}

    public HandEvaluator(Hand playersHand){
        this.hand = Arrays.copyOf(playersHand.toCardArray(), playersHand.toCardArray().length);
        Arrays.sort(this.hand); //sort array of cards according to Card.compareTo() method from Comparable<T> interface
    }

    public void updateEvaluator(Hand playersHand){
        this.hand = Arrays.copyOf(playersHand.toCardArray(), playersHand.toCardArray().length);
        Arrays.sort(this.hand);
    }

    private boolean isFlush(){
        char suite = this.hand[0].getSuit();
        for(int i = 1; i < hand.length; i++)
            if(this.hand[i].getSuit() != suite)
                return false;
        return true;
    }

    /*This function needs the cards in @hand to be ordered*/
    private boolean isStraight() {
        int prev = this.hand[0].getValue();

        for(int i = 1; i < this.hand.length; i++){
            if(this.hand[i].getValue() != prev + 1) {
                if (!(prev == 1 && this.hand[i].getValue() == 10))
                    return false;
            }
            prev = this.hand[i].getValue();
        }

        return true;
    }

    /*This function needs the cards in @hand to be ordered*/
    private int valueStreak(int searchIndex){
        int streak = 0;
        int value = this.hand[searchIndex].getValue();

        for(int i = searchIndex + 1; i < this.hand.length; i++)
            if(this.hand[i].getValue() == value)
                streak++;
            else
                break;

        return streak;
    }

    public HandRank evaluate(){
        int valueStreak, value;
        HandRank currentRank = HandRank.NON;

        for (int i = 0; i < 5; i++) {
            value = this.hand[i].getValue();
            valueStreak = this.valueStreak(i);

            if(valueStreak == 3) {
                if (value == 1)
                    return (this.handRank =  HandRank.FOAK_A);
                if (value < 5)
                    return (this.handRank =  HandRank.FOAK_24);
                else
                    return (this.handRank =  HandRank.FOAK_5K);

            } else if(valueStreak == 2) {
                if (currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    return (this.handRank =  HandRank.FULLHOUSE);
                currentRank = HandRank.TOAK;

            } else if(valueStreak == 1) {
                if(currentRank == HandRank.TOAK)
                    return (this.handRank =  HandRank.FULLHOUSE);
                if(currentRank == HandRank.PAIR || currentRank == HandRank.JoB)
                    return (this.handRank =  HandRank.TWOPAIR);

                if(value > 10 || value == 1)
                    currentRank = HandRank.JoB;
                else
                    currentRank = HandRank.PAIR;
            }
            i += valueStreak;
        }

        if(currentRank == HandRank.NON){ //if @currentRank is different from NON it is impossible for the @hand to be a STRAIGHT or a FLUSH
            if (this.isStraight()) {
                if (this.isFlush()) {
                    if (this.hand[hand.length - 1].getValue() == 13 && this.hand[0].getValue() == 1)
                        return (this.handRank =  HandRank.ROYAL_FLUSH);
                    else
                        return (this.handRank =  HandRank.STRAIGHT_FLUSH);
                } else
                    return (this.handRank =  HandRank.STRAIGHT);
            } else if (this.isFlush())
                return (this.handRank =  HandRank.FLUSH);
        }

        this.handRank =  currentRank;
        return currentRank;
    }

    //return the number of different high cards
    private int diffHighCards(){
      int ret =0;
      boolean[] highvalues = new boolean[4]; //J Q K A

      for(int i = 0; i < this.hand.length; i++){
        if(this.hand[i].getValue() == 1) //J
          highvalues[0] = true;
        if(this.hand[i].getValue() == 11) // Q
          highvalues[1] = true;
        if(this.hand[i].getValue() == 12) //K
          highvalues[2] = true;
        if(this.hand[i].getValue() == 13) //A
          highvalues[3] = true;
      }
      for(int i = 0; i < 4; i++)
        if(highvalues[i])
          ret++;

      return ret;
    }

    private boolean isThreeToStraight(int[] indexes){
        int straight1 = 0, straight2 = 0, straight3 = 0, base = this.hand[indexes[0]].getValue();
        for(int i = 1; i < indexes.length; i++){
            if(this.hand[indexes[i]].getValue() >= (base - 2) && this.hand[indexes[i]].getValue() <= (base + 2))
                straight1++;
            if(this.hand[indexes[i]].getValue() >= (base - 1) && this.hand[indexes[i]].getValue() <= (base + 3))
                straight2++;
            if(this.hand[indexes[i]].getValue() > base && this.hand[indexes[i]].getValue() <= (base + 4))
                straight3++;
        }

        if(straight1 != 2 && straight2 != 2 && straight3 != 2)
            return false;
        return true;
    }

    private int getIndexCardOutOfSuit(){
      int index = -1;
      int flag = 0;
      char suit = this.hand[0].getSuit();

      for(int i = 1; i < this.hand.length; i++){
        if(this.hand[i].getSuit() != suit && flag == 0){
          index = i;
          break;
        }
        if(this.hand[i].getSuit() != suit && flag != 0)
          break;

      }
      return index;

    }

    public int[] indexOrderedToUnordered(int[] orderedIndexes, Hand unorderedHand){
        Card[] unorderedCards = unorderedHand.toCardArray();
        int[] unorderedIndexes = new int[orderedIndexes.length];
        int j = 0;

        for(int i = 0; i < unorderedCards.length; i++)
            for (int index : orderedIndexes)
                if (this.hand[index].getValue() == unorderedCards[i].getValue() && this.hand[index].getSuit() == unorderedCards[i].getSuit())
                    unorderedIndexes[j++] = i;

        return unorderedIndexes;
    }

    public int[] getAdvice(){

      int[] ret, all = {0, 1, 2, 3, 4};

      evaluate();
      System.out.println("ordered " + Arrays.toString(this.hand));
      System.out.println(this.handRank);
      if(this.SF_RF())
        return all;

      if((ret = this.Foak()) != null)
          return ret;

      if((ret = this.fourToRF()) != null)
        return ret;

      if((ret = this.threeAces()) != null)
        return ret;

      if(this.S_F_FH())
        return all;

      if((ret = this.Toak()) != null)
        return ret;

      if((ret = this.fourToSF()) != null)
        return ret;

      if((ret = this.twoPair()) != null)
        return ret;

      if((ret = this.highPair()) != null)
        return ret;

      if((ret = this.fourToFlush()) != null)
        return ret;

      if((ret = this.threeToRF()) != null)
        return ret;

      if((ret = this.fourToOutStraight()) != null)
        return ret;

      if((ret = this.lowPair()) != null)
        return ret;

      if((ret = this.unsuitedAKQJ()) != null)
        return ret;

      if((ret = this.threeToSFtype1()) != null)
        return ret;
    System.out.println("sdasdad");
      if((ret = this.fourToIStraight3HC()) != null)
        return ret;

      if((ret = this.suitedQJ()) != null)
        return ret;

      if((ret = this.threeToFlush2HC()) != null)
        return ret;

      if((ret = this.suited2HC()) != null)
        return ret;

      if((ret = this.fourToIStraight2HC()) != null)
        return ret;

      if((ret = this.threeToFlush2HCtoSFtype2()) != null)
        return ret;

      if((ret = this.fourToIStraight1HC()) != null)
        return ret;

      if((ret = this.unsuiedtKQJ()) != null)
        return ret;

      if((ret = this.suitedJT()) != null)
        return ret;

      if((ret = this.unsuitedQJ()) != null)
        return ret;

      if((ret = this.threeToFlush1HC()) != null)
        return ret;

      if((ret = this.suitedQT()) != null)
        return ret;

      if((ret = this.threeToSFtype3()) != null)
        return ret;

      if((ret = this.unsuitedKQKJ()) != null)
        return ret;

      if((ret = this.ace()) != null)
        return ret;

      if((ret = this.suitedKT()) != null)
        return ret;

      if((ret = this.JQK()) != null)
        return ret;

      if((ret = this.fourToIStraight()) != null)
        return ret;

      if((ret = this.threeToFlush()) != null)
        return ret;
        System.out.println("AFTER");
      //Discard everything
      return null;
    }

    //CHECK
    private boolean SF_RF(){
      if(this.handRank == HandRank.STRAIGHT_FLUSH || this.handRank == HandRank.ROYAL_FLUSH)
        return true;
      else
        return false;
    }

    //CHECK
    private int[] Foak(){
        if(this.handRank == HandRank.FOAK_5K || this.handRank == HandRank.FOAK_24 || this.handRank == handRank.FOAK_A) {
            if (valueStreak(0) == 3) {
                int[] ret = {0, 1, 2, 3};
                return ret;
            }

            if (valueStreak(1) == 3) {
                int[] ret = {1, 2, 3, 4};
                return ret;
            }
        }
        return null;
    }

    //CHECK
    private int[] fourToRF(){
        int checkSuits = 1;
        char suit2 = '\0', suit1 = this.hand[0].getSuit();
        for(int i = 1; i < this.hand.length; i++)
            if(this.hand[i].getSuit() != suit1)
                if(this.hand[i].getSuit() != suit2) {
                    if (suit2 == '\0')
                        suit2 = this.hand[i].getSuit();
                    checkSuits++;
                }

        if(checkSuits > 2)
            return null;

        int[] indexes1 = {-1, -1, -1, -1, -1}, indexes2 = {-1, -1, -1, -1, -1};
        for(int i = 0; i < this.hand.length; i++){
            switch(this.hand[i].getValue()){
                case 1:
                    if(this.hand[i].getSuit() == suit1)
                        indexes1[0] = i;
                    else
                        indexes2[0] = i;
                    break;
                case 10:
                    if(this.hand[i].getSuit() == suit1)
                        indexes1[1] = i;
                    else
                        indexes2[1] = i;
                    break;
                case 11:
                    if(this.hand[i].getSuit() == suit1)
                        indexes1[2] = i;
                    else
                        indexes2[2] = i;
                    break;
                case 12:
                    if(this.hand[i].getSuit() == suit1)
                        indexes1[3] = i;
                    else
                        indexes2[3] = i;
                    break;
                case 13:
                    if(this.hand[i].getSuit() == suit1)
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
            System.out.println("4ToRoyalFlush1");
            return Arrays.copyOfRange(indexes1, 1, indexes1.length);
        }

        if(indexes2[1] != -1) {
            System.out.println("4ToRoyalFlush2");
            return Arrays.copyOfRange(indexes2, 1, indexes2.length);
        }

        return null;
    }

    //CHECK
    private int[] threeAces(){
      //check if its a toak
      if(this.handRank == HandRank.TOAK || this.handRank == HandRank.FULLHOUSE){
        //check if the toak is from aces
        if(this.hand[0].getValue() == 1 && this.hand[0].getValue() == this.hand[1].getValue() && this.hand[1].getValue() == this.hand[2].getValue()){
          int[] ret = {0, 1, 2};
            System.out.println("3aces");
          return ret;
        }
      }
      return null;
    }

    //CHECK
    private boolean  S_F_FH(){
      if(this.handRank == HandRank.STRAIGHT || this.handRank == HandRank.FLUSH || this.handRank == handRank.FULLHOUSE){
          System.out.println("SFFH");
        return true;}
      else
        return false;
    }

    //CHECK
    private int[] Toak(){
      if(this.handRank == HandRank.TOAK){
        //find where the Toak is
        for(int i = 0; i < 3; i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()
          && this.hand[i+1].getValue() == this.hand[i+2].getValue()){
            int[] ret = {i, i+1, i+2};
            System.out.println("TOAK");
            return ret;
          }
        }
      }
      return null;
    }

    //CHECK
    private int[] fourToSF() {
        //to do...
        int[] indexes;
        int i, straight1, straight2;

        if ((indexes = this.fourToFlush()) == null)
            return null;

        straight1 = straight2 = 0;
        int base = this.hand[indexes[0]].getValue();
        for(i = 1; i < indexes.length; i++){
            if(this.hand[indexes[i]].getValue() >= (base - 1) && this.hand[indexes[i]].getValue() <= (base + 3))
                straight1++;
            if(this.hand[indexes[i]].getValue() > base && this.hand[indexes[i]].getValue() <= (base + 4))
                straight2++;
        }

        if(straight1 == 3){
            System.out.println("4toSF");
            return indexes;}
        if(straight2 == 3){
            System.out.println("4toSF");
            return indexes;}

        return null;

    }

    //CHECK
    private int[] twoPair(){
      if(this.handRank == HandRank.TWOPAIR){
        //find where the pairs are
        int[] ret = new int[4];
        int j = 0;
        for(int i = 0; i < 4; i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()){
            ret[j] = i;
            ret[j+1] = i+1;
            i++; j += 2;
          }
        }
        return ret;
      }
      return null;
    }

    //CHECK
    private int[] highPair(){
      if(this.handRank == HandRank.JoB){
        //find where the pair is
        for(int i = 0; i < 4; i++){
          if(this.hand[i].getValue() == this.hand[i+1].getValue()){
            int[] ret = {i, i+1};
            System.out.println("HP");
            return ret;
          }
        }
      }
      return null;
    }

    //CHECK
    private int[] fourToFlush(){
        int j1 = 1, j2 = 0;
        int[] indexes2 = new int[4], indexes1 = {0, -1, -1, -1};
        char suit2 = '\0', suit1 = this.hand[0].getSuit();

        for(int i = 1; i < this.hand.length; i++)
            if(this.hand[i].getSuit() == suit1) {
                indexes1[j1++] = i;
            } else if(this.hand[i].getSuit() == suit2) {
                indexes2[j2++] = i;
            } else if(i < 2) {
                suit2 = this.hand[i].getSuit();
                indexes2[j2++] = i;
            }

        System.out.println("4toFlush");
        if(j1 == 4)
          return indexes1;
        if(j2 == 4)
          return indexes2;

        System.out.println("not 4toFlush");
        return null;
    }

    //CHECK
    private int[] threeToRF(){
        int[] indexes;

        if((indexes = this.threeToFlush()) == null)
            return null;

        if(this.hand[indexes[0]].getValue() == 11 || ((this.hand[indexes[0]].getValue() == 1 || this.hand[indexes[0]].getValue() == 10) && this.hand[indexes[1]].getValue() >= 10)){
            System.out.println("3ToRoyalFlush");
            return indexes;
        }

      return null;
    }

    //CHECK
    private int[] fourToOutStraight(){
        int straight1 = 0, straight2 = 0;

        for(int i = 1; i < this.hand.length; i++)
            if((this.hand[0].getValue() + i) == this.hand[i].getValue())
                straight1++;
            else
                break; //if streak is broken, break

        if(straight1 == 3){
            System.out.println("4toOutStraight 0 1 2 3");
            int[] ret = {0, 1, 2, 3};
            return ret;
        }

        for(int i = 2; i < this.hand.length; i++)
            if((this.hand[1].getValue() + i - 1) == this.hand[i].getValue())
                straight2++;
            else
                break;

        if(straight2 == 3){
            System.out.println("4toOutStraight 1 2 3 4");
            int[] ret = {1, 2, 3, 4};
            return ret;
        }

        return null;
    }

    //CHECK
    private int[] lowPair(){
        if(this.handRank == HandRank.PAIR){
        //find where the pair is
            for(int i = 0; i < this.hand.length - 1; i++){
                if(this.hand[i].getValue() == this.hand[i+1].getValue()){
                    System.out.println("lowPair");
                    int[] ret = {i, i+1};
                    return ret;
                }
            }
        }
        return null;
    }

    //CHECK
    private int[] unsuitedAKQJ(){
        if(this.diffHighCards() == 4){
            System.out.println("unAKQJ");
            int[] ret = {0, 2, 3, 4};
            return ret;
        }
        return null;
    }

    //CHECK
    private int[] threeToSFtype1(){
        int i, jumps = 0, highCards = 0;
        int[] indexes;

        if((indexes = threeToFlush()) == null)
            return null;

        if(!isThreeToStraight(indexes))
            return null;
        /*now we know it's a 3 to a straight flush*/
        for(i = 0; i < indexes.length; i++)
            if (this.hand[indexes[i]].isHighCard()) //do not count aces as high cards; there will
                highCards++;

        if(this.hand[indexes[0]].getValue() == 1 || (this.hand[indexes[0]].getValue() == 2 && this.hand[indexes[2]].getValue() == 4)) //if its ace low or
            return null;

        if((this.hand[indexes[0]].getValue() + 1) != this.hand[indexes[1]].getValue())
            jumps++;
        if((this.hand[indexes[1]].getValue() + 1) != this.hand[indexes[2]].getValue())
            jumps++;

        if(highCards >= jumps)
            System.out.println("3toSF1 jumps "+jumps+" highCards "+highCards);

        return highCards >= jumps ? indexes : null;
    }

    //return cards to hold! if not return null
    private int[] fourToIStraight3HC(){

        return null;
    }

    //CHECK
    private int[] suitedQJ(){
    //no pairs in this stage
        for(int i = 0; i < 4; i++){
            if(this.hand[i].getValue() == 11 && this.hand[i+1].getValue() == 12
            && this.hand[i].getSuit() == this.hand[i+1].getSuit()){
                System.out.println("suitedQJ");
                int[] ret = { i, i+1 };
                return ret;
            }
        }
    return null;
    }

    //CHECK
    private int[] threeToFlush2HC(){
        int[] indexes;
        if((indexes = threeToFlush()) == null)
            return null;

        int hc = 0;
        for(int i : indexes)
            if(this.hand[i].isHighCard()) hc++;

        if(hc == 2)
        System.out.println("3toF2HC");
        return hc != 2 ? null : indexes;

    }

    //CHECK
    private int[] suited2HC(){

        for(int i = 0; i < this.hand.length; i++)
            for(int j = i+1; j < this.hand.length; j++)
                if(this.hand[i].getSuit() == this.hand[j].getSuit() && this.hand[i].isHighCard() && this.hand[j].isHighCard()){
            System.out.println("suited2HC");
                    int ret[] = {i, j};
                    return ret;
                }

        return null;
    }

    //return cards to hold! if not return null
    private int[] fourToIStraight2HC(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] threeToFlush2HCtoSFtype2(){
      //to do...
        return null;
    }

    //return cards to hold! if not return null
    private int[] fourToIStraight1HC(){
      //to do...
        return null;
    }

    //CHECK
    private int[] unsuiedtKQJ(){
        int[] check = {-1, -1, -1}; //0J, 1Q, 2K
        for(int i = 0; i < this.hand.length - 2; i++)
            if(this.hand[i].getValue() == 11 && this.hand[i+1].getValue() == 12 && this.hand[i+2].getValue() == 13){
                System.out.println("unsuitedKQJ");
                int[] ret = {i, i+1, i+2};
                return ret;
            }

        return null;
    }

    //CHECK
    private int[] suitedJT(){
        for(int i = 0; i < 4; i++){
            if(this.hand[i].getValue() == 10 && this.hand[i+1].getValue() == 11
                    && this.hand[i].getSuit() == this.hand[i+1].getSuit()){
                System.out.println("suitedJT");
                int[] ret = { i, i+1 };
                return ret;
            }
        }
        return null;
    }

    //CHECK
    private int[] unsuitedQJ(){
        for(int i = 0; i < this.hand.length - 1; i++){
            if(this.hand[i].getValue() == 11 && this.hand[i+1].getValue() == 12){
                System.out.println("unsuitedQJ");
                int[] ret = { i, i+1 };
                return ret;
            }
        }
        return null;
    }

    //return cards to hold! if not return null
    private int[] threeToFlush1HC(){
      //to do...
        return null;
    }

    //CHECK
    private int[] suitedQT(){
        for(int i = 0; i < this.hand.length - 1; i++){ //ten and Q will be sequencial because unsuitedQJ was already tested
            if(this.hand[i].getValue() == 10 && this.hand[i+1].getValue() == 12){
                System.out.println("suitedQT");
                int[] ret = { i, i+1 };
                return ret;
            }
        }
        return null;
    }

    //CHECK
    private int[] threeToSFtype3(){
        int[] indexes;
        if((indexes = threeToFlush()) == null)
            return null;
        if(!isThreeToStraight(indexes))
            return null;
        /*now we know it is a three to straight flush*/
        if((this.hand[indexes[0]].getValue() + 2) == this.hand[indexes[1]].getValue() || (this.hand[indexes[1]].getValue() + 2) == this.hand[indexes[2]].getValue()){
            System.out.println("3toSFt3");
            return indexes;
        }

        return null;
    }

    //CHECK
    private int[] unsuitedKQKJ(){
        if(this.hand[hand.length - 1].getValue() == 13 && (this.hand[hand.length - 2].getValue() == 12 || this.hand[hand.length - 2].getValue() == 11)){
            System.out.println("unsuitedKQKJ");
            int[] ret = {hand.length - 1, hand.length - 2};
            return ret;
        }

        return null;
    }

    //CHECK
    private int[] ace(){
        int[] ret = new int[1];
        for(int i = 0; i < this.hand.length; i++)
            if(this.hand[i].getValue() == 1){
            System.out.println("ace");
                ret[0] = i;
                return ret;
            }


        return null;
    }

    //CHECK
    private int[] suitedKT(){ //There will only be 1 Ten and 1 K maximum; pairs have already been analised

        if(this.hand[this.hand.length - 1].getValue() == 13)
            for(int i = 0; i < this.hand.length - 1; i++)
                if(this.hand[i].getValue() == 10 && this.hand[i].getSuit() == this.hand[this.hand.length - 1].getSuit()) {
                    System.out.println("suitedKT");
                    int[] ret = { i, this.hand.length - 1 };
                    return ret;
                }

        return null;
    }

    //CHECK
    //Jack or Queen or King
    private int[] JQK(){
        int[] check = new int[1];
        for(int i = 0; i < this.hand.length; i++)
            if(this.hand[i].getValue() == 13 || this.hand[i].getValue() == 12 || this.hand[i].getValue() == 11) {
                System.out.println("JorQorK");
                check[0] = i;
                return check;
            }

        return null;
    }

    //return cards to hold! if not return null
    private int[] fourToIStraight(){
      //to do...
        return null;
    }

    //CHECK
    private int[] threeToFlush(){
        int j1 = 1, j2 = 0, j3 = 0;
        int[] indexes2 = new int[3], indexes3 = new int[3], indexes1 = {0, -1, -1};
        char suit2 = '\0', suit3 = '\0', suit1 = this.hand[0].getSuit();

        if(this.handRank == HandRank.FLUSH || this.handRank == HandRank.STRAIGHT_FLUSH)
            return null;

        for(int i = 1; i < this.hand.length; i++)
            if(this.hand[i].getSuit() == suit1) {
                indexes1[j1++] = i;
            } else if(this.hand[i].getSuit() == suit2) {
                indexes2[j2++] = i;
            } else if(this.hand[i].getSuit() == suit3) {
                indexes3[j3++] = i;
            } else if(i < 2) {
                suit2 = this.hand[i].getSuit();
                indexes2[j2++] = i;
            } else if(i < 3) {
                suit3 = this.hand[i].getSuit();
                indexes3[j3++] = i;
            }

        System.out.println("3toFlush ");
        if(j1 == 3)
            return indexes1;
        if(j2 == 3)
            return indexes2;
        if(j3 == 3)
            return indexes3;

        System.out.println("not 3toFlush");
        return null;
    }
}
