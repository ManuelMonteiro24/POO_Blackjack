package videopoker.game;

import videopoker.utils.HandRank;
//implement on player??? need to rcv actual balance for N13
public class Scoreboard{

  //pass to final or static??
    private int initialBalance;
    private int dealsNb;
    private final static String[] handType = {"Jacks or Better","Two Pair","Three of a Kind","Straight","Flush","Full house", "Four of a Kind", "Straight Flush", "Royal Flush", "Others"}; //Others = Pairs ??
    private int[] playsNb = new int[10];
    private static HandRank[] plays;

    static{
        plays = HandRank.values();
    }

    Scoreboard(int initialBalance){

        this.initialBalance = initialBalance;
        this.dealsNb = 0;
        for(int i = 0; i < this.playsNb.length; i++)
            this.playsNb[i] = 0;
    }

    //recv other type hand? String?
    public void receiveRoundInfo(HandRank rank){
        this.dealsNb++;
System.out.println("Rank " + rank);
        HandRank[] plays = HandRank.values();
        for(int i = 0; i < plays.length; i++){
            System.out.println(plays[i]);
            if(rank == plays[i]){
                System.out.println(i);
                if(rank == HandRank.FOAK_24 || rank == HandRank.FOAK_5K || rank == HandRank.FOAK_A)
                    this.playsNb[6]++;
                else if(rank == HandRank.PAIR || rank == HandRank.NON)
                    this.playsNb[9]++;
                else{
                    if(i < 8)
                        this.playsNb[i - 2]++;
                    else
                        this.playsNb[i - 4]++;
                }

            }

        }

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("\nHand             Nb\n");
        sb.append("---------------------\n");
        for(int i = 0; i < handType.length; i++){
            sb.append(String.format("%s      %d\n", handType[i], playsNb[i]));

        }
        /*ret += "Total    " + this.playsNb + "\n";
        actual balance e necessario aqui
        ret += "Credit    " + "("+ ((this.initialBalance) * 100) +"%)\n";*/
        System.out.println(sb.toString());
        return sb.toString();
    }
}
