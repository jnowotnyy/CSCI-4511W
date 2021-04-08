import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Euchre implements EuchreUpdateable{
    private Gamestates gameState;
    public Euchre(Event state){
        this.state = gameState;
        gameState.registerForUpdates(this);
    }
    public static void main(String[] args) throws Exception {
        int numGames = 1000;
        EuchrePlayer[] players = new EuchrePlayer[2];
        players[0] = new RandomPlayer();
        players[1] = new RandomPlayer();
        
        int totalMoves = 0;
        int[] wins = {0,0};
        ArrayList <Integer[]> scores = new ArrayList <Integer[]>();
        
        for(int i = 0; i< numGames; i++){
            State state = new State;
            Integer[] gameScores = {0,0};

            //game loop
            try{
                int playerToMove;
                while(state.getWinner() == -1){
                    playerToMove = state.getPlayerToMove();
                    if(playerToMove == -1){
                        state.doAction(0);
                    } else {
                        state.doAction(player[playerToMove].getMove(state));
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                break;
            }

            //records the game result
            int winner = state.getWinner();
            if(winner == 0){
                wins[0]++;
            } else if (winner == 1){
                wins[1]++;
            }
            gameScores[0] = state.getScore(0);
            gameScores[1] = state.getScore(1);
            scores.add(gameScores.clone());
        }
        //TODO: PRINT OUT RESULTS OF FOR LOOP
    }
}
