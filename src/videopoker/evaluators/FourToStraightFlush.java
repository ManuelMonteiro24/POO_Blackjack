package videopoker.evaluators;

class FourToStraightFlush extends Evaluator{

    FourToStraightFlush(){}
    @Override
    public int[] evaluate(){
        //to do...
        int[] indexes;
        int i, straight1, straight2;

        FourToFlush ftf = new FourToFlush();
        if ((indexes = ftf.evaluate()) == null)
            return null;

        straight1 = straight2 = 0;
        int base = hand[indexes[0]].getValue();
        for(i = 1; i < indexes.length; i++){
            if(hand[indexes[i]].getValue() >= (base - 1) && hand[indexes[i]].getValue() <= (base + 3))
                straight1++;
            if(hand[indexes[i]].getValue() > base && hand[indexes[i]].getValue() <= (base + 4))
                straight2++;
        }

        if(straight1 == 3){
            return indexes;}
        if(straight2 == 3){
            return indexes;
        }

        return null;
    }


}