package antichess;

public class Board {

   private Piece[][] squares;

   public Board() {
      //initialise board
      squares = new Piece[8][8];
      //squares[0][0] = Rook(0,0,'w');
      //squares[1][0] = Knight(0,1,'w');
      //squares[2][0] = Bishop(0,2,'w');
      //squares[3][0] = King(0,3,'w');
      //squares[4][0] = Queen(0,4,'w');
      //squares[5][0] = Bishop(0,5,'w');
      //squares[6][0] = Knight(0,6,'w');
      //suqares[7][0] = Rook(0,7,'w');
      //for (int i = 0; i < 8; i++) {
      //    squares[i][1] = Pawn(i,1,'w');
      //    squares[i][6] = Pawn(i,1,'b');
      //}
      //squares[0][7] = Rook(0,0,'b');
      //squares[1][7] = Knight(0,1,'b');
      //squares[2][7] = Bishop(0,2,'b');
      //squares[3][7] = King(0,3,'b');
      //squares[4][7] = Queen(0,4,'b');
      //squares[5][7] = Bishop(0,5,'b');
      //squares[6][7] = Knight(0,6,'b');
      //suqares[7][7] = Rook(0,7,'b');
   }

   public void drawBoard() {
      //This shouldn't be too bad
      //I was thinking we could use upper/lower case to distinguish black/white
   }

   public boolean isPathClear(Move move) {
      //Not sure where this function should be, I think it might be useful
      return true;
   }

   public boolean isMoveValid(char colour, Move move) {
      //check if the piece exists
      if (squares[move.oldX][move.oldY] == null) {
         return false;
      }

      //check if the piece belongs to the current player
      if (!squares[move.oldX][move.oldY].isPlayersPiece(colour)) {
         return false;
      }

      //check if the move is valid
      return squares[move.oldX][move.oldY].isMoveValid(this, move.newX, move.newY);
   }

   public boolean isMoveCapture(Move move) {
      //If there is no piece in the new square then it can't be a capture
      //otherwise it is capture (or not valid for some reason which should be
      //dealt with by isMoveValid()
      return (squares[move.newX][move.newY] == null);
   }

   public void makeMove(Move move) {
      squares[move.newX][move.newY] = squares[move.oldX][move.oldY];
      squares[move.oldX][move.oldY] = null;
   }

   public boolean isCapturePossible(char playerColour) {

      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            if (squares[i][j].isPlayersPiece(playerColour)) {
               if (squares[i][j].isCapturePossible(this)) {
                  return true;
               }
            }
         }
      }
      
      return false;
   }

   public char isWon() {
      int whiteCount = 0;
      int blackCount = 0;
      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            if (squares[i][j] == null) {
               if (squares[i][j].pieceColour() == 'w') {
                  whiteCount++;
               } else {
                  blackCount++;
               }
            }
         }
      }
      if (blackCount == 0 ) {
         return 'b';
      }
      else if (whiteCount == 0) {
         return 'w';
      } else {
         return ' ';
      }
   }

   public boolean isStaleMate() {
      //Apart from the whole bishops on different colour squares I'm not sure
      //what needs checking.
      return false;
   }
}
