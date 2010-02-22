package antichess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Pawn extends Piece {

    public Pawn(int posX, int posY, char newColour) {
        super(posX, posY, newColour);
    }

    public char getAppearance() {
        if (this.colour == 'b') {
            return 'p';
        } else {
            return 'P';
        }
    }

    public BufferedImage getImage() {
        if (this.colour == 'b') {
            try {
                img = ImageIO.read(new File("./images/Black_pawn.png"));

            } catch (IOException ex) {
                Logger.getLogger(Pawn.class.getName()).log(Level.SEVERE, null, ex);
                img = null;
            }

        } else {
            try {
                img = ImageIO.read(new File("./images/White_pawn.png"));

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



        int xDiff = move.newX - move.oldX;


        int yDiff = move.newY - move.oldY;


        int xDiffAbs = Math.abs(xDiff);


        int yDiffAbs = Math.abs(yDiff);

        // Capture case


        if (this.colour == 'w' && board.isMoveCapture(move)) {
            if (xDiffAbs == 1 && yDiff == 1) {
                return true;


            } else {
                return false;


            }
        } else if (this.colour == 'b' && board.isMoveCapture(move)) {
            if (xDiffAbs == 1 && yDiff == -1) {
                return true;


            } else {
                return false;


            }
        }

        // First move case
        if (this.colour == 'w' && oldY == 1) {
            if (yDiff <= 2 && yDiff > 0 && xDiff == 0 && board.isPathClear(move)) {
                return true;


            } else {
                return false;


            }
        } else if (this.colour == 'b' && oldY == 6) {
            if (yDiff >= -2 && yDiff < 0 && xDiff == 0 && board.isPathClear(move)) {
                return true;


            } else {
                return false;


            }
        } // other case
        else if (this.colour == 'w') {
            if (xDiff == 0 && yDiff == 1) {
                return true;


            } else {
                return false;


            }
        } else if (this.colour == 'b') {
            if (xDiff == 0 && yDiff == -1) {
                return true;


            } else {
                return false;


            }
        }

        return true;

    }
//   public boolean isCapturePossible(Board board) {
//      return true;
//   }
}
