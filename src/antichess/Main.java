package antichess;

public class Main {

   public static void main(String[] args) {
      Game currentGame = new Game(Definitions.HUMAN_PLAYER, Definitions.HUMAN_PLAYER);
      currentGame.runGame();
   }
}
