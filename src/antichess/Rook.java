package antichess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Rook extends Piece {
   public Rook(int posX, int posY, char newColour) {
      super(posX, posY, newColour);
   }

   public char getAppearance() {
      if (this.colour == 'b') {
         return 'r';
      } else {
         return 'R';
      }
   }

    public BufferedImage getImage() {
        if (this.colour == 'b') {
            try {
                img = ImageIO.read(new File("./images/Black_rook.png"));

            } catch (IOException ex) {
                Logger.getLogger(Pawn.class.getName()).log(Level.SEVERE, null, ex);
                img = null;
            }

        } else {
            try {
                img = ImageIO.read(new File("./images/White_rook.png"));

            } catch (IOException ex) {
                Logger.getLogger(Pawn.class.getName()).log(Level.SEVERE, null, ex);
                img = null;
            }
        }
        return img;


    }

   public boolean isMoveValid(Board board, Move move) {
       int newX = move.newX;
       int newY = move.newY;
       int oldX = move.oldX;
       int oldY = move.oldY;

       int xDiff = Math.abs(oldX - newX);  // number of squares moved in x
       int yDiff = Math.abs(oldY - newY);  // number of squares moved in y

       if( (xDiff == 0 || yDiff == 0) && board.isPathClear(move) )
       {
           return true;
       }
       else
       {
           return false;
       }
       
   }

//   public boolean isCapturePossible(Board board) {
//      return true;
//   }
}
