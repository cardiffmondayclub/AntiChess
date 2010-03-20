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

	public AIBoard(int playerColour, int[] pieceValues, int testCase) {
		super(testCase);
		this.pieceValues = pieceValues;
		this.playerColour = playerColour;
		historyStack = new Stack<HistoryMove>();
	}

	public AIBoard Clone() {
		AIBoard board = new AIBoard(playerColour, pieceValues);
		for (int colour : Definitions.COLOURS) {
			for (Piece piece : remainingPieces[colour]) {
				board.makePiece(piece.xPosition, piece.yPosition, piece.pieceType, colour);
			}
		}
		return board;
	}

	@Override
	public void makeMove(Move move) {
		if (move != null) {

			HistoryMove tempMove = new HistoryMove(move, squares[move.newX][move.newY]);
			historyStack.push(tempMove);
			super.makeMove(move);

		} else {
			historyStack.push(null);
		}
	}

	public void undoMove() {
		HistoryMove tempMove = historyStack.pop();
		if (tempMove != null) {

			tempMove.reverseMove();
			super.makeMove(tempMove.move);

			squares[tempMove.move.oldX][tempMove.move.oldY] = tempMove.capturedPiece;

			if (tempMove.capturedPiece != null) {
				remainingPieces[tempMove.capturedPiece.colour].add(tempMove.capturedPiece);
			}
		}
	}

	public int staticEval() {
		int evalTotal = 0;
		int oppositeColour = (playerColour + 1) % 2;

		if (isWon() == playerColour) {
			evalTotal = Integer.MAX_VALUE - 1;
			return evalTotal;
		}

		if (isWon() == oppositeColour) {
			evalTotal = Integer.MIN_VALUE + 1;
			return evalTotal;
		}

		for (int i = 0; i < remainingPieces[playerColour].size(); i++) {
			switch (remainingPieces[playerColour].get(i).pieceType) {
				case Definitions.PAWN:
					evalTotal -= pieceValues[Definitions.PAWN];
					break;

				case Definitions.KNIGHT:
					evalTotal -= pieceValues[Definitions.KNIGHT];
					break;

				case Definitions.BISHOP:
					evalTotal -= pieceValues[Definitions.BISHOP];
					break;

				case Definitions.ROOK:
					evalTotal -= pieceValues[Definitions.ROOK];
					break;

				case Definitions.QUEEN:
					evalTotal -= pieceValues[Definitions.QUEEN];
					break;

				case Definitions.KING:
					evalTotal -= pieceValues[Definitions.KING];
					break;

			}


		}

		for (int i = 0; i <
				  remainingPieces[oppositeColour].size(); i++) {
			switch (remainingPieces[oppositeColour].get(i).pieceType) {
				case Definitions.PAWN:
					evalTotal += pieceValues[Definitions.PAWN];
					break;

				case Definitions.KNIGHT:
					evalTotal += pieceValues[Definitions.KNIGHT];
					break;

				case Definitions.BISHOP:
					evalTotal += pieceValues[Definitions.BISHOP];
					break;

				case Definitions.ROOK:
					evalTotal += pieceValues[Definitions.ROOK];
					break;

				case Definitions.QUEEN:
					evalTotal += pieceValues[Definitions.QUEEN];
					break;

				case Definitions.KING:
					evalTotal += pieceValues[Definitions.KING];
					break;

			}


		}

		return evalTotal;
	}
}
