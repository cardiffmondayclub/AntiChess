package antichess;

public class Definitions {

   final static int WHITE = 0;
   final static int BLACK = 1;
   final static int NO_COLOUR = -1;
   final static int FRAME_SIZE = 600;
   final static int HUMAN_PLAYER = 0;
   final static int AI_PLAYER = 1;
   final static int NETWORK_PLAYER = 2;

	final static int NO_PIECE = -1;
   final static int PAWN = 0;
   final static int KNIGHT = 1;
   final static int BISHOP = 2;
   final static int ROOK = 3;
   final static int QUEEN = 4;
   final static int KING = 5;
	final static int[] PROMOTION_PIECES = {KNIGHT, BISHOP, ROOK, QUEEN, KING};

	final static int NO_WIN = -1;
	final static int WHITE_WINS = 0;
	final static int BLACK_WINS = 1;
	final static int LOCKED_STALEMATE = 2;
	final static int DERIVED_STALEMATE = 3;
}
