public class Euchre implements EuchreUpdateable{
    private Gamestates gameState;
    public Euchre(Event state){
        this.state = gameState;
        gameState.registerForUpdates(this);
    }
    public static void main(String[] args) throws Exception {

    }
}
