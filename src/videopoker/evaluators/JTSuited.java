package videopoker.evaluators;

class JTSuited extends Evaluator{

    JTSuited(){}

    @Override
    public int[] evaluate(){
        for(int i = 0; i < 4; i++){
            if(hand[i].getValue() == 10 && hand[i+1].getValue() == 11
                    && hand[i].getSuit() == hand[i+1].getSuit()){
                System.out.println("suitedJT");
                int[] ret = { i, i+1 };
                return ret;
            }
        }
        return null;
    }


}