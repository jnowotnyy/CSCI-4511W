public class Euchre implements EuchreUpdateable{
    private Gamestates gameState;
    public Euchre(Event state){
        this.state = gameState;
        gameState.registerForUpdates(this);
    }

    
}
