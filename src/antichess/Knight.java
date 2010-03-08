package antichess;

public class Knight extends Piece {

   public Knight(int posX, int posY, int newColour) {
      super(posX, posY, newColour, "knight");
   }

   public char getAppearance() {
      if (this.colour == 'b') {
         return 'n';
      } else {
         return 'N';
      }
   }

   public boolean isMoveValid(Board board, Move move) {
      int newX = move.newX;
      int newY = move.newY;
      int oldX = move.oldX;
      int oldY = move.oldY;

      int xDiff = Math.abs(oldX - newX);  // number of squares moved in x
      int yDiff = Math.abs(oldY - newY);  // number of squares moved in y

      /* if number of squares moved in x and y totals 3, and squares
      moved in x direc is either 1 or 2, then return true */
      if ((xDiff + yDiff == 3) && ((xDiff == 1) || (xDiff == 2))) {
         return true;
      } else {
         return false;
      }

   }

      public String getPieceName() {
      return "knight";
   }
}
