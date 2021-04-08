import java.util.Random;

public class RandomPlayer implements EuchrePlayer{
    public int getMove(GameState gameState) throws Exception{
        int[] actions = gameState.getActions();
        Random rand = new Random();
        return actions[rand.nextInt(actions.length)];
    }
    public String toString(){
        return "Random Player";
    }
}