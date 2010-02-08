package antichess;

import java.util.Scanner;

public class Main {

   public static void main(String[] args) {
      
      char colour = selectColour();
      Board currentBoard = new Board();
      boolean captureRequired = false;
      Move nextMove;
      nextMove = new Move(0, 0, 0, 0);

      //Connect to a server used colour to determine which port to use.

      currentBoard.drawBoard();

      //Take special action if the player is white.
      if (colour == 'w') {
         while (true) {
            nextMove = getInput();
            if (currentBoard.isMoveValid(colour, nextMove) == true) {
               break;
            }
         }
         currentBoard.makeMove(nextMove);
         sendMove(nextMove);
      }

      while (true) { //The main loop

         /*This passes the nextMove object to the receiveMode function
          * if receiveMove returns false the game is over and if it returns true
          * a move has been received.
          */
         if (receiveMove(nextMove) == false) {
            break;
         }
         currentBoard.makeMove(nextMove);

         currentBoard.drawBoard();

         captureRequired = currentBoard.isCapturePossible(colour);

         while (true) {
            nextMove = getInput();
            if (currentBoard.isMoveValid(colour, nextMove)) {
               if (captureRequired) {
                  if (currentBoard.isMoveCapture(nextMove)) {
                     break;
                  }
               } else {
                  break;
               }
            }
         }

         currentBoard.makeMove(nextMove);

         currentBoard.drawBoard();

         sendMove(nextMove);
      }
   }

   public static char selectColour() {
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

   public static Move getInput() {
      Scanner in = new Scanner(System.in);
      System.out.println("Please enter your next move");
      String move = in.nextLine();
      //This is more complex than my brain can deal with right now.
      //Anyone want to do it?
      return new Move(0, 0, 0, 0);
   }

   public static void sendMove(Move move) {
      //Code to send the move to the server.
      String toServer;
      toServer = Integer.toString(move.oldX);
      toServer += Integer.toString(move.oldY);
      toServer += Integer.toString(move.newX);
      toServer += Integer.toString(move.newY);

      //Now send the string toServer to the server. The server will probably
      //have to be passed to the function.
   }

   public static boolean receiveMove(Move move) {
      //Wait for response from server then return true if there is a move and
      //false if there isn't. The move object should be used to store the
      //received move. The server will probably have to be passed to the
      //function.

      String fromServer = "1020";
      //Oviously this is just an example. We need to implement actually
      //receiving from the server.

      if (fromServer.equals("Win")) {
         System.out.println("You have won!");
         return false;
      } else if (fromServer.equals("Loss")) {
         System.out.println("You have lost!");
         return false;
      }
      return true;
   }
}
