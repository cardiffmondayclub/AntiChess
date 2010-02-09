package antichess;

public class King extends Piece {
   public King(int posX, int posY, char newColour) {
      super(posX, posY, newColour);
   }

   public char getAppearance() {
      if (this.colour == 'b') {
         return 'k';
      } else {
         return 'K';
      }
   }

   public boolean isMoveValid(Board board, int newX, int newY) {
      return true;
   }

   public boolean isCapturePossible(Board board) {
      return true;
   }
}
