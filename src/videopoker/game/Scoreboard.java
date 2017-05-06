package videopoker.game;

import videopoker.utils.HandRank;
//implement on player??? need to rcv actual balance for N13
public class Scoreboard{

  //pass to final or static??
    private double initialBalance;
    private double currentBalance;
    private int dealsNb;
    private final static String[] handType = {"Jacks or Better","Two Pair","Three of a Kind","Straight","Flush","Full House", "Four of a Kind", "Straight Flush", "Royal Flush", "Others"}; //Others = Pairs ??
    private final static String[] padding = {"      ", "             ", "      ", "             ", "                ", "           ",  "       ", "       ", "          ", "               "};
    private int[] playsNb = new int[10];
    private static HandRank[] plays;

    static{
        plays = HandRank.values();
    }

    public Scoreboard(int initialBalance){

        this.initialBalance = initialBalance;
        this.dealsNb = 0;
        for(int i = 0; i < this.playsNb.length; i++)
            this.playsNb[i] = 0;
    }

    //recv other type hand? String?
    public void receiveRoundInfo(HandRank rank, double playerBalance) {
        HandRank[] plays = HandRank.values();
        for (int i = 0; i < plays.length; i++) {
            if (rank == plays[i]) {
                System.out.println(i);
                if (rank == HandRank.FOAK_24 || rank == HandRank.FOAK_5K || rank == HandRank.FOAK_A)
                    this.playsNb[6]++;
                else if (rank == HandRank.PAIR || rank == HandRank.NON)
                    this.playsNb[9]++;
                else {
                    if (i < 8)
                        this.playsNb[i - 2]++; //compensate for the first two HandRank enum {NON, PAIR, ...
                    else
                        this.playsNb[i - 4]++; //compensate for the 3 types of FOAK in the enum
                }
            }
        }

        this.dealsNb++;
        this.currentBalance = playerBalance;

    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder("\nHand                Nb\n");
        sb.append("----------------------\n");
        for(int i = 0; i < handType.length; i++){
            sb.append(String.format("%s%S%d\n", handType[i], padding[i], playsNb[i]));

        }
        sb.append("----------------------\n");
        sb.append(String.format("Total                %d\n", this.dealsNb));
        sb.append("----------------------\n");
        double gainPercentage = (this.currentBalance/this.initialBalance)*100;
        sb.append(String.format("Credit        %.0f (%.1f%s)\n", this.currentBalance, gainPercentage, "%"));

        return sb.toString();
    }
}
