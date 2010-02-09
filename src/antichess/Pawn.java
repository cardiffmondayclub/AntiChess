package antichess;

public class Pawn extends Piece {
   public Pawn(int posX, int posY, char newColour) {
      super(posX, posY, newColour);
   }
   
   public char getAppearance() {
      if (this.colour == 'b') {
         return 'p';
      } else {
         return 'P';
      }
   }

   public boolean isMoveValid(Board board, int newX, int newY) {
      return true;
   }

   public boolean isCapturePossible(Board board) {
      return true;
   }
}
