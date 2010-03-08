package antichess;

public class AIPlayer extends Player {

   private AIBoard currentBoard;

   public AIPlayer(int colour) {
      super(colour);
      currentBoard = new AIBoard();
   }

   @Override
   public Move getMove() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void sendMove(Move move) {
      //do stuff
   }

   public void miniMax() {
      //do stuff
   }

   public void min() {
      //do stuff
   }

   public void max() {
      //do stuff
   }
}
