package antichess;

public class HistoryMove {
	public Move move;
	public Piece capturedPiece;

	public HistoryMove(Move move, Piece capturedPiece) {
		this.move = move.clone();
		this.capturedPiece = capturedPiece;
	}

	public void reverseMove() {
		int tempOldX = move.oldX;
		int tempOldY = move.oldY;
		move.oldX = move.newX;
		move.oldY = move.newY;
		move.newX = tempOldX;
		move.newY = tempOldY;

		if (move instanceof PromotionMove) {
			((PromotionMove) move).newPiece = Definitions.PAWN;
		}
	}
}
