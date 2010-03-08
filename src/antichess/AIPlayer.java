package antichess;

public class AIPlayer extends Player {

   private AIBoard currentBoard;

   final int PAWN_VALUE = 1;
   final int KNIGHT_VALUE = 2;
   final int BISHOP_VALUE = 3;
   final int ROOK_VALUE = 4;
   final int QUEEN_VALUE = 5;
   final int KING_VALUE = 6;

   public AIPlayer(int colour) {
      super(colour);
      int[] evaluationValues = {PAWN_VALUE, KNIGHT_VALUE, BISHOP_VALUE
              , ROOK_VALUE, QUEEN_VALUE, KING_VALUE};
      currentBoard = new AIBoard(colour, evaluationValues);
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
