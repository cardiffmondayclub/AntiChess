package antichess;

public class Human extends Player {
private HumanBoard currentBoard;

   public Human(HumanBoard currentBoard, int colour) {
      super(colour);
      this.currentBoard = currentBoard;
   }

   public Move getMove() {
      // Crude code that prints whose go it is
      System.out.print(playerColour + ": ");

      //Check if a capture is required in the next move.
      boolean captureRequired = currentBoard.isCapturePossible(playerColour);

      while (true) {
         Move nextMove = null;
         try {
            nextMove = currentBoard.getMove();
         } catch (InterruptedException e) {
            System.exit(-1);
         }

         if (currentBoard.isMoveValid(playerColour, nextMove)) {
            if (captureRequired) {
               if (currentBoard.isMoveCapture(nextMove)) {
                  return nextMove;
               } else {
                  System.out.println("You are able capture therefore you must.");
                  for (int i = 0; i < currentBoard.validCaptures.size(); i++) {
                     System.out.print(currentBoard.validCaptures.get(i).oldX + 1);
                     System.out.print(currentBoard.validCaptures.get(i).oldY + 1);
                     System.out.print(currentBoard.validCaptures.get(i).newX + 1);
                     System.out.print(currentBoard.validCaptures.get(i).newY + 1);
                     System.out.println();
                  }
               }

            } else {
               return nextMove;
            }
         } else {
            System.out.println("This move is not valid.");
         }
      }
   }
}
