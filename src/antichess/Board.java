package antichess;

import java.awt.*;

import java.awt.Font.*;

import java.util.ArrayList;

public class Board extends Frame {

   protected Piece[][] squares;
   public ArrayList<Move> validMoves;
   public ArrayList<Move> validCaptures;
   private ArrayList<Piece> remainingPieces;
   // End-game cases
   public static final int LOCKED_STALEMATE = 1;
   public static final int DERIVED_STALEMATE = 2;
   public static final int WHITE_WINS = 3;
   public static final int BLACK_WINS = 4;
   // Ints that represent pieces
   private static final int PAWN = 1;
   private static final int KNIGHT = 2;
   private static final int BISHOP = 3;
   private static final int ROOK = 4;
   private static final int QUEEN = 5;
   private static final int KING = 6;

   public Board() {
      // initialise the array lists
      validMoves = new ArrayList<Move>();
      validCaptures = new ArrayList<Move>();
      remainingPieces = new ArrayList<Piece>();


      //Initialises the board.
      squares = new Piece[8][8];

      makePiece(0, 0, ROOK, Definitions.WHITE);
      makePiece(1, 0, KNIGHT, Definitions.WHITE);
      makePiece(2, 0, BISHOP, Definitions.WHITE);
      makePiece(3, 0, QUEEN, Definitions.WHITE);
      makePiece(4, 0, KING, Definitions.WHITE);
      makePiece(5, 0, BISHOP, Definitions.WHITE);
      makePiece(6, 0, KNIGHT, Definitions.WHITE);
      makePiece(7, 0, ROOK, Definitions.WHITE);

      for (int i = 0; i < 8; i++) {
         makePiece(i, 1, PAWN, Definitions.WHITE);
         makePiece(i, 6, PAWN, Definitions.BLACK);
      }
      for (int i = 0; i < 8; i++) {
         for (int j = 2; i < 6; i++) {
            squares[i][j] = null;
         }
      }

      makePiece(0, 7, ROOK, Definitions.BLACK);
      makePiece(1, 7, KNIGHT, Definitions.BLACK);
      makePiece(2, 7, BISHOP, Definitions.BLACK);
      makePiece(3, 7, QUEEN, Definitions.BLACK);
      makePiece(4, 7, KING, Definitions.BLACK);
      makePiece(5, 7, BISHOP, Definitions.BLACK);
      makePiece(6, 7, KNIGHT, Definitions.BLACK);
      makePiece(7, 7, ROOK, Definitions.BLACK);


   }

   public Board(int testNumber) {
      this();  // Inherit code from first Board constructor

      // wipe board
      for (int col = 0; col < 8; col++) {
         for (int row = 0; row < 8; row++) {
            squares[col][row] = null;
         }
      }
      boolean squaresSameColour = true;

      switch (testNumber) {
         case 1:  // Test for locked stalemate
            makePiece(0, 1, PAWN, Definitions.WHITE);
            makePiece(0, 3, PAWN, Definitions.BLACK);
            break;
         case 2:  // Test for lock for one player
            makePiece(0, 5, PAWN, Definitions.WHITE);
            makePiece(0, 6, PAWN, Definitions.BLACK);
            makePiece(7, 3, PAWN, Definitions.WHITE);
            break;
         case 3:  // Test for lock with contrasting bishops remaining
            makePiece(0, 3, PAWN, Definitions.WHITE);
            makePiece(0, 5, PAWN, Definitions.BLACK);
            makePiece(2, 3, PAWN, Definitions.WHITE);
            makePiece(2, 4, PAWN, Definitions.BLACK);
            makePiece(1, 0, BISHOP, Definitions.WHITE);
            makePiece(7, 7, BISHOP, Definitions.BLACK);
            break;
      }
   }

   public void makePiece(int column, int row, int pieceName, int playerColour) {
      switch (pieceName) {
         case PAWN:
            squares[column][row] = new Pawn(column, row, playerColour);
            break;
         case KNIGHT:
            squares[column][row] = new Knight(column, row, playerColour);
            break;
         case BISHOP:
            squares[column][row] = new Bishop(column, row, playerColour);
            break;
         case ROOK:
            squares[column][row] = new Rook(column, row, playerColour);
            break;
         case QUEEN:
            squares[column][row] = new Queen(column, row, playerColour);
            break;
         case KING:
            squares[column][row] = new King(column, row, playerColour);
            break;
         default:
            squares[column][row] = null;
            break;
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
         for (int step = 1; step < steps; step++) {
            if (squares[move.oldX + step * xIncrement][move.oldY + step * yIncrement] != null) {
               return false;
            }
         }
      }
      return true;
   }

