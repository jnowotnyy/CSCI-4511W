//will represent the cards in a hand of euchre (9-A)
public class PlayingCard implements Comparable<Object>{
    private CardSuit cardSuit;
    private int cardNum;

    public PlayingCard (CardSuit c, int n){ //card constructor
        cardSuit = c;
        cardNum = n;
    }

    public CardSuit getCardSuit(){ //returns suit
        return cardSuit;
    }

    public int getCardNum(){ //returns card number
        return cardNum;
    }

    public String toString(){ //makes card string (nice for printing)
        String card = (cardNum == 1) ? "A" : (cardNum == 11) ? "J" : (cardNum == 12) ? "Q" : (cardNum == 13) ? "K" : "";
        if (card == ""){card += cardNum;}
        card += cardSuit.toString();
        return card;
    }
    //lets us know for our purposes if a card is greater than or less than another card
    // 1 is greater than
    // -1 is less than
    // 0 if they are the same (should never happen)
    public int compareTo(Object object){
        PlayingCard other = (PlayingCard) object;
        int val = (cardNum > other.cardNum) ? 1 : (cardNum < other.cardNum) ? -1 : (cardSuit.ordinal() > other.cardSuit.ordinal()) ? 1 : (cardSuit.ordinal() < other.cardSuit.ordinal()) ? -1 : 0;
        return val;
    }

}
