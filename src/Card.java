package src;

//review visibilidades
public class Card{

  private int value;
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

}
