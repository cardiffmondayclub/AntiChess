package antichess;

import java.util.ArrayList;

public class AIPlayer extends Player {

   private AIBoard currentBoard;
   final int PAWN_VALUE = 1;
   final int KNIGHT_VALUE = 2;
   final int BISHOP_VALUE = 3;
   final int ROOK_VALUE = 4;
   final int QUEEN_VALUE = 5;
   final int KING_VALUE = 6;

   final int MIN = 0;
   final int MAX = 1;

   public AIPlayer(int colour) {
      super(colour);
      int[] evaluationValues = {PAWN_VALUE, KNIGHT_VALUE, BISHOP_VALUE, ROOK_VALUE, QUEEN_VALUE, KING_VALUE};
      currentBoard = new AIBoard(colour, evaluationValues);
   }

   @Override
   public Move getMove() {
      //do a minimax search and find the best move
      Move move = new Move(0, 0, 0, 0);

      //make the move
      currentBoard.makeMove(move);

      //return the move to the game class
      return move;

   }

   public void sendMove(Move move) {
      currentBoard.makeMove(move);
   }

   public void miniMaxStart(int maxDepth) {
      miniMax(0, maxDepth, MAX);
   }

   public int miniMax(int currentDepth, int maxDepth, int searchType) {

      if (currentDepth == maxDepth) {
         //If this is the bottom layer
         return currentBoard.staticEval();
      } else {
         //Get the list of move (captures if one is possible, valid moves otherwise)
         ArrayList<Move> moveList;
         if (currentBoard.validCaptures.size() > 0) {
            moveList = currentBoard.validCaptures;
         } else {
            moveList = currentBoard.validMoves;
         }

         //Iterate through the list, make each move, find the max of that position
         //at the end return the minimum score and update some list of moves
         //Might need something to check for situations where there are no possible moves
         //(someone has won/some has lost/can hapen in stalemate)

         int score = 0;
         //switch based on min or max search
         switch (searchType) {
            case MIN:
               score = Integer.MAX_VALUE;
               for (Move move : moveList) {
                  currentBoard.makeMove(move);
                  score = Math.min(score, miniMax(currentDepth - 1, maxDepth, MAX));
                  currentBoard.undoMove();
               }
               break;
            case MAX:
               score = Integer.MIN_VALUE;
               for (Move move : moveList) {
                  currentBoard.makeMove(move);
                  score = Math.max(score, miniMax(currentDepth - 1, maxDepth, MIN));
                  currentBoard.undoMove();
               }
               break;
         }
         //end switch

         return score;
      }
   }
}
