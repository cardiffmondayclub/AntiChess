package antichess;

public class Rook extends Piece {
   public Rook(int posX, int posY, char newColour) {
      super(posX, posY, newColour);
   }

   public char getAppearance() {
      if (this.colour == 'b') {
         return 'r';
      } else {
         return 'R';
      }
   }

   public boolean isMoveValid(Board board, Move move) {
      return true;
   }

   public boolean isCapturePossible(Board board) {
      return true;
   }
}
