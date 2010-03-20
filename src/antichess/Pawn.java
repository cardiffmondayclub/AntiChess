package antichess;

import java.util.ArrayList;

public class Pawn extends Piece {
	public Pawn(int posX, int posY, int newColour) {
		super(posX, posY, newColour, Definitions.PAWN);
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

	public final void generateMoves(Piece[][] squares, ArrayList<Move> validMoves, ArrayList<Move> validCaptures) {
		//do stuff
		int yDiff = 0;
		Move move;
		switch (colour) {
			case Definitions.WHITE:
				yDiff = 1;
				break;
			case Definitions.BLACK:
				yDiff = -1;
				break;
		}

		//check square in front
		if (squares[xPosition][yPosition + yDiff] == null) {

			//if first move check the square two in front
			if ((colour == Definitions.BLACK && yPosition == 6) || (colour == Definitions.WHITE && yPosition == 1)) {
				if (squares[xPosition][yPosition + 2 * yDiff] == null) {
					validMoves.add(new Move(xPosition, yPosition, xPosition, yPosition + 2 * yDiff));
				}
			}

			if ((yPosition + yDiff == 0) || (yPosition + yDiff == 7)) {
				//adds possible pawn promotions
				move = new Move(xPosition, yPosition, xPosition, yPosition + yDiff);
				for (int promotionPiece : Definitions.PROMOTION_PIECES) {
					validMoves.add(new PromotionMove(move, promotionPiece));
				}
			} else {
				validMoves.add(new Move(xPosition, yPosition, xPosition, yPosition + yDiff));
			}
		}

		//check square diagonally in front for capture

		if (xPosition > 0) {
			//if a left diagnoal capture is possible
			if ((squares[xPosition - 1][yPosition + yDiff] != null) && (squares[xPosition - 1][yPosition + yDiff].colour != this.colour)) {
				if ((yPosition + yDiff == 0) || (yPosition + yDiff == 7)) {
					//adds possible pawn promotions
					move = new Move(xPosition, yPosition, xPosition - 1, yPosition + yDiff);
					for (int promotionPiece : Definitions.PROMOTION_PIECES) {
						validCaptures.add(new PromotionMove(move, promotionPiece));
					}
				} else {
					validCaptures.add(new Move(xPosition, yPosition, xPosition - 1, yPosition + yDiff));
				}
			}
		}

		if (xPosition < 7) {
			//if a right diagonal capture is possible
			if ((squares[xPosition + 1][yPosition + yDiff] != null) && (squares[xPosition + 1][yPosition + yDiff].colour != this.colour)) {
				if ((yPosition + yDiff == 0) || (yPosition + yDiff == 7)) {
					//adds possible pawn promotions
					move = new Move(xPosition, yPosition, xPosition + 1, yPosition + yDiff);
					for (int promotionPiece : Definitions.PROMOTION_PIECES) {
						validCaptures.add(new PromotionMove(move, promotionPiece));
					}
				} else {
					validCaptures.add(new Move(xPosition, yPosition, xPosition + 1, yPosition + yDiff));
				}
			}
		}



	}
}
