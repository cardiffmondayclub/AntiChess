package antichess;

public abstract class Player {
   // Instance Variables
   public static final int WHITE = 0;
   public static final int BLACK = 1;

   protected int playerColour;

   // construct player with variable player colour
   public Player(int colour) {
      playerColour = colour;
   }
   
   public int getPlayerColour() {
      return playerColour;
   }

   public abstract Move getMove();

}
