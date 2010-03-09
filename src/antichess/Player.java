package antichess;

public abstract class Player {
   // Instance Variables
   public static final int WHITE = 0;
   public static final int BLACK = 1;

   protected int playerColour;
	protected int otherPlayerColour;

   // construct player with variable player colour
   public Player(int colour) {
      playerColour = colour;

		if (playerColour==Definitions.WHITE) {
			otherPlayerColour = Definitions.BLACK;
		} else {
			otherPlayerColour = Definitions.WHITE;
		}
   }
   
   public int getPlayerColour() {
      return playerColour;
   }

   public abstract Move getMove();

   public abstract void sendMove(Move move);

}
