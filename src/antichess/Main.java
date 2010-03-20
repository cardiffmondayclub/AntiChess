package antichess;

public class Main {

   public static void main(String[] args) {
      int[] whiteValues = {2,4,4,6,10,3};
      int[] blackValues = {1,3,3,5,9,2};
       
      AIOptions whiteOptions = new AIOptions(whiteValues);
      AIOptions blackOptions = new AIOptions(blackValues);
       
      Game currentGame = new Game(Definitions.HUMAN_PLAYER, whiteOptions, Definitions.HUMAN_PLAYER, blackOptions);
      currentGame.runGame();
   }
}
