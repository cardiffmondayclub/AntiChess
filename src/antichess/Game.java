/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antichess;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game {
   private boolean gameRunning = true;
   private Player whitePlayer = null;
   private Player blackPlayer = null;
   private Board currentBoard;
   private HumanBoard currentHumanBoard;

   public Game(int whitePlayerType, int blackPlayerType) {
      currentBoard = new Board();
      currentHumanBoard = new HumanBoard(currentBoard, Definitions.FRAME_SIZE);

      switch (whitePlayerType) {
         case Definitions.HUMAN_PLAYER:
            whitePlayer = new HumanPlayer(currentBoard, currentHumanBoard, Definitions.WHITE);
            break;
         case Definitions.AI_PLAYER:
            //
            break;
         case Definitions.NETWORK_PLAYER:
            //
            break;
      }

      switch (blackPlayerType) {
         case Definitions.HUMAN_PLAYER:
            blackPlayer = new HumanPlayer(currentBoard, currentHumanBoard, Definitions.BLACK);
            break;
         case Definitions.AI_PLAYER:
            //
            break;
         case Definitions.NETWORK_PLAYER:
            //
            break;
      }
   }

   public void runGame() {
      Player currentPlayer = whitePlayer;
      Player otherPlayer = blackPlayer;

      Move nextMove = null;

      currentHumanBoard.addWindowListener(new WindowAdapter() {

         @Override
         public void windowClosing(WindowEvent we) {
            currentHumanBoard.setVisible(false);
         }
      });

      currentHumanBoard.setSize(Definitions.FRAME_SIZE, Definitions.FRAME_SIZE);
      currentHumanBoard.setVisible(true);

      while (gameRunning) {
         currentBoard.isFinished(currentPlayer.getPlayerColour());

         currentBoard.generateMoves(currentPlayer.getPlayerColour());
         if (currentBoard.canMove()) {
            nextMove = currentPlayer.getMove();
            currentBoard.makeMove(nextMove);
            otherPlayer.sendMove(nextMove);
            currentHumanBoard.repaint();
            currentHumanBoard.setVisible(true);
         }

         int end = currentBoard.isFinished(currentPlayer.getPlayerColour());

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

         if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
            otherPlayer = whitePlayer;
         } else {
            currentPlayer = whitePlayer;
            otherPlayer = blackPlayer;
         }
      }
   }

  
}
