package videopoker.evaluators;

class FourToInStraight1HC extends Evaluator{

        FourToInStraight1HC(){}

        @Override
        public int[] evaluate(){
            int highCards =0;
            int[] indexes;

            if((indexes = fourToIStraight()) == null)
                return null;

            for(int i = 0; i < indexes.length; i++)
                if (hand[indexes[i]].isHighCard()) // J,Q,K,A
                    highCards++;

            if(highCards == 1)
                return indexes;

            return null;

        }


}