package videopoker.cards;

/**
* Card class represents a card and implements it's related methods.
* Instances of this class represent a card with a unique value/suit combination.
*/
public class Card implements Comparable<Card>{

  private int value; //1 ... 14, A=1
  private char suite; //H S J D

  /**
  * Create an instance of the class Card
  * @param  value         value of the card
  * @param  suite         suite of the card
  */
  public Card(int value, char suite){
    this.value = value;
    this.suite = suite;
  }

  /**
  * Method to retrieve this Card instance's suit.
  * @return card's suit
  */
  public char getSuit(){
    return this.suite;
  }

  /**
  * Method to retrieve this Card instance's value.
  * @return card's value
  */
  public int getValue(){
    return this.value;
  }

  /**
  * Method to verify if this Card instance is a high card {J, Q, K, A}.
  * @return true if the card is a high card, false otherwise
  */
  public boolean isHighCard(){ return (this.value == 1 || this.value > 10) ? true : false; }

  /**
  * Method to compare the values between two cards
  * @param  other         Card to compare this card
  * @return the result of the comparison 0 same value, 1 other smaller, -1 other greater
  */
  @Override
  public int compareTo(Card other){
    if (this.getValue() > other.getValue())
    return 1;  //other smaller
    else if (this.getValue() < other.getValue())
    return -1; //other greater
    else
    return 0; // same value

  }

  /**
  * Method to put the description of the Card to a String
  * @return a String with the description of the card
  */
  @Override
  public String toString(){

    String aux = "";

    switch(this.value){
      case 1:
      aux = aux.concat("A");
      break;
      case 10:
      aux = aux.concat("T");
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
}
