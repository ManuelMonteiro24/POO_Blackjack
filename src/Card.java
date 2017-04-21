package src;

//review visibilidades
public class Card implements Comparable<Card>{

  private int value; //1 ... 13
  private char suite; //H S J D

  Card(int value, char suite){
    this.value = value;
    this.suite = suite;
  }

  char getSuit(){
    return this.suite;
  }

  int getValue(){
    return this.value;
  }

  //verificar se é necessario
  @Override
  public int compareTo(Card other){
    if (this.getValue() > other.getValue())
				return 1;
		else if (this.getValue() < other.getValue())
				return -1;
		else
				return 0;

  }

  @Override
  public String toString(){

    String aux = "";

    switch(value){
      case 1: aux += "A" + this.suite;
            break;
      case 11: aux += "J" + this.suite;
            break;
      case 12: aux += "Q" + this.suite;
            break;
      case 13 : aux += "K" + this.suite;
            break;
      default:  aux += this.value + this.suite;
            break;
    }
    return aux;
  }

  @Override
  public boolean equals(Object obj){
    if (this==obj)
      return true;
    if (obj==null)
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
