package videopoker.game;

import videopoker.utils.HandRank;

/**
* Class used for maintaining track of the players statistics.
* It registers the amount of different ranks a player has had
* during the game of videopoker and calculates it's gains accordingly.
*/
public class Scoreboard{

  private double initialBalance;
  private double currentBalance;
  private int dealsNb;
  private final static String[] handType = {"Jacks or Better","Two Pair","Three of a Kind","Straight","Flush","Full House", "Four of a Kind", "Straight Flush", "Royal Flush", "Others"}; //Others = Pairs ??
  private final static String[] padding = {"      ", "             ", "      ", "             ", "                ", "           ",  "       ", "       ", "          ", "               "};
  private int[] playsNb = new int[10];


  /**
  * Scoreboard constructor receives initial player balance for posterior
  * comparison and gain calculation. Initializes all other attributes of
  * interest.
  * @param initialBalance players initial balance.
  */
  public Scoreboard(int initialBalance){

    this.initialBalance = initialBalance;
    this.currentBalance = initialBalance;
    this.dealsNb = 0;
    for(int i = 0; i < this.playsNb.length; i++)
    this.playsNb[i] = 0;
  }

  /**
  * Updates this scoreboard with the results obtained from the last videopoker round.
  * Increments the @playsNb array  according to the player's hand rank. Saves the player's
  * current balance.
  *
  * @param rank final rank of the player's hand.
  * @param playerBalance player's balance after payout.
  */
  public void receiveRoundInfo(HandRank rank, double playerBalance) {
    HandRank[] plays = HandRank.values();
    for (int i = 0; i < plays.length; i++) {
      if (rank == plays[i]) {
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

    /**
    * Produces a formated string with the scoraboar table and calculates
    * the player's gains accoring to it's initial balance.
    * @return string representation of the scoreboard.
    */
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
      double gainPercentage = ((this.currentBalance - this.initialBalance)/this.initialBalance)*100;
      sb.append(String.format("Credit        %.0f (%.1f%s)\n", this.currentBalance, gainPercentage, "%"));

      return sb.toString();
    }

    public double getInitialBalance() {
      return this.initialBalance;
    }

    public double getCurrentBalance() {
      return this.currentBalance;
    }

    public int getDealsNb() {
      return this.dealsNb;
    }

    public int[] getPlaysNb() {
      return this.playsNb;
    }
  }
