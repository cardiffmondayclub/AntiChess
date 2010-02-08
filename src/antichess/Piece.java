package antichess;


public abstract class Piece {
   private int xPosition;
   private int yPosition;

   public Piece(int posX, int posY) {
      xPosition = posX;
      yPosition = posY;
   }

   public abstract boolean isMoveValid(int newX, int newY);
   public boolean isMoveCapture(int newX, int newY) {
      return false;
   }
   public abstract boolean isPlayersPiece(int posX, int posY);
   public abstract int[][] possibleMoves();
}
