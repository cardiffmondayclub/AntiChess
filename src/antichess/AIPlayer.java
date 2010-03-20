package antichess;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AIPlayer extends Player {
	private AIBoard currentBoard;
	final int MIN = 0;
	final int MAX = 1;

	public AIPlayer(int colour, Object options) {
		super(colour);
		currentBoard = new AIBoard(colour, ((AIOptions) options).pieceValues);
	}

	@Override
	public Move getMove() {
		int thinkingTime = 0;
		if (playerColour == Definitions.WHITE) {
			thinkingTime = 750;
		} else {
			thinkingTime = 750;
		}
		System.out.println("AI Thinking (" + thinkingTime / 1000.0 + " seconds)");
		long startTime = System.currentTimeMillis();
		long finishTime = System.currentTimeMillis();

		int depth = 1;
		Move move = null;
		Move[][] moves = new Move[depth][depth];
		Move[] previousMoves = null;
		currentBoard.generateMoves(playerColour);

		//Create the first thread and start it running.
		System.out.println("Running depth 1 search");
		Runnable search;
		if (playerColour == Definitions.WHITE) {
			search = new MiniMaxSearch2(currentBoard, playerColour, otherPlayerColour, depth, previousMoves, moves);
		} else {
			search = new MiniMaxSearch(currentBoard, playerColour, otherPlayerColour, depth, previousMoves, moves);
		}
		Thread t = new Thread(search);
		t.start();
		//Ensure the first thread completes
		try {
			t.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(AIPlayer.class.getName()).log(Level.SEVERE, null, ex);
		}

		while (System.currentTimeMillis() - startTime < thinkingTime) {
			try {
				//if the thread has finished start another one
				if (t.getState() == State.TERMINATED) {
					move = moves[0][0];
					finishTime = System.currentTimeMillis();
					//System.out.println("Current best move is " + move.oldX + move.oldY + move.newX + move.newY);
					depth++;
					System.out.println("Trying depth " + depth);
					previousMoves = moves[0];
					moves = new Move[depth][depth];
					currentBoard.generateMoves(playerColour);
					if (playerColour == Definitions.WHITE) {
						search = new MiniMaxSearch2(currentBoard, playerColour, otherPlayerColour, depth, previousMoves, moves);
					} else {
						search = new MiniMaxSearch(currentBoard, playerColour, otherPlayerColour, depth, previousMoves, moves);
					}
					t = new Thread(search);
					t.start();
				}
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				Logger.getLogger(AIPlayer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		t.interrupt();
		try {
			t.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(AIPlayer.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.println("Terminating depth " + depth + " search");
		System.out.println("The move to be used is " + move.oldX + move.oldY + move.newX + move.newY);
		System.out.println("The " + (depth - 1) + " level search took " + (finishTime - startTime) / 1000.0 + " seconds");


		currentBoard.makeMove(move);

		return move;

	}

	public void sendMove(Move move) {
		currentBoard.makeMove(move);
	}

	private class MiniMaxSearch implements Runnable {
		private AIBoard currentBoard;
		private int playerColour;
		private int otherPlayerColour;
		private int maxDepth;
		private Move[][] moves;

		public MiniMaxSearch(AIBoard currentBoard, int playerColour, int otherPlayerColour, int maxDepth, Move[] unused, Move[][] moves) {
			this.currentBoard = currentBoard;
			this.playerColour = playerColour;
			this.otherPlayerColour = otherPlayerColour;
			this.maxDepth = maxDepth;
			this.moves = moves;
		}

		public void run() {
			try {
				miniMax(0, MAX, Integer.MAX_VALUE);
			} catch (InterruptedException exception) {
				//just exit if interrupted
			}
		}

		public int miniMax(int currentDepth, int searchType, int parentNodeScore) throws InterruptedException {
			try {
				if (currentDepth == maxDepth) {
					//If this is the bottom layer
					return currentBoard.staticEval();
				} else {
					//Get the list of move (captures if one is possible, valid moves otherwise)
					ArrayList<Move> moveList;
					if (currentBoard.validCaptures.size() > 0) {
						moveList = (ArrayList<Move>) currentBoard.validCaptures.clone();
					} else {
						moveList = (ArrayList<Move>) currentBoard.validMoves.clone();
					}
					//Certain actions should be taken if the move list has no moves in it
					if (moveList.size() == 0) {
						int finished = currentBoard.isFinished(playerColour);

						//check for wins
						if (finished == Definitions.WHITE_WINS || finished == Definitions.BLACK_WINS) {
							return currentBoard.staticEval();
						}

						//check for stalemates
						if (finished == Definitions.LOCKED_STALEMATE || finished == Definitions.DERIVED_STALEMATE) {
							//THIS SHOULD REALLY BE DISCUSSED. I'm not sure 0 is the appropriate score for stalemate. MCS
							return 0;
						}

						//if the player just has no moves add a null move
						moveList.add(null);
					}

					//Iterate through the list, make each move, find the max of that position
					//at the end return the minimum score and update some list of moves
					//Might need something to check for situations where there are no possible moves
					//(someone has won/some has lost/can hapen in stalemate)
					int score = 0;
					int testScore = 0;
					//switch based on min or max search
					switch (searchType) {
						case MIN:
							score = Integer.MAX_VALUE;
							for (Move move : moveList) {
								currentBoard.makeMove(move);
								Thread.sleep(0);
								currentBoard.generateMoves(playerColour);
								testScore = miniMax(currentDepth + 1, MAX, score);
								if (testScore < score) {
									score = testScore;
									moves[0][currentDepth] = move;
								}
								currentBoard.undoMove();
								if (score <= parentNodeScore) {
									break;
								}

							}
							break;
						case MAX:
							score = Integer.MIN_VALUE;
							for (Move move : moveList) {
								currentBoard.makeMove(move);
								Thread.sleep(0);
								currentBoard.generateMoves(otherPlayerColour);
								testScore = miniMax(currentDepth + 1, MIN, score);
								if (testScore > score) {
									score = testScore;
									moves[0][currentDepth] = move;
								}
								currentBoard.undoMove();
								if (score >= parentNodeScore) {
									break;
								}

							}
							break;
					}
					//end switch
					return score;
				}
			} catch (InterruptedException exception) {
				currentBoard.undoMove();
				throw exception;
			}
		}
	}

	private class MiniMaxSearch2 implements Runnable {
		private AIBoard currentBoard;
		private int playerColour;
		private int otherPlayerColour;
		private int maxDepth;
		private Move[] previousMoves;
		private Move[][] futureMoves;

		public MiniMaxSearch2(AIBoard currentBoard, int playerColour, int otherPlayerColour, int maxDepth, Move[] previousMoves, Move[][] futureMoves) {
			this.currentBoard = currentBoard;
			this.playerColour = playerColour;
			this.otherPlayerColour = otherPlayerColour;
			this.maxDepth = maxDepth;
			this.previousMoves = previousMoves;
			this.futureMoves = futureMoves;
		}

		public void run() {
			try {
				if (previousMoves == null) {
					miniMax(0, MAX, Integer.MAX_VALUE, false);
				} else {
					miniMax(0, MAX, Integer.MAX_VALUE, true);
				}
			} catch (InterruptedException exception) {
				//just exit if interrupted
			}
		}

		public int miniMax(int currentDepth, int searchType, int parentNodeScore, boolean bestMove) throws InterruptedException {
			try {
				if (currentDepth == maxDepth) {
					//If this is the bottom layer
					return currentBoard.staticEval();
				} else {
					//Get the list of move (captures if one is possible, valid moves otherwise)
					ArrayList<Move> moveList;
					if (currentBoard.validCaptures.size() > 0) {
						moveList = (ArrayList<Move>) currentBoard.validCaptures.clone();
					} else {
						moveList = (ArrayList<Move>) currentBoard.validMoves.clone();
					}
					//Certain actions should be taken if the move list has no moves in it
					if (moveList.size() == 0) {
						int finished = currentBoard.isFinished(playerColour);

						//check for wins
						if (finished == Definitions.WHITE_WINS || finished == Definitions.BLACK_WINS) {
							return currentBoard.staticEval();
						}

						//check for stalemates
						if (finished == Definitions.LOCKED_STALEMATE || finished == Definitions.DERIVED_STALEMATE) {
							//THIS SHOULD REALLY BE DISCUSSED. I'm not sure 0 is the appropriate score for stalemate. MCS
							return 0;
						}

						//if the player just has no moves add a null move
						moveList.add(null);
					}

					//Iterate through the list, make each move, find the max of that position
					//at the end return the minimum score and update some list of moves
					//Might need something to check for situations where there are no possible moves
					//(someone has won/some has lost/can hapen in stalemate)
					int score = 0;
					int testScore = 0;
					//switch based on min or max search

					//Move the best previous move to the front if one exists.
					if (bestMove == true) {
						ArrayList<Move> tempList = new ArrayList<Move>();
						tempList.add(previousMoves[currentDepth]);
						tempList.addAll(moveList);
						moveList = tempList;
					}

					switch (searchType) {
						case MIN:
							score = Integer.MAX_VALUE;
							for (Move move : moveList) {
								currentBoard.makeMove(move);
								Thread.sleep(0);
								currentBoard.generateMoves(playerColour);
								if (bestMove == true && move == previousMoves[currentDepth]) {
									if (previousMoves.length > currentDepth + 1) {
										testScore = miniMax(currentDepth + 1, MAX, score, true);
									} else {
										testScore = miniMax(currentDepth + 1, MAX, score, false);
									}
								} else {
									testScore = miniMax(currentDepth + 1, MAX, score, false);
								}
								if (testScore < score) {
									score = testScore;
									futureMoves[currentDepth][currentDepth] = move;
									//copy other moves
									for (int i = currentDepth + 1; i < maxDepth; i++) {
										futureMoves[currentDepth][i] = futureMoves[currentDepth + 1][i];
									}
								}
								currentBoard.undoMove();
								if (score <= parentNodeScore) {
									break;
								}

							}
							break;
						case MAX:
							score = Integer.MIN_VALUE;
							for (Move move : moveList) {
								currentBoard.makeMove(move);
								Thread.sleep(0);
								currentBoard.generateMoves(otherPlayerColour);
								if (bestMove == true && move == previousMoves[currentDepth]) {
									if (previousMoves.length > currentDepth + 1) {
										testScore = miniMax(currentDepth + 1, MIN, score, true);
									} else {
										testScore = miniMax(currentDepth + 1, MIN, score, false);
									}
								} else {
									testScore = miniMax(currentDepth + 1, MIN, score, false);
								}
								if (testScore > score) {
									score = testScore;
									futureMoves[currentDepth][currentDepth] = move;
									//copy other moves
									for (int i = currentDepth + 1; i < maxDepth; i++) {
										futureMoves[currentDepth][i] = futureMoves[currentDepth + 1][i];
									}
								}
								currentBoard.undoMove();
								if (score >= parentNodeScore) {
									break;
								}

							}
							break;
					}
					//end switch
					return score;
				}
			} catch (InterruptedException exception) {
				currentBoard.undoMove();
				throw exception;
			}
		}
	}
}
