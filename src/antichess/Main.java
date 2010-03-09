package antichess;

public class Main {

   public static void main(String[] args) {
      Game currentGame = new Game(Definitions.HUMAN_PLAYER, Definitions.AI_PLAYER);
      currentGame.runGame();
   }
}
