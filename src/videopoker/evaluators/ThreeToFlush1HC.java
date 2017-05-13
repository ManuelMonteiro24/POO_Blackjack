package videopoker.evaluators;

class ThreeToFlush1HC extends Evaluator{

    ThreeToFlush1HC(){}

    @Override
    public int[] evaluate(){
        int highCards =0;
        int[] indexes;

        // check 3 cards from same suit
        if((indexes = threeToFlush()) == null)
            return null;

        //Check for 1 HC
        for(int i = 0; i < indexes.length; i++)
            if (hand[indexes[i]].isHighCard()) // J,Q,K,A
                highCards++;

        if(highCards == 1){
            return indexes;
        }else
            return null;
        }


}