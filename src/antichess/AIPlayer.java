package antichess;

public class AIPlayer extends Player {

   private AIBoard currentBoard;

   public AIPlayer(int colour) {
      super(colour);
      currentBoard = new AIBoard();
   }

   @Override
   public Move getMove() {
      //do a minimax search and find the best move
      Move move = new Move(0,0,0,0);
      
      //make the move
      currentBoard.makeMove(move);

      //return the move to the game class
      return move;

   }

   public void sendMove(Move move) {
      currentBoard.makeMove(move);
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
