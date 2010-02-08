package antichess;

public class Move {
   int oldX;
   int oldY;
   int newX;
   int newY;

   public Move(int oldX, int oldY, int newX, int newY) {
      this.oldX = oldX;
      this.oldY = oldY;
      this.newX = newX;
      this.newY = newY;
   }
}
