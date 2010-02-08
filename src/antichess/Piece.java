package antichess;

public abstract class Piece {
   private int xPosition;
   private int yPosition;
   private char colour;

   public Piece(int posX, int posY, char newColour) {
      xPosition = posX;
      yPosition = posY;
      colour = newColour;
   }

   public char pieceColour() {
      return colour;
   }

   public boolean isPlayersPiece(char playerColour) {
      return playerColour == colour;
   }

   public abstract boolean isMoveValid(Board board, int newX, int newY);
   public abstract boolean isCapturePossible(Board board);
}
