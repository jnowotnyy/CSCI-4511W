public class State{
	private CardSuit trumpSuit;
	private Deck deck;
	private Gamestates gamestate;
	private int playerToMove;
	private int dealer;
	private Player[] players;
	
	//play information
	private int trickNum;
	private boolean trickover;
	private boolean handOver;
	
	private Collection<EuchreUpdateable> toUpdate;

    public class State(){
        players = new Player[2];
		players[0] = new Player();
		players[1] = new Player();
		
		dealer = 0;
		playerToMove = -1; //represents game to move (deal)
		stage = Gamestates.DEAL;
		deck = new Deck();
		handOver = true;
		
		hand.reset();
		
		toUpdate = new ArrayList<CribbageUpdateable>();
    }

    public int getScore(int player){
        return players[player].getScore();
    }

    public int getPlayerToMove(){
        return playerToMove;
    }

    public int getDealer(){
        return dealer;
    }

    public int getWinner(){
        if(player[0].score() == 10){
            return 0;
        } else if (player[1].score() == 10){
            return 1;
        } else {
            return -1;
        }
    }

    public Deck getDeck(){
        return deck;
    }

    public void setDeck(Deck deck){
        this.deck = deck;
    }

    public PlayingCard getTrumpSuit(){
        return trumpSuit;
    }

    public Hand getFullHand(int player){
        return players[player].peek();
    }

    public int[] getPlayableCardIndexes(int player){
        return players[player].getPlayableCardIndexes();
    }

    public boolean[] getPlayed(int player) {
		return players[player].getPlayed();
	}

    public int[] getActions(){
		if (getWinner() != -1) {
			throw new IllegalStateException("Game Over");
		}	
		int[] actions;
		switch (gamestate) {
            case DEAL:
                actions = new int[1];
                actions[0] = 0;
                break;
            case CUT:
                actions = new int[1];
                actions[0] = 0;
                break;
            case PLAY:
                actions = players[playerToMove].getPlayableCardIndexes(playerToMove);
                break;
            default:
                throw new IllegalStateException("Illegal State");
        }
        return actions;
    }

    public State doAction(int action){
        if (getWinner() != -1) {
			throw new IllegalStateException("Game Over");
		}	
		int[] moves = getActions();
        switch (stage) {
		case DEAL:
            deck.shuffle();
            players[0].initHand(deck.draw(5));
            players[1].initHand(deck.draw(5));
            reportUpdate(Gamestates.DEAL, -1 ,0);
            playerToMove = (dealer + 1) % 2;
            gamestate = Gamestates.CUT;
            handOver = false;
            break;
        case CUT:
        case PLAY:
        }
    }

    public void registerForUpdates(EuchreUpdateable update) {
		toUpdate.add(update);
	}

    private void reportUpdate(Gamestates type, int player, int points) {
		for (EuchreUpdateable updateable : toUpdate) {
			updateable.receiveUpdate(type, player, points);
		}
	}

    private class Player(){
        private Hand hand;
        private boolean[] played;
        private int score;
        private int trickswon;
        int cardsToPlay;

        //constructor
        public Player(){
            this.score = 0;
        }
        
        //initialize hand
        public void initHand(Hand hand){
            this.hand = hand;
            played = new boolean[5];
            cardsToPlay = 5;
            trickswon = 0;
        }

        //play a card
        public void play(int card){
            Card lastPlayed = hand.peek(card);
            played[card] = true;
            cardsToPlay--;
            hand.removeFromHand(card);
            return lastPlayed;
        }

        public int[] getPlayableCardIndexes(int trickNum){
            int[] playable = new int[cardsToPlay];
            int cnt = 0;
            for(int i = 0; i < 5; i++){
                if(hand.peek(i) != NULL){
                    playable[cnt++] = hand.peek(i);
                }
            }
        }

        public boolean[] getPlayed(){
            return played;
        }

        public int getScore(){
            return score;
        }

        public int getTricksWon(){
            return trickswon;
        }

        public void addToScore(int points){
            score+= points;
        }

        public void newTrickWon(){
            trickswon++;
        }

        public Hand getHand(){
            return hand.peek();
        }
    }
}