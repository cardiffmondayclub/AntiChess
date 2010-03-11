package antichess;

import java.util.ArrayList;

public class Queen extends Piece {

   public Queen(int posX, int posY, int newColour) {
      super(posX, posY, newColour, "queen");
   }

   public boolean isMoveValid(Board board, Move move) {
      int newX = move.newX;
      int newY = move.newY;
      int oldX = move.oldX;
      int oldY = move.oldY;

      int xDiff = Math.abs(oldX - newX);  // number of squares moved in x
      int yDiff = Math.abs(oldY - newY);  // number of squares moved in y

      if (board.isPathClear(move) && (xDiff == yDiff || xDiff == 0 || yDiff == 0)) {
         return true;
      } else {
         return false;
      }

   }

   public String getPieceName() {
      return "queen";
   }

   public int getPieceType() {
      return Definitions.QUEEN;
   }

	public void generateMoves(Piece[][] squares, ArrayList<Move> validMoves, ArrayList<Move> validCaptures) {
		//do stuff
	}
}
