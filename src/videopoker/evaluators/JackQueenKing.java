package videopoker.evaluators;

class JackQueenKing extends Evaluator{

    JackQueenKing(){}

    @Override
    public int[] evaluate(){
        int[] check = new int[1];
        for(int i = 0; i < hand.length; i++)
            if(hand[i].getValue() == 13 || hand[i].getValue() == 12 || hand[i].getValue() == 11) {
                check[0] = i;
                return check;
            }

        return null;
    }


}