package videopoker.cards;

//review visibilidades
public class Card implements Comparable<Card>{

  private int value; //2 ... 14, A=14
  private char suite; //H S J D

  public Card(int value, char suite){
    this.value = value;
    this.suite = suite;
  }

  public char getSuit(){
    return this.suite;
  }

  public int getValue(){
    return this.value;
  }

  @Override
  public int compareTo(Card other){
    if (this.getValue() > other.getValue())
				return 1;  //other smaller
		else if (this.getValue() < other.getValue())
				return -1; //other greater
		else
				return 0; // same value

  }

  @Override
  public String toString(){

    String aux = "";

    switch(this.value){
      case 14:
            aux = aux.concat("A");
            break;
      case 11:
            aux = aux.concat("J");
            break;
      case 12:
            aux = aux.concat("Q");
            break;
      case 13 :
            aux = aux.concat("K");
            break;
      default:
            aux = aux.concat(Integer.toString(this.value));
            break;
    }

    return aux.concat(String.valueOf(this.suite));
  }

  @Override
  public boolean equals(Object obj){
    if(this == obj)
      return true;
    if(obj == null)
      return false;
    if(!(obj instanceof Card))
      return false;
    Card aux = (Card) obj;
    if(this.value != aux.value)
      return false;
    if(this.suite != aux.suite)
      return false;

    return true;
  }

//check if it is correct
  @Override
  public int hashCode(){
    final int prime = 31;
		int result = 1;
		result = prime * result + this.value + this.suite;
		return result;
  }

}
