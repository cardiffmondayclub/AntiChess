package antichess;

import java.util.Stack;

public class AIBoard extends Board {

    private Stack<HistoryMove> historyStack;
    private int[] pieceValues;
    private int playerColour;

    public AIBoard(int playerColour, int[] pieceValues) {
        super();
        historyStack = new Stack<HistoryMove>();
        this.pieceValues = pieceValues;
        this.playerColour = playerColour;
    }

    @Override
    public void makeMove(Move move) {
        HistoryMove tempMove = new HistoryMove(move, squares[move.newX][move.newY]);
        historyStack.push(tempMove);
        super.makeMove(move);
    }

    public void undoMove() {
        HistoryMove tempMove = historyStack.pop();
        tempMove.reverseMove();
        super.makeMove(tempMove.move);
        squares[tempMove.move.oldX][tempMove.move.oldY] = tempMove.capturedPiece;
    }

    public int staticEval() {
        generateMoves(playerColour);
        for(int i = 0; i < remainingPieces.size(); i++) {
            switch()
        }

        return 0;
    }




}
