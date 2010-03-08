package antichess;

public class Rook extends Piece {

   public Rook(int posX, int posY, int newColour) {
      super(posX, posY, newColour, "rook");
   }

  public boolean isMoveValid(Board board, Move move) {
      int newX = move.newX;
      int newY = move.newY;
      int oldX = move.oldX;
      int oldY = move.oldY;

      int xDiff = Math.abs(oldX - newX);  // number of squares moved in x
      int yDiff = Math.abs(oldY - newY);  // number of squares moved in y

      if ((xDiff == 0 || yDiff == 0) && board.isPathClear(move)) {
         return true;
      } else {
         return false;
      }
   }

   public String getPieceName() {
      return "rook";
   }
}
