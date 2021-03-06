package antichess;

import java.util.ArrayList;

public class Rook extends Piece {

final static int[] xMoves = {-1,0,1,0};
final static int[] yMoves = {0,1,0,-1};	

   public Rook(int posX, int posY, int newColour) {
      super(posX, posY, newColour, Definitions.ROOK);
   }

  public boolean isMoveValid(Board board, Move move) {
      int newX = move.newX;
      int newY = move.newY;
      int oldX = move.oldX;
      int oldY = move.oldY;

      int xDiff = Math.abs(oldX - newX);  // number of squares moved in x
      int yDiff = Math.abs(oldY - newY);  // number of squares moved in y

      if ((xDiff == 0 || yDiff == 0) && board.isPathClear(move)) {
         return true;
      } else {
         return false;
      }
   }

   public String getPieceName() {
      return "rook";
   }

   public int getPieceType() {
      return Definitions.ROOK;
   }

	public void generateMoves(Piece[][] squares, ArrayList<Move> validMoves, ArrayList<Move> validCaptures) {
		// for each possible direction (NE, SE, SW, NW)
		int xPos;
		int yPos;

		for (int direction = 0; direction < xMoves.length; direction++) {
			xPos = xPosition + xMoves[direction];
			yPos = yPosition + yMoves[direction];

			while (0 <= xPos && xPos <= 7 && 0 <= yPos && yPos <= 7) {
				if (squares[xPos][yPos] == null) {
					//if the square is empty
					validMoves.add(new Move(xPosition, yPosition, xPos, yPos));
				} else {
					//if the square contains a piece
					if (squares[xPos][yPos].colour == this.colour) {
						//if the square contains a friendly piece
						break;
					} else {
						validCaptures.add(new Move(xPosition, yPosition, xPos, yPos));
						break;
						//if the square contains an enemy piece
					}
				}
				//increment the position
				xPos += xMoves[direction];
				yPos += yMoves[direction];
			}

		}
	}
}
