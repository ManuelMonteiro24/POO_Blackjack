package videopoker.evaluators;

class Ace extends Evaluator{

    Ace(){}

    @Override
    public int[] evaluate(){
        int[] ret = new int[1];
        for(int i = 0; i < hand.length; i++)
            if(hand[i].getValue() == 1){
                System.out.println("ace");
                ret[0] = i;
                return ret;
            }


        return null;
    }


}