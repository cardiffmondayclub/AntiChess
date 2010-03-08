package antichess;

import java.util.Scanner;
import java.awt.event.*;

public class Main {

   // MA - new variable to set frame size of the board
   private static final int FRAME_SIZE = 600;
   private static boolean gameRunning = true;

   public static void main(String[] args) {

      //Allows the user to select a colour and stores it as either 'b' or 'w'.
      char playerColour = selectColour();

      //Intialises a new board and stores a reference to it in currentBoard.
      //MA - added parameter frame_size because the board is now drawn in a frame as well
      final HumanBoard currentBoard = new HumanBoard(FRAME_SIZE);

      Move nextMove = null;
      currentBoard.addWindowListener(new WindowAdapter() {

         @Override
         public void windowClosing(WindowEvent we) {
            currentBoard.setVisible(false);
         }
      });

      currentBoard.setSize(FRAME_SIZE, FRAME_SIZE);
      currentBoard.setVisible(true);

      //Take special action if the player is white.
      //Basically just get a valid move, then make it and send to the server.
      if (playerColour == 'w') {
         // store valid moves and captures


         nextMove = getMove(currentBoard, playerColour);
         currentBoard.makeMove(nextMove);
         // currentBoard.drawBoard();

         int end = currentBoard.isFinished(playerColour);

         // MA - Repaints the board after first move
         currentBoard.repaint();
         currentBoard.setVisible(true);

         sendMove(nextMove);
      }


      while (gameRunning) {
         if (playerColour == 'b') {
            playerColour = 'w';
         } else {
            playerColour = 'b';
         }

         currentBoard.generateMoves(playerColour);
         // if player cant move then continue to next loop
         if (currentBoard.canMove()) {


            //Get the next move from the player.
            nextMove = getMove(currentBoard, playerColour);

            //Make the move specified by the player.
            currentBoard.makeMove(nextMove);

            //Draw the new board.
            // currentBoard.drawBoard();

            // MA - Repaints the board after  each move
            currentBoard.repaint();
            currentBoard.setVisible(true);

            //Send the move to the server.
            sendMove(nextMove);

         }

         int end = currentBoard.isFinished(playerColour);

         switch (end) {
            case Board.LOCKED_STALEMATE:
               System.out.println("Neither of you can move so its stalemate!");
               gameRunning = false;
               break;
            case Board.DERIVED_STALEMATE:
               System.out.println("Neither of you can win so its stalemate!");
               gameRunning = false;
               break;
            case Board.WHITE_WINS:
               System.out.println("White wins!");
               gameRunning = false;
               break;
            case Board.BLACK_WINS:
               System.out.println("Black wins!");
               gameRunning = false;
               break;
            default:
         }
      }
   }

   public static char selectColour() {
      /* Loops infinitely until either 'b' or 'w' is entered and then
       * returns whichever letter was entered
       */
      while (true) {
         System.out.println("Select your colour, 'b' for black or 'w' for white");
         Scanner in = new Scanner(System.in);
         String selectedColour = in.nextLine();
         if (selectedColour.equals("b")) {
            return 'b';
         } else if (selectedColour.equals("w")) {
            return 'w';
         }
      }
   }

   public static Move getMove(HumanBoard currentBoard, char playerColour) {
      // Crude code that prints whose go it is
      System.out.print(playerColour + ": ");

      //Check if a capture is required in the next move.
      boolean captureRequired = currentBoard.isCapturePossible(playerColour);

      while (true) {
         Move nextMove = getInput(currentBoard);
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

   public static Move getInput(HumanBoard currentBoard) {
      try {
         return currentBoard.getMove();
      } catch (InterruptedException e) {
         System.exit(-1);
         return null;
      }
   }

   public static void sendMove(Move move) {
      //Sends the move to the server.
      String toServer;
      toServer = Integer.toString(move.oldX);
      toServer += Integer.toString(move.oldY);
      toServer += Integer.toString(move.newX);
      toServer += Integer.toString(move.newY);

      //TO DO
      //Now send the string toServer to the server. The server will probably
      //have to be passed to the function.
   }

   public static boolean receiveMove(Move move) {
      //Wait for response from server then return true if there is a move and
      //false if there isn't. The move object should be used to store the
      //received move. The server will probably have to be passed to the
      //function.
      String fromServer = "1020";
      //TO DO
      //Oviously this is just an example. We need to implement actually
      //receiving from the server.

      if (fromServer.equals("Win")) {
         System.out.println("You have won!");
         return false;
      } else if (fromServer.equals("Loss")) {
         System.out.println("You have lost!");
         return false;
      } else if (fromServer.equals("Stale")) {
         System.out.println("Stalemate");
         return false;
      } else {
         //TO DO
         //This needs to separate out the bits of the string and
         //convert to integers.
         move = new Move(0, 1, 0, 2);
         return true;
      }
   }
}
