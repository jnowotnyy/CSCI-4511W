import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
    private PlayingCard[] deck;
    private int card; // current card
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
    
    public PlayingCard draw() throws Exception {
        if(card < DECK_SIZE){
             return deck[count++];
        } else {
             throw new Exception("ran out of cards :/");
        }
    }
    
    public PlayingCard[] draw(int n) throws Exception {
        if(card + n < DECK_SIZE){
            PlayingCard[] cards = new PlayingCard[n];
            for(i = 0; i<n; i++){
                cards[i] = deck[card++];
            }
            return cards;
        } else {
            throw new Exception("ran out of cards :/");
        }
    }
    
    public PlayingCard getCard(int index) {
        return deck[index];
    }
    
    public void shuffle() {
        deck = shuffle(deck);
        card = 0;
    }
    
    private PlayingCard[] shuffle(PlayingCard[] deck) {
        ArrayList<PlayingCard> CardList = new ArrayList<PlayingCard>(Arrays.asList(deck));
        Collections.shuffle(CardList);
        return (PlayingCard[]) CardList.toArray(new PlayingCard[DECK_SIZE]);
    }
}
