package antichess;

public class Move {

   public int oldX;
   public int oldY;
   public int newX;
   public int newY;

   public Move(int oldX, int oldY, int newX, int newY) {
      this.oldX = oldX;
      this.oldY = oldY;
      this.newX = newX;
      this.newY = newY;
   }

	@Override
	public Move clone() {
		return new Move(oldX, oldY, newX, newY);
	}
}
