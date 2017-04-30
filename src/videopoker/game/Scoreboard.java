package videopoker.game;

//implement on player??? need to rcv actual balance for N13
public class Scoreboard{

  //pass to final or static??
  private int initialBalance;
  private int dealsNb;

  int[] playsNb = new int[10];

  //struct for score table?? to do
  private final static String[] handType = {"Other","Jacks or Better","Two Pair","Three of a Kind","Straight","Flush","Full house",
  "Four of a Kind", "Straight Flush", "Royal Flush"};

  Scoreboard(int initialBalance){
    this.initialBalance = initialBalance;
    this.dealsNb = 0;
    for(int i=0;i< this.playsNb.length;i++)
      this.playsNb[i] = 0;
  }

  //recv other type hand? String?
  public void rcvRoundInfo(int rank){
    this.dealsNb++;
    // Diferent types of Four's of a kind, meter uma estrutura mapeada aqui to do..
    switch(rank){
      case 8:
        rank = 7;
        break;
      case 9:
        rank = 7;
        break;
      case 10:
        rank = 8;
        break;
      case 11:
        rank = 9:
        break;
      default:
        break;
    }
    this.playsNb[rank]++;
  }

  @Override
  public String toString(){
    String ret = "Hand    Nb\n";
    for(int i=0;i<10;i++){
      ret += handType[i+1] +  "    " + this.playsNb[i+1] +"\n";
      if(i == 10)
        ret += handType[0] + "    " + this.playsNb[0] + "\n";
    }
    ret += "Total    " + this.playsNb + "\n";
    //actual balance e necessario aqui
    ret += "Credit    " + "("+ ((this.initialBalance) * 100) +"%)\n";
    return ret;
  }
}
