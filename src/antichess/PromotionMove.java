package antichess;

public class PromotionMove extends Move {

public int newPiece;

    public PromotionMove(int oldX, int oldY, int newX, int newY, int newPiece ) {
        super(oldX, oldY, newX, newY);
        this.newPiece = newPiece;
    }

	 public PromotionMove(Move move, int newPiece) {
		 super(move.oldX, move.oldY, move.newX, move.newY);
		 this.newPiece = newPiece;
	 }

	@Override
	 public PromotionMove clone() {
		 return new PromotionMove(super.clone() ,newPiece);
	 }
}
