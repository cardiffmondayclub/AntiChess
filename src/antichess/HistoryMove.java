package antichess;

public class HistoryMove {
    private Move move;
    private Piece capturedPiece;

    public HistoryMove(Move move, Piece capturedPiece) {
        this.move = move;
        this.capturedPiece = capturedPiece;
    }

}
