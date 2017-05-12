package videopoker.evaluators;

class FourToInStraight3HC extends Evaluator{

        FourToInStraight3HC(){}

        @Override
        public int[] evaluate(){
            int highCards =0;
            int[] indexes;

            if((indexes = fourToIStraight()) == null)
                return null;

            for(int i = 0; i < indexes.length; i++)
                if (hand[indexes[i]].isHighCard()) // J,Q,K,A
                    highCards++;

            if(highCards == 3)
                return indexes;
            else
                return null;
        }


}