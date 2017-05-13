package videopoker.evaluators;

class QTSuited extends Evaluator{

    QTSuited(){}

    @Override
    public int[] evaluate(){
        for(int i = 0; i < hand.length - 1; i++){ //ten and Q will be sequencial because unsuitedQJ was already tested
            if(hand[i].getValue() == 10 && hand[i+1].getValue() == 12 && hand[i].getSuit() == hand[i+1].getSuit()){
                int[] ret = { i, i+1 };
                return ret;
            }
        }
        return null;
    }


}