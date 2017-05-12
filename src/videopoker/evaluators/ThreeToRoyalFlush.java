package videopoker.evaluators;

class ThreeToRoyalFlush extends Evaluator{

    ThreeToRoyalFlush(){}

    @Override
    public int[] evaluate(){
        int[] indexes;

        //check if it has 3 cards from same suit
        if((indexes = threeToFlush()) == null)
            return null;

        //check if it has a pair of 10 in those 3 cards
        if(hand[indexes[0]].getValue() == 10 && hand[indexes[1]].getValue() == 10)
            return null;

        //check if those 3 cards with same suit can make RF
        if(hand[indexes[0]].getValue() == 11 || ((hand[indexes[0]].getValue() == 1 || hand[indexes[0]].getValue() == 10) && hand[indexes[1]].getValue() >= 10)){
            System.out.println("3ToRoyalFlush");
            return indexes;
        }

        return null;
    }


}