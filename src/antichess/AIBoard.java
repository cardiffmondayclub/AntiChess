package antichess;

import java.util.Stack;

public class AIBoard extends Board {

    private Stack<HistoryMove> historyStack;

    public AIBoard() {
        super();
        historyStack = new Stack<HistoryMove>();
    }

    @Override
    public void makeMove(Move move) {
        HistoryMove tempMove = new HistoryMove(move, squares[move.newX][move.newY]);
        historyStack.push(tempMove);
        super.makeMove(move);
    }

    public void undoMove() {
        //?????????
    }

    public int staticEval() {
        return 0;
    }




}
