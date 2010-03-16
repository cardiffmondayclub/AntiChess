package antichess;

public class Main {

   public static void main(String[] args) {
      AIOptions whiteOptions = new AIOptions();
      AIOptions blackOptions = new AIOptions();

       
      Game currentGame = new Game(Definitions.AI_PLAYER, whiteOptions, Definitions.AI_PLAYER, blackOptions);
      currentGame.runGame();
   }
}
