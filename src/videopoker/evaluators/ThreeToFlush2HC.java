package videopoker.evaluators;

class ThreeToFlush2HC extends Evaluator{

    ThreeToFlush2HC(){}

    @Override
    public int[] evaluate(){
        int[] indexes;
        if((indexes = threeToFlush()) == null)
            return null;

        int hc = 0;
        for(int i : indexes)
            if(hand[i].isHighCard()) hc++;

        return hc != 2 ? null : indexes;
    }


}