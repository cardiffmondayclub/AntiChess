package antichess;

public class Main {

   public static void main(String[] args) {
      int[] whiteValues = {1,3,3,5,9,2};
      int[] blackValues = {1,3,3,5,9,2};
       
      AIOptions whiteOptions = new AIOptions(whiteValues);
      AIOptions blackOptions = new AIOptions(blackValues);
       
      Game currentGame = new Game(Definitions.AI_PLAYER, whiteOptions, Definitions.AI_PLAYER, blackOptions);
      currentGame.runGame();
   }
}
