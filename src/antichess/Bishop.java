package antichess;

public class Bishop extends Piece {
   public Bishop(int posX, int posY, char newColour) {
      super(posX, posY, newColour);
   }

   public char getAppearance() {
      if (this.colour == 'b') {
         return 'b';
      } else {
         return 'B';
      }
   }

   public boolean isMoveValid(Board board, Move move) {
       int newX = move.newX;
       int newY = move.newY;
       int oldX = move.oldX;
       int oldY = move.oldY;

       int xDiff = Math.abs(oldX - newX);  // number of squares moved in x
       int yDiff = Math.abs(oldY - newY);  // number of squares moved in y

       /* If the path is clear and the squares moved in x direction equals
        * the squares moved in y direction, then the move is valid */

       if( xDiff == yDiff && board.isPathClear(move) )
       {
           return true;
       }
       else
       {
           return false;
       }


      
   }

//   public boolean isCapturePossible(Board board) {
//      return true;
//   }
}
