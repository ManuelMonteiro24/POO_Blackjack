package videopoker.evaluators;

class QJUnsuited extends Evaluator{

    QJUnsuited(){}

    @Override
    public int[] evaluate(){
        for(int i = 0; i < hand.length - 1; i++){
            if(hand[i].getValue() == 11 && hand[i+1].getValue() == 12){
                int[] ret = { i, i+1 };
                return ret;
            }
        }
        return null;
    }


}