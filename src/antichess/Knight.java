package antichess;

public class Knight extends Piece {
   public Knight(int posX, int posY, char newColour) {
      super(posX, posY, newColour);
   }

   public char getAppearance() {
      if (this.colour == 'b') {
         return 'n';
      } else {
         return 'N';
      }
   }

   public boolean isMoveValid(Board board, int newX, int newY) {
       int x = newX;
       int y = newY;
       // use xPosition and yPosition

       int xDiff = Math.abs(x - newX);  // number of squares moved in x
       int yDiff = Math.abs(y - newY);  // number of squares moved in y

       /* if number of squares moved in x and y totals 3, and squares 
          moved in x direc is either 1 or 2, then return true */
       if ( (xDiff + yDiff == 3) && ((xDiff == 1) || (xDiff == 2)) )
       {
           return true;
       }
       else
       {
           return false;
       }

   }

   public boolean isCapturePossible(Board board) {
      return true;
   }
}
