package antichess;

public abstract class Piece {

   protected int xPosition;
   protected int yPosition;
   protected char colour;

   public Piece(int posX, int posY, char newColour) {
      xPosition = posX;
      yPosition = posY;
      colour = newColour;
   }

   public void setPosition(int posX, int posY) {
      xPosition = posX;
      yPosition = posY;
   }

   public char pieceColour() {
      //Pretty self explanatory.
      return colour;
   }

   public boolean isPlayersPiece(char playerColour) {
      //Pretty self explanatory.
      return playerColour == colour;
   }

   //The idea is to return a character that can be used by the current
   //drawBoard() function. For example the white king might return 'K' and the
   //black king return 'k'.
   public abstract char getAppearance();

   //Checks if a move is valid. I'm hoping the Board.isPathClear() function
   //could be used here.
   public abstract boolean isMoveValid(Board board, Move move);

}
