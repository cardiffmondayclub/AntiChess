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

   //Checks if the piece can capture. Not sure how best to implement this.
   //The prototype has the board passed to the method in case that is useful
   //but feel free to rewrite.
   public boolean isCapturePossible(Board board) {
      for (int col = 0; col < 8; col++) {
         for (int row = 0; row < 8; row++) {
            if (board.isMoveValid(colour, new Move(xPosition, yPosition, col, row))) {
               if (board.isMoveCapture(new Move(xPosition, yPosition, col, row))) {
                  return true;
               }
            }
         }
      }
      return false;
   }
}
