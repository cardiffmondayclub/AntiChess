package antichess;

import java.util.ArrayList;

public class Pawn extends Piece {
	public Pawn(int posX, int posY, int newColour) {
		super(posX, posY, newColour, "pawn");
	}

	public boolean isMoveValid(Board board, Move move) {
		int newX = move.newX;
		int newY = move.newY;
		int oldX = move.oldX;
		int oldY = move.oldY;

		int xDiff = move.newX - move.oldX;
		int yDiff = move.newY - move.oldY;
		int xDiffAbs = Math.abs(xDiff);
		int yDiffAbs = Math.abs(yDiff);

		// Capture case

		if (this.colour == Definitions.WHITE && board.isMoveCapture(move)) {
			if (xDiffAbs == 1 && yDiff == 1) {
				return true;
			} else {
				return false;
			}
		} else if (this.colour == Definitions.BLACK && board.isMoveCapture(move)) {
			if (xDiffAbs == 1 && yDiff == -1) {
				return true;
			} else {
				return false;
			}
		}

		// First move case
		if (this.colour == Definitions.WHITE && oldY == 1) {
			if (yDiff <= 2 && yDiff > 0 && xDiff == 0 && board.isPathClear(move)) {
				return true;
			} else {
				return false;
			}
		} else if (this.colour == Definitions.BLACK && oldY == 6) {
			if (yDiff >= -2 && yDiff < 0 && xDiff == 0 && board.isPathClear(move)) {
				return true;
			} else {
				return false;
			}
		} // other case
		else if (this.colour == Definitions.WHITE) {
			if (xDiff == 0 && yDiff == 1) {
				return true;
			} else {
				return false;
			}
		} else if (this.colour == Definitions.BLACK) {
			if (xDiff == 0 && yDiff == -1) {
				return true;
			} else {
				return false;
			}
		}

		return true;

	}

	public String getPieceName() {
		return "pawn";
	}

	public int getPieceType() {
		return Definitions.PAWN;
	}

	public void generateMoves(Piece[][] squares, ArrayList<Move> validMoves, ArrayList<Move> validCaptures) {
		//do stuff
		}
}
