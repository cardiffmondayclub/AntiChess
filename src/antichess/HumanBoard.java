/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antichess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.*;
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.geom.*;

public class HumanBoard extends Board {

   private double squareSize;
   private BufferedImage wTile;
   private BufferedImage bTile;
   //Testing mouse clicking stuff. MCS
   public Move mouseClick;
   private int firstX;
   private int firstY;
   private boolean secondClick;
   private boolean returnMove;
   private boolean refreshBoard;

   public HumanBoard(int frameSize) {
      super();
      //Testing mouse clicking stuff. MCS
      mouseClick = null;
      secondClick = false;
      returnMove = false;
      refreshBoard = false;
      firstX = -1;
      firstY = -1;
      MouseClickListener listener = new MouseClickListener();
      addMouseListener(listener);
      //MA - set square size
      squareSize = frameSize / 10;

      try {
         wTile = ImageIO.read(new File("./images/tile_white.png"));
      } catch (IOException ioe) {
         System.exit(-1);
      }
      try {
         bTile = ImageIO.read(new File("./images/tile_black.png"));
      } catch (IOException ioe) {
         System.exit(-1);
      }
   }

   public Move getMove() throws InterruptedException {
      while (true) {
         if (refreshBoard == true) {
            this.repaint();
            refreshBoard = false;
         }
         if (returnMove == true) {
            //System.out.println("ready to return");
            break;
         }
         Thread.sleep(10);
      }
      returnMove = false;
      return mouseClick;
   }

   //Testing mouse clicking stuff. MCS
   private class MouseClickListener extends MouseAdapter {

      @Override
      public void mouseClicked(MouseEvent event) {
         int x = event.getX() / 60 - 1;
         int y = 9 - event.getY() / 60 - 1;
         //System.out.println(x);
         //System.out.println(y);

         if (secondClick == true) {
            //do stuff if it is the second click
            //System.out.println("Working on the second click");
            secondClick = false;
            if (firstX != x || firstY != y) {
               //do stuff if the user clicks in the same square twice
               mouseClick = new Move(firstX, firstY, x, y);
               firstX = -1;
               firstY = -1;
               refreshBoard = true;
               returnMove = true;
            } else {
               firstX = -1;
               firstY = -1;
               refreshBoard = true;
            }
         } else {
            //do stuff if it is the first click
            firstX = x;
            firstY = y;
            secondClick = true;
            refreshBoard = true;
         }
      }
   }

   public void drawBoard() {
      for (int row = 7; row >= 0; row--) {
         System.out.format("%d ", row + 1);
         for (int col = 0; col < 8; col++) {
            if (squares[col][row] != null) {
               System.out.print(squares[col][row].getAppearance() + " ");
            } else {
               System.out.print("  ");
            }
         }
         System.out.println();
      }
      System.out.println("  a b c d e f g h ");
      System.out.println();
   }

   // MA - this paint method draws the board in a frame and uses the getAppearance method to show
   // where each piece is on the board as a red character for now so it can be seen on both the
   // white and black squares - TODO - change appearance of each piece to an image (possibly)
   @Override
   public void paint(Graphics g) {
      Graphics2D ga = (Graphics2D) g;
      for (int i = 7; i >= 0; i--) {
         for (int j = 7; j >= 0; j--) {
            double leftEdge = squareSize * (i + 1);
            double topEdge = squareSize * (8 - j);

            if ((i + j) % 2 == 1) //(Changed) - I may have the white and black squares in the wrong places but they are swapped by changing the 0 here to a 1
            {
               ga.drawImage(wTile, null, (int) leftEdge, (int) topEdge);

            } else {
               ga.drawImage(bTile, null, (int) leftEdge, (int) topEdge);
            }

            if (squares[i][j] != null) {
               ga.drawImage(squares[i][j].getImage(), null, (int) leftEdge + 10, (int) topEdge + 10);
            }
         }
      }
      if (firstX != -1) {
         Rectangle2D.Double highlightedSquare = new Rectangle2D.Double((firstX + 1) * 60, (8 - firstY) * 60, 60, 60);
         ga.setColor(Color.RED);
         ga.draw(highlightedSquare);
      }
   }
}
