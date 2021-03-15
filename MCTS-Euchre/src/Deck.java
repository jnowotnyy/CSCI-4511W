import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
    private PlayingCard[] deck;
    private int card;
    private final int DECK_SIZE = 24;

    //new deck (unshuffled)
    public Deck(){
        deck = new PlayingCard[DECK_SIZE];
        int count = 0;
        for (CardSuit suit :CardSuit.values()){
            deck[count] = new PlayingCard(suit,1);
            count++;
            for(int i = 9; i <=13; i++){
                deck[count] = new PlayingCard(suit,i);
                count++;
            }
        }
    }

    //make a copy of this deck (could be useful so why not)
    public Deck(Deck original){
        this.card = original.card;
        this.deck = new PlayingCard[DECK_SIZE];
        for(int i = 0; i < deck.length; i++){
            this.deck[i] = original.deck[i];
        }
    }
    
    public Card draw() throws Exception {
        
    }
    
    public Card[] draw(int n) throws Exception {
        
    }
    
    public Card getCard(int index) {
        
    }
    
    public void shuffle() {
        
    }
    
    private Card[] shuffle(Card[] deck) {
        
    }
}
