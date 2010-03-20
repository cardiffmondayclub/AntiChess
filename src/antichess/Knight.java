package antichess;

import java.util.ArrayList;

public class Knight extends Piece {
	final static int[] xMoves = {-2,-2,-1,1,2,2,1,-1};
	final static int[] yMoves = {1,-1,2,2,1,-1,-2,-2};

	public Knight(int posX, int posY, int newColour) {
		super(posX, posY, newColour, Definitions.KNIGHT);
	}

	public boolean isMoveValid(Board board, Move move) {
		int newX = move.newX;
		int newY = move.newY;
		int oldX = move.oldX;
		int oldY = move.oldY;

		int xDiff = Math.abs(oldX - newX);  // number of squares moved in x
		int yDiff = Math.abs(oldY - newY);  // number of squares moved in y

		/* if number of squares moved in x and y totals 3, and squares
		moved in x direc is either 1 or 2, then return true */
		if ((xDiff + yDiff == 3) && ((xDiff == 1) || (xDiff == 2))) {
			return true;
		} else {
			return false;
		}

	}

	public String getPieceName() {
		return "knight";
	}

	public int getPieceType() {
		return Definitions.KNIGHT;
	}

	public void generateMoves(Piece[][] squares, ArrayList<Move> validMoves, ArrayList<Move> validCaptures) {
		// for each possible direction (NE, SE, SW, NW)
		int xPos;
		int yPos;

		for (int direction = 0; direction < xMoves.length; direction++) {
			xPos = xPosition + xMoves[direction];
			yPos = yPosition + yMoves[direction];

			//Make sure the move would still be inside the board
			if (0 <= xPos && xPos <= 7 && 0 <= yPos && yPos <= 7) {

				if (squares[xPos][yPos] == null) {
					//if the square is empty
					validMoves.add(new Move(xPosition, yPosition, xPos, yPos));
				} else {
					//if the square contains a piece
					if (squares[xPos][yPos].colour == this.colour) {
						//if the square contains a friendly piece do nothing
					} else {
						validCaptures.add(new Move(xPosition, yPosition, xPos, yPos));
						//if the square contains an enemy piece
					}
				}
			}
		}
	}
}
