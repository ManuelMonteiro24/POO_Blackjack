package videopoker.evaluators;

class FourToFlush extends Evaluator{

        FourToFlush(){}

        @Override
        public int[] evaluate(){
                int j1 = 1, j2 = 0;
                int[] indexes2 = new int[4], indexes1 = {0, -1, -1, -1};
                char suit2 = '\0', suit1 = hand[0].getSuit();

                for(int i = 1; i < hand.length; i++) {
                        if(hand[i].getSuit() == suit1) {
                                indexes1[j1++] = i;
                        } else if(hand[i].getSuit() == suit2) {
                                indexes2[j2++] = i;
                        } else if(i < 2) {
                                suit2 = hand[i].getSuit();
                                indexes2[j2++] = i;
                        }
                }

                if(j1 == 4)
                        return indexes1;
                if(j2 == 4)
                        return indexes2;

                return null;
        }


}