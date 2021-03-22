public class Hand {
    public PlayingCard hand;
    private final int HAND_SIZE = 5;
    private int position;

    public Hand(){
        hand = new PlayingCard[HAND_SIZE];
        pos = 0;
    }

    public void addToHand(PlayingCard card){
        if (pos >= 4){
            System.out.println("whY aRe wE hERe?")
            throw new IllegalStateException("too many cards in hand");
        } else {
            hand[pos] = card;
            pos++;
        }
    }

    public void addNToHand(PlayingCard[] cards, int n){
        for(int i = 0; i < n; i++){
            addToHand(cards[i]);
        }
    }

    public void removeFromHand(PlayingCard card){
        for(int i = 0; i < HAND_SIZE; i++){
            if(hand[i] == card){
                hand[i] = NULL;
                break;
            }
        }
    }

    public Hand peek(){ //peek at hand
        return hand;
    }

    public PlayingCard peek(int card){ //peek at hand
        return hand[card];
    }

    public void reset(){
        hand = new PlayingCard[HAND_SIZE];
        pos = 0;
    }
}