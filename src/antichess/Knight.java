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
      return true;
   }

   public boolean isCapturePossible(Board board) {
      return true;
   }
}
