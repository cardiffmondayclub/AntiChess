package antichess;

public class PromotionMove extends Move {

public Piece newPiece;

    public PromotionMove(int oldX, int oldY, int newX, int newY, Piece newPiece ) {
        super(oldX, oldY, newX, newY);
        this.newPiece = newPiece;
    }
}
