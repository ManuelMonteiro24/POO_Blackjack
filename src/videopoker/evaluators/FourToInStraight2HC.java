package videopoker.evaluators;

class FourToInStraight2HC extends Evaluator{

        FourToInStraight2HC(){}

        @Override
        public int[] evaluate(){
            int highCards =0;
            int[] indexes;

            if((indexes = fourToIStraight()) == null)
                return null;

            for(int i = 0; i < indexes.length; i++)
                if (hand[indexes[i]].isHighCard()) // J,Q,K,A
                    highCards++;

            if(highCards == 2)
                return indexes;
            else
                return null;
        }


}