/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antichess;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author mikeakey
 */
public class Game {

   private static final int FRAME_SIZE = 600;
   private boolean gameRunning = true;
   final static int HUMAN_PLAYER = 0;
   final static int AI_PLAYER = 1;
   final static int NETWORK_PLAYER = 2;

   public Game(int whitePlayerType, int blackPlayerType) {
      HumanBoard currentBoard = new HumanBoard(FRAME_SIZE);

      switch (whitePlayerType) {
         case HUMAN_PLAYER:
            //
            break;
         case AI_PLAYER:
            //
            break;
         case NETWORK_PLAYER:
            //
            break;
      }

      switch (blackPlayerType) {
         case HUMAN_PLAYER:
            //
            break;
         case AI_PLAYER:
            //
            break;
         case NETWORK_PLAYER:
            //
            break;
      }
   }

   public void runGame() {
      char playerColour = 'w';
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

      while (gameRunning) {
         currentBoard.isFinished(playerColour);

         currentBoard.generateMoves(playerColour);
         if (currentBoard.canMove()) {
            nextMove = getMove(currentBoard, playerColour);
            currentBoard.makeMove(nextMove);
            currentBoard.repaint();
            currentBoard.setVisible(true);
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

         if (playerColour == 'b') {
            playerColour = 'w';
         } else {
            playerColour = 'b';
         }
      }
   }

   public static Move getMove(HumanBoard currentBoard, char playerColour) {
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
