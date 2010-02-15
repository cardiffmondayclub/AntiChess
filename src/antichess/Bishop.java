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
      return true;
   }

   public boolean isCapturePossible(Board board) {
      return true;
   }
}
