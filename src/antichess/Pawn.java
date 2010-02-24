package antichess;

public class Pawn extends Piece
{

	public Pawn(int posX, int posY, char newColour)
	{
		super(posX, posY, newColour, "pawn");
	}

	public char getAppearance()
	{
		if (this.colour == 'b') {
			return 'p';
		} else {
			return 'P';
		}
	}

	public boolean isMoveValid(Board board, Move move)
	{
		int newX = move.newX;
		int newY = move.newY;
		int oldX = move.oldX;
		int oldY = move.oldY;

		int xDiff = move.newX - move.oldX;
		int yDiff = move.newY - move.oldY;
		int xDiffAbs = Math.abs(xDiff);
		int yDiffAbs = Math.abs(yDiff);

		// Capture case

		if (this.colour == 'w' && board.isMoveCapture(move)) {
			if (xDiffAbs == 1 && yDiff == 1) {
				return true;
			} else {
				return false;
			}
		} else if (this.colour == 'b' && board.isMoveCapture(move)) {
			if (xDiffAbs == 1 && yDiff == -1) {
				return true;
			} else {
				return false;
			}
		}

		// First move case
		if (this.colour == 'w' && oldY == 1) {
			if (yDiff <= 2 && yDiff > 0 && xDiff == 0 && board.isPathClear(move)) {
				return true;


			} else {
				return false;


			}
		} else if (this.colour == 'b' && oldY == 6) {
			if (yDiff >= -2 && yDiff < 0 && xDiff == 0 && board.isPathClear(move)) {
				return true;


			} else {
				return false;


			}
		} // other case
		else if (this.colour == 'w') {
			if (xDiff == 0 && yDiff == 1) {
				return true;


			} else {
				return false;


			}
		} else if (this.colour == 'b') {
			if (xDiff == 0 && yDiff == -1) {
				return true;


			} else {
				return false;


			}
		}

		return true;

	}
//   public boolean isCapturePossible(Board board) {
//      return true;
//   }
}
