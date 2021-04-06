public class State{
	private CardSuit trumpSuit;
    private CardSuit sameColorSuit;
	private Deck deck;
	private Gamestate gamestate;
    private int numCardsPlayed;
    private int dealer = 0;
    private int defender = 1;
	private int playerToMove;
	private int dealer;
	private Player[] players;
    private PlayingCard[] played;
	
	//play information
	private int trickNum;
	private boolean trickover;
	private boolean handOver;
	
	private Collection<EuchreUpdateable> toUpdate;

    public class State(){
        players = new Player[2];
		players[0] = new Player();
		players[1] = new Player();

        played = new PlayingCard[2];
        played[0] = new PlayingCard();
        played[1] = new PlayingCard();
		
		playerToMove = -1; //represents game to move (deal)
		stage = Gamestate.DEAL;
		deck = new Deck();
		handOver = true;
        numCardsPlayed = 0;
		
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

    public void getSameColorSuit(){
        if(trumpSuit == CardSuit.Spades){
            sameColorSuit == CardSuit.Clubs;
        } else if(trumpSuit == CardSuit.Clubs){
            sameColorSuit == CardSuit.Spades;
        } else if(trumpSuit == CardSuit.Hearts){
            sameColorSuit == CardSuit.Diamonds;
        } else{
            sameColorSuit = CardSuit.Hearts;
        }
    }

    public int findTrickWinner(PlayingCard dealer, PlayingCard defender){
        getSameColorSuit();
        if(dealer.getCardSuit() == trumpSuit && dealer.getCardNum() == 11){
            return dealer;
        }
        else if(defender.getCardSuit() == trumpSuit && defender.getCardNum() == 11){
            return defender;
        } 
        else if(dealer.getCardSuit() == sameColorSuit && dealer.getCardNum() == 11){
            return dealer;
        }
        else if(defender.getCardSuit() == sameColorSuit && defender.getCardNum() == 11){
            return defender;
        }
        else if(dealer.getCardSuit() == trumpSuit && defender.getCardSuit() == trumpSuit){
            if(dealer.getCardNum() == 1){
                return dealer;
            } else if(defender.getCardNum == 1){
                return defender;
            }
            int retval = dealer.compareTo(defender);
            if(retval == 1){
                return dealer;
            } else if (retval == -1){
                return defender;
            } else {
                throw new Exception("Cards are Same Value????")
            }
        } 
        else if(dealer.getCardSuit() == trumpSuit){
            return dealer;
        }
        else if(defender.getCardSuit() trumpSuit){
            return defender;
        }
        else{
            int retval = dealer.compareTo(defender);
            if(retval == 1){
                return dealer;
            } else if (retval == -1){
                return defender;
            } else {
                throw new Exception("Cards are Same Value????")
            }
        }
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

    public State doAction(PlayingCard action){
        if (getWinner() != -1) {
			throw new IllegalStateException("Game Over");
		}	
		int[] moves = getActions();
        switch (stage) {
		case DEAL:
            deck.shuffle();
            players[0].initHand(deck.draw(3));
            players[1].initHand(deck.draw(2));
            players[0].addNToHand(2);
            players[0].addNToHand(3);
            reportUpdate(Gamestate.DEAL, -1 ,0);
            playerToMove = (dealer + 1) % 2;
            stage = Gamestate.CUT;
            handOver = false;
            trickNum = 1;
            break;
        case CUT:
            if (action != 0) {
				throw new Exception("Illegal action");
			}
			PlayingCard cut = deck.draw();
            trumpSuit = cut.getCardSuit();
			reportUpdate(Gamestate.CUT, -1, 0);
            playerToMove = (dealer + 1) % 2;
			stage = Gamestate.PLAY;
			break;	
        case PLAY:
            if(playerToMove == dealer){
                trickover = false;
                played[dealer] = players[dealer].play(action);
                reportUpdate(Gamestate.Play, dealer, 0);
            } else if(playerToMove == defender){
                played[defender] = players[defender].play(action);
                int trickwinner = findTrickWinner(played[dealer], played[defender]);
                if(trickwinner = dealer){
                    players[dealer].winTrick();
                } else if (trickwinner = defender){
                    players[defender].winTrick();
                }
                trickNum++;
            }
            if(trickNum == 5){
                
            }
            if(trickover || handOver){
                stage = Gamestate.DEAL;
            }
            dealer = (dealer + 1) % 2;
            defender = (defender + 1) % 2; 
        }
    }

    public void registerForUpdates(EuchreUpdateable update) {
		toUpdate.add(update);
	}

    private void reportUpdate(Event type, int player, int points) {
		for (EuchreUpdateable updateable : toUpdate) {
			updateable.receiveUpdate(type, player, points);
		}
	}

    private class Player(){
        private Hand hand;
        private boolean[] played;
        private int gameScore;
        private int trickScore;
        int cardsToPlay;

        //constructor
        public Player(){
            this.gameScore = 0;
            this.trickScore = 0;
        }
        
        //initialize hand
        public void initHand(Hand hand){
            this.hand = hand;
            played = new boolean[5];
            cardsToPlay = 5;
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
            for(int i = 0; i < hand.size; i++){
                if(hand.peek(i) != NULL){
                    playable[cnt++] = hand.peek(i);
                }
            }
        }

        public boolean[] getPlayed(){
            return played;
        }

        public int getScore(){
            return gameScore;
        }

        public int getTricksWon(){
            return trickScore;
        }

        public void addToScore(int points){
            gameScore+= points;
        }

        public void winTrick(){
            trickScore++;
        }

        public void resetHand(){
            trickScore = 0;
            for(int i = 0; i<5; i++){
                hand[i] = null;
            }
        }

        public Hand getHand(){
            return hand.peek();
        }
    }
}