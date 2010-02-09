package antichess;

public class Queen extends Piece {
   public Queen(int posX, int posY, char newColour) {
      super(posX, posY, newColour);
   }

   public char getAppearance() {
      if (this.colour == 'b') {
         return 'q';
      } else {
         return 'Q';
      }
   }

   public boolean isMoveValid(Board board, int newX, int newY) {
      return true;
   }

   public boolean isCapturePossible(Board board) {
      return true;
   }
}
