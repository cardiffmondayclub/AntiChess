package antichess;

import java.util.ArrayList;

public class Board {
	public Piece[][] squares;
	public ArrayList<Move> validMoves;
	public ArrayList<Move> validCaptures;
	public ArrayList<Piece>[] remainingPieces;

	public Board() {
		// initialise the array lists
		validMoves = new ArrayList<Move>();
		validCaptures = new ArrayList<Move>();
		remainingPieces = new ArrayList[2];
		remainingPieces[Definitions.WHITE] = new ArrayList<Piece>();
		remainingPieces[Definitions.BLACK] = new ArrayList<Piece>();


		//Initialises the board.
		squares = new Piece[8][8];

		makePiece(0, 0, Definitions.ROOK, Definitions.WHITE);
		makePiece(1, 0, Definitions.KNIGHT, Definitions.WHITE);
		makePiece(2, 0, Definitions.BISHOP, Definitions.WHITE);
		makePiece(3, 0, Definitions.QUEEN, Definitions.WHITE);
		makePiece(4, 0, Definitions.KING, Definitions.WHITE);
		makePiece(5, 0, Definitions.BISHOP, Definitions.WHITE);
		makePiece(6, 0, Definitions.KNIGHT, Definitions.WHITE);
		makePiece(7, 0, Definitions.ROOK, Definitions.WHITE);

		for (int i = 0; i < 8; i++) {
			makePiece(i, 1, Definitions.PAWN, Definitions.WHITE);
			makePiece(i, 6, Definitions.PAWN, Definitions.BLACK);
		}

		makePiece(0, 7, Definitions.ROOK, Definitions.BLACK);
		makePiece(1, 7, Definitions.KNIGHT, Definitions.BLACK);
		makePiece(2, 7, Definitions.BISHOP, Definitions.BLACK);
		makePiece(3, 7, Definitions.QUEEN, Definitions.BLACK);
		makePiece(4, 7, Definitions.KING, Definitions.BLACK);
		makePiece(5, 7, Definitions.BISHOP, Definitions.BLACK);
		makePiece(6, 7, Definitions.KNIGHT, Definitions.BLACK);
		makePiece(7, 7, Definitions.ROOK, Definitions.BLACK);


	}

	public Board(int testNumber) {
		this();  // Inherit code from first Board constructor

		// wipe board
		while(remainingPieces[Definitions.WHITE].size() > 0) {
			removePiece(remainingPieces[Definitions.WHITE].get(0));
		}
		while(remainingPieces[Definitions.BLACK].size() > 0) {
			removePiece(remainingPieces[Definitions.BLACK].get(0));
		}

		switch (testNumber) {
			case 1:  // Test for locked stalemate
				makePiece(0, 1, Definitions.PAWN, Definitions.WHITE);
				makePiece(0, 3, Definitions.PAWN, Definitions.BLACK);
				break;
			case 2:  // Test for lock for one player
				makePiece(0, 5, Definitions.PAWN, Definitions.WHITE);
				makePiece(0, 6, Definitions.PAWN, Definitions.BLACK);
				makePiece(7, 6, Definitions.PAWN, Definitions.WHITE);
				break;
			case 3:  // Test for lock with contrasting bishops remaining
				makePiece(0, 3, Definitions.PAWN, Definitions.WHITE);
				makePiece(0, 5, Definitions.PAWN, Definitions.BLACK);
				makePiece(2, 3, Definitions.PAWN, Definitions.WHITE);
				makePiece(2, 4, Definitions.PAWN, Definitions.BLACK);
				makePiece(1, 0, Definitions.BISHOP, Definitions.WHITE);
				makePiece(7, 7, Definitions.BISHOP, Definitions.BLACK);
				break;
		}
	}

	public void makePiece(int column, int row, int pieceName, int playerColour) {
		Piece newPiece = null;
		switch (pieceName) {
			case Definitions.PAWN:
				newPiece = new Pawn(column, row, playerColour);
				break;
			case Definitions.KNIGHT:
				newPiece = new Knight(column, row, playerColour);
				break;
			case Definitions.BISHOP:
				newPiece = new Bishop(column, row, playerColour);
				break;
			case Definitions.ROOK:
				newPiece = new Rook(column, row, playerColour);
				break;
			case Definitions.QUEEN:
				newPiece = new Queen(column, row, playerColour);
				break;
			case Definitions.KING:
				newPiece = new King(column, row, playerColour);
				break;
			default:
				break;
		}
		squares[column][row] = newPiece;
		remainingPieces[playerColour].add(squares[column][row]);
	}

