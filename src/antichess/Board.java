package antichess;

import java.awt.*;
import java.awt.geom.*;
import java.awt.Font.*;

public class Board extends Frame {

   private Piece[][] squares;
   private double squareSize;

   public Board(double FRAME_SIZE) {
       //MA - set square size
      squareSize = FRAME_SIZE / 10;
       //Initialises the board.
      squares = new Piece[8][8];
      squares[0][0] = new Rook(0, 0, 'w');
      squares[1][0] = new Knight(1, 0, 'w');
      squares[2][0] = new Bishop(2, 0, 'w');
      squares[3][0] = new Queen(3, 0, 'w');
      squares[4][0] = new King(4, 0, 'w');
      squares[5][0] = new Bishop(5, 0, 'w');
      squares[6][0] = new Knight(6, 0, 'w');
      squares[7][0] = new Rook(7, 0, 'w');
      for (int i = 0; i < 8; i++) {
         squares[i][1] = new Pawn(i, 1, 'w');
         squares[i][6] = new Pawn(i, 6, 'b');
      }
      for (int i = 0; i < 8; i++) {
         for (int j = 2; i < 6; i++) {
            squares[i][j] = null;
         }
      }
      squares[0][7] = new Rook(0, 7, 'b');
      squares[1][7] = new Knight(1, 7, 'b');
      squares[2][7] = new Bishop(2, 7, 'b');
      squares[3][7] = new Queen(3, 7, 'b');
      squares[4][7] = new King(4, 7, 'b');
      squares[5][7] = new Bishop(5, 7, 'b');
      squares[6][7] = new Knight(6, 7, 'b');
      squares[7][7] = new Rook(7, 7, 'b');
   }

   public void drawBoard() {
      for (int row = 7; row >= 0; row--) {
         System.out.format("%d ", row + 1);
         for (int col = 0; col < 8; col++) {
            if (squares[col][row] != null) {
               System.out.print(squares[col][row].getAppearance() + " ");
            } else {
               System.out.print("  ");
            }
         }
         System.out.println();
      }
      System.out.println("  a b c d e f g h ");
      System.out.println();
   }

    // MA - this paint method draws the board in a frame and uses the getAppearance method to show
    // where each piece is on the board as a red character for now so it can be seen on both the
    // white and black squares - TODO - change appearance of each piece to an image (possibly)
    
    @Override
    public void paint( Graphics g )
    {
        Graphics2D ga = (Graphics2D)g;       
        for ( int i = 7; i >= 0; i-- )
        {
            for ( int j = 7; j >= 0; j-- )
            {
                double leftEdge = squareSize * (i + 1);
                double topEdge = squareSize * (8 - j);
                Shape square = new Rectangle2D.Double( leftEdge, topEdge, squareSize, squareSize );

                if ( (i + j) % 2 == 1 )     //(Changed) - I may have the white and black squares in the wrong places but they are swapped by changing the 0 here to a 1
                {
                    ga.setColor( Color.white );
                    ga.fill( square );                    
                }
                else
                {
                    ga.setPaint( Color.black );
                    ga.fill( square );                    
                }
                if (squares[i][j] != null)
                {
                    char[] appearance = { squares[i][j].getAppearance() };
                    //MA - Change font size and colour depending on piece colour
                    if (squares[i][j].colour == 'w')
                    {                        
                        ga.setColor(Color.red);
                        ga.setFont( new Font("ComicSansMS", Font.BOLD, 24) );                        
                    }
                    else
                    {
                        ga.setColor(Color.blue);
                        ga.setFont( new Font("ComicSansMS", Font.ITALIC, 24) );                        
                    }
                    ga.drawChars(appearance, 0, 1, (int)leftEdge + 25, (int)topEdge + 35);
                }
            }
        }
    }

   public boolean isPathClear(Move move) {
      int xDelta = move.newX - move.oldX;
      int yDelta = move.newY - move.oldY;
      int absXDelta = Math.abs(xDelta);
      int absYDelta = Math.abs(yDelta);
      int steps = Math.max(absXDelta, absYDelta);

      if (xDelta != 0 && yDelta != 0 && absXDelta != absYDelta) {
         //Not a valid path
         return false;
      } else {
         //Path is at least straight or diagonal
         int xIncrement = (move.newX - move.oldX) / steps;
         int yIncrement = (move.newY - move.oldY) / steps;
         for (int step = 1; step < steps ; step++) {
            if (squares[move.oldX + step * xIncrement][move.oldY + step * yIncrement] != null) {
               return false;
            }
         }
      }
      return true;
   }

   public boolean isMoveValid(char playerColour, Move move) {
      //check destination isn't the same as origin
      if (move.newX == move.oldX && move.newY == move.oldY) {
         return false;
      }

      //check if the piece exists
      if (squares[move.oldX][move.oldY] == null) {
         return false;
      }

      //check if the piece belongs to the current player
      if (!squares[move.oldX][move.oldY].isPlayersPiece(playerColour)) {
         return false;
      }

      //check the destination piece (if any) doesn't belong to the player
      if (squares[move.newX][move.newY] != null &&
              squares[move.newX][move.newY].isPlayersPiece(playerColour)) {
         return false;
      }

      //check if the move is valid
      return squares[move.oldX][move.oldY].isMoveValid(this, move);
   }

   public boolean isMoveCapture(Move move) {
      //If there is no piece in the new square then it can't be a capture
      //otherwise it is capture (or not valid for some reason which should be
      //dealt with by isMoveValid() ).
      return (squares[move.newX][move.newY] != null);
   }

   public void makeMove(Move move) {
      /* Assuming that everything has been checked by isMoveValid() and
       * isMoveCapture() this function simply replaces the contents of the
       * new square with the contents of the old square and wipes the old
       * square
       */
      squares[move.newX][move.newY] = squares[move.oldX][move.oldY];
      squares[move.oldX][move.oldY] = null;

      //Updates the Piece instances position values
      squares[move.newX][move.newY].setPosition(move.newX, move.newY);
   }

   public boolean isCapturePossible(char playerColour) {
      //TO DO
      //Bit of a placeholder.
      //Currently loops over all squares, checks if it belongs to the player
      //and checks if so checks if the piece can capture. Obviously if any piece
      //can capture the answer is true so it can return straight away.

      for (int col = 0; col < 8; col++) {
         for (int row = 0; row < 8; row++) {
            if (squares[col][row] != null && squares[col][row].isPlayersPiece(playerColour)) {
               if (squares[col][row].isCapturePossible(this)) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   public char isWon() {
      //Horribly crude function to check if there are zero black or zero white
      //pieces. Returns ' ' if both players still have pieces, 'w' if white has
      //no pieces and 'b' if black has no pieces.
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
      if (blackCount == 0) {
         return 'b';
      } else if (whiteCount == 0) {
         return 'w';
      } else {
         return ' ';
      }
   }

   public boolean isStaleMate() {
      //TO DO
      //Apart from the whole bishops on different colour squares I'm not sure
      //what needs checking.

      /* (JC) - I think the best way to implement this is to handle each
       * different scenario of remaining pieces that would cause a stalemate
       * one by one.  For instance:
       * 1. Each player has one bishop remaining and the bishop is sitting on
       *    a square that is different in colour than the square occupied by the
       *    opposing bishop.
       *
       *   --- we can continue adding scenarios to this list, and then code each
       *   one as an if statement.
       */
      return false;
   }
}
