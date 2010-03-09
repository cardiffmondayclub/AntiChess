package antichess;

import java.util.ArrayList;
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

//	@Override
//	public AIBoard clone() {
//		AIBoard temp = new AIBoard(playerColour, pieceValues.clone());
//		temp.squares = this.squares.clone();
//		temp.historyStack = (Stack<HistoryMove>) this.historyStack.clone();
//		temp.validMoves = (ArrayList<Move>) this.validMoves.clone();
//		temp.validCaptures = (ArrayList<Move>) this.validCaptures.clone();
//		temp.remainingPieces = (ArrayList<Piece>) this.remainingPieces.clone();
//		return temp;
//}

@Override
    public void

makeMove(Move move) {
        HistoryMove tempMove = new HistoryMove(move, squares[move.newX][move.newY]);
        historyStack.push

(tempMove);
        super

.makeMove(move);
    }

public void

undoMove() {
        HistoryMove tempMove = historyStack.pop();
        tempMove.reverseMove

();
        super

.makeMove(tempMove.move);
        squares[tempMove

.move.oldX][tempMove.move.oldY] = tempMove.capturedPiece;
    }

public int

staticEval() {
        int evalTotal = 0;
        int

oppositeColour = (playerColour + 1) % 2;
        
        if

(isWon() == playerColour) {
            evalTotal = Integer.MAX_VALUE;
            return

evalTotal;
        }

if(isWon() == oppositeColour) {
            evalTotal = Integer.MIN_VALUE;
            return

evalTotal;
        }

generateMoves(playerColour);
        for

(int i = 0; i <

remainingPieces.size(); i++)

{
            switch(remainingPieces.get(i).getPieceType()) {
                case Definitions.PAWN:
                    evalTotal -= pieceValues[Definitions.PAWN];
                    break;

case

Definitions.KNIGHT:
                    evalTotal -= pieceValues[Definitions.KNIGHT];
                    break;

case

Definitions.BISHOP:
                    evalTotal -= pieceValues[Definitions.BISHOP];
                    break;

case

Definitions.ROOK:
                    evalTotal -= pieceValues[Definitions.ROOK];
                    break;

case

Definitions.QUEEN:
                    evalTotal -= pieceValues[Definitions.QUEEN];
                    break;

case

Definitions.KING:
                    evalTotal -= pieceValues[Definitions.KING];
                    break;

}


}

        generateMoves(oppositeColour);
        for

(int i = 0; i <

remainingPieces.size(); i++)

{
            switch(remainingPieces.get(i).getPieceType()) {
                case Definitions.PAWN:
                    evalTotal += pieceValues[Definitions.PAWN];
                    break;

case

Definitions.KNIGHT:
                    evalTotal += pieceValues[Definitions.KNIGHT];
                    break;

case

Definitions.BISHOP:
                    evalTotal += pieceValues[Definitions.BISHOP];
                    break;

case

Definitions.ROOK:
                    evalTotal += pieceValues[Definitions.ROOK];
                    break;

case

Definitions.QUEEN:
                    evalTotal += pieceValues[Definitions.QUEEN];
                    break;

case

Definitions.KING:
                    evalTotal += pieceValues[Definitions.KING];
                    break;

}


}

        return evalTotal;
    }
}