	public void removePiece(Piece piece) {
		squares[piece.xPosition][piece.yPosition] = null;
		remainingPieces[piece.colour].remove(piece);
	}

	public boolean isPathClear(Move move) {
		//This could do with some tidying/refactoring. MCS
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
		if (move == null) {
			return;
		}
		/* Assuming that everything has been checked by isMoveValid() and
		 * isMoveCapture() this function simply replaces the contents of the
		 * new square with the contents of the old square and wipes the old
		 * square
		 */

		//Remove a piece from the remaining pieces list if it is being captured
		if (squares[move.newX][move.newY] != null) {
			Piece piece = squares[move.newX][move.newY];
			remainingPieces[piece.colour].remove(piece);
		}

		squares[move.newX][move.newY] = squares[move.oldX][move.oldY];
		squares[move.oldX][move.oldY] = null;

		//Updates the Piece instances position values
		squares[move.newX][move.newY].setPosition(move.newX, move.newY);

		//Pawn promotion stuff
		if (move instanceof PromotionMove) {
			int colour = squares[move.newX][move.newY].pieceColour();
			removePiece(squares[move.newX][move.newY]);
			makePiece(move.newX, move.newY, ((PromotionMove) move).newPiece, colour);
		}
	}

	public boolean isMovePawnPromotion(Move move) {
		return (move.newY == 7 || move.newY == 0) && squares[move.oldX][move.oldY] instanceof Pawn;
	}

	public boolean isCapturePossible(int playerColour) {
		// check if any captures are listed in capture list
		return (validCaptures.size() > 0);
	}

	public void generateMoves(int playerColour) {

		// reset the ArrayLists
		validMoves.clear();
		validCaptures.clear();

		// iterate over all moves
		for (Piece piece : remainingPieces[playerColour]) {
			piece.generateMoves(squares, validMoves, validCaptures);
		}

//		System.out.println("Valid Moves");
//		for (Move move : validMoves) {
//			System.out.println("-" + move.oldX + move.oldY + move.newX + move.newY + "-");
//		}
//
//		System.out.println("Valid Captures");
//		for (Move move : validCaptures) {
//			System.out.println("-" + move.oldX + move.oldY + move.newX + move.newY + "-");
//		}
	}

	public int isWon() {

		if (remainingPieces[Definitions.BLACK].size() == 0) {
			return Definitions.BLACK;
		}

		if (remainingPieces[Definitions.WHITE].size() == 0) {
			return Definitions.WHITE;
		}

		return Definitions.NO_COLOUR;
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
			int prevSquareColour = this.remainingPieces[playerColour].get(0).getSquareColour();

			for (int j = 1; j < this.remainingPieces[playerColour].size(); j++) {
				int nextSquareColour = this.remainingPieces[playerColour].get(j).getSquareColour();

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
			if (playerColour == Definitions.BLACK && firstY > 0 && squares[firstX][firstY - 1] instanceof Pawn && squares[firstX][firstY - 1].pieceColour() == Definitions.WHITE) {
				return false;
			}
			if (playerColour == Definitions.WHITE && firstY < 7 && squares[firstX][firstY + 1] instanceof Pawn && squares[firstX][firstY + 1].pieceColour() == Definitions.BLACK) {
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
		return (validMoves.size() + validCaptures.size() > 0);
	}

	public int isFinished(int playerColour) {
		int previousMoves = validMoves.size();

		if (playerColour == Definitions.BLACK) {
			this.generateMoves(Definitions.WHITE);
		} else if (playerColour == Definitions.WHITE) {
			this.generateMoves(Definitions.BLACK);
		}

		if (validMoves.size() + previousMoves == 0) {
			return Definitions.LOCKED_STALEMATE;
		}

		if (this.isStaleMate()) {
			return Definitions.DERIVED_STALEMATE;
		}
		if (this.isWon() == Definitions.WHITE) {
			return Definitions.WHITE_WINS;
		}
		if (this.isWon() == Definitions.BLACK) {
			return Definitions.BLACK_WINS;
		}
		return Definitions.NO_WIN;

	}
}