   public boolean isMoveValid(int playerColour, Move move) {
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
      if (squares[move.newX][move.newY] != null && squares[move.newX][move.newY].isPlayersPiece(playerColour)) {
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

      //Crude first draft of pawn promotion code
      //Assumes the player wants a queen
      if ((move.newY == 7 || move.newY == 0) && squares[move.newX][move.newY] instanceof Pawn) {
         int colour = squares[move.newX][move.newY].pieceColour();
         squares[move.newX][move.newY] = new Queen(move.newX, move.newY, colour);
      }
   }

   public boolean isCapturePossible(int playerColour) {
      // check if any captures are listed in capture list
      return (validCaptures.size() > 0);
   }

   public void generateMoves(int playerColour) {

      // reset the ArrayLists
      validMoves.clear();
      validCaptures.clear();
      remainingPieces.clear();

      // create tempMove that holds each test move
      Move testMove;

      // iterate over all moves
      for (int sourceCol = 0; sourceCol < 8; sourceCol++) {
         for (int sourceRow = 0; sourceRow < 8; sourceRow++) {

            if (squares[sourceCol][sourceRow] != null) {
               if (squares[sourceCol][sourceRow].pieceColour() == playerColour) {
                  remainingPieces.add(squares[sourceCol][sourceRow]);
               }
            }
            for (int destCol = 0; destCol < 8; destCol++) {
               for (int destRow = 0; destRow < 8; destRow++) {
                  // initialise testMove
                  testMove = new Move(sourceCol, sourceRow, destCol, destRow);

                  // check move validity
                  if (this.isMoveValid(playerColour, testMove)) {
                     // if valid add to valid move list
                     validMoves.add(testMove);

                     // check if test move is capture
                     if (this.isMoveCapture(testMove)) {
                        validCaptures.add(testMove);
                     }
                  }

               }
            }
         }
      }
   }

   public char isWon() {
      generateMoves(Definitions.BLACK);
      if (remainingPieces.size() == 0) {
         return Definitions.BLACK;
      }
      generateMoves(Definitions.WHITE);
      if (remainingPieces.size() == 0) {
         return Definitions.WHITE;
      }
      return ' ';
   }

   public boolean isStaleMate() {
      boolean singleStartSquare = true;
      boolean freeBishop = true;
      int whiteBishopColour = -1;
      int blackBishopColour = -1;


      int bBishopX = 0, bBishopY = 0, wBishopX = 0, wBishopY = 0;

      // do tests for both players
      for (int i = 0; i < 2; i++) {
         int playerColour = Definitions.WHITE;
         if (i == 1) {
            playerColour = Definitions.BLACK;
         }

         generateMoves(playerColour);
         if (this.validMoves.size() <= 0) {
            continue;
         }


         int firstX = this.validMoves.get(0).oldX;
         int firstY = this.validMoves.get(0).oldY;

         // check that player can only move one piece
         for (int j = 1; j < this.validMoves.size(); j++) {
            int nextX = this.validMoves.get(j).oldX;
            int nextY = this.validMoves.get(j).oldY;
            if (nextX != firstX || nextY != firstY) {
               singleStartSquare = false;
            }
         }


         // check that all the player's pieces are on the same coloured squares
         int prevSquareColour = this.remainingPieces.get(0).getSquareColour();

         for (int j = 1; j < this.remainingPieces.size(); j++) {
            int nextSquareColour = this.remainingPieces.get(j).getSquareColour();

            if (nextSquareColour != prevSquareColour) {
               return false;
            }

            prevSquareColour = nextSquareColour;
         }

         // check whether movable piece is a bishop
         if (!(squares[firstX][firstY] instanceof Bishop)) {
            freeBishop = false;
         }

         // Clumsy bit of code that checks that the bishops aren't blocking the pawns
         if (playerColour == Definitions.BLACK && firstY > 0
                 && squares[firstX][firstY - 1] instanceof Pawn && squares[firstX][firstY - 1].pieceColour() == Definitions.WHITE) {
            return false;
         }
         if (playerColour == Definitions.WHITE && firstY < 7
                 && squares[firstX][firstY + 1] instanceof Pawn && squares[firstX][firstY + 1].pieceColour() == Definitions.BLACK) {
            return false;
         }

         /* If either player can move more than one piece, or the piece
         they can move isn't a bishop then return false */
         if (singleStartSquare == false || freeBishop == false) {
            return false;
         }

         if (playerColour == Definitions.WHITE) {
            wBishopX = firstX;
            wBishopY = firstY;
            whiteBishopColour = squares[wBishopX][wBishopY].getSquareColour();
         } else if (playerColour == Definitions.BLACK) {
            bBishopX = firstX;
            bBishopY = firstY;
            blackBishopColour = squares[bBishopX][bBishopY].getSquareColour();
         }
      }
      // return false if the opposing bishops are on the same colour square
      if (whiteBishopColour != -1 && blackBishopColour != -1) {
         if (whiteBishopColour == blackBishopColour) {
            return false;
         }
      }
      return true;
   }

   public boolean canMove() {
      return (validMoves.size() > 0);
   }

   public int isFinished(int playerColour) {
      int previousMoves = validMoves.size();

      if (playerColour == Definitions.BLACK) {
         this.generateMoves(Definitions.WHITE);
      } else if (playerColour == Definitions.WHITE) {
         this.generateMoves(Definitions.BLACK);
      }
      if (validMoves.size() + previousMoves == 0) {
         return LOCKED_STALEMATE;
      }

      if (this.isStaleMate()) {
         return DERIVED_STALEMATE;
      }
      if (this.isWon() == Definitions.WHITE) {
         return WHITE_WINS;
      }
      if (this.isWon() == Definitions.BLACK) {
         return BLACK_WINS;
      }
      return 0;

   }
}
