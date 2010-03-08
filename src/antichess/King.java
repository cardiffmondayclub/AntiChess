package antichess;

public class King extends Piece {

   public King(int posX, int posY, int newColour) {
      super(posX, posY, newColour, "king");
   }
   
   public boolean isMoveValid(Board board, Move move) {
      int newX = move.newX;
      int newY = move.newY;
      int oldX = move.oldX;
      int oldY = move.oldY;

      int xDiff = Math.abs(oldX - newX);  // number of squares moved in x
      int yDiff = Math.abs(oldY - newY);  // number of squares moved in y

      if (xDiff > 1 || yDiff > 1) {
         return false;
      } else {
         return true;
      }
   }

   public String getPieceName() {
      return "king";
   }
}
