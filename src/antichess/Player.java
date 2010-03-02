package antichess;

public abstract class Player {
   // Instance Variables
   public static final int WHITE = 0;
   public static final int BLACK = 1;

   private int playerColour;

   // construct player with variable player colour
   public Player(boolean starts) {
      if( starts ) {
         playerColour = WHITE;
      }
      else {
         playerColour = BLACK;
      }

   }
   
   public int getPlayerColour() {
      return playerColour;
   }
}
