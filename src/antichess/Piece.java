package antichess;

import java.util.ArrayList;

public abstract class Piece
{
	public int pieceType = 0;
	protected int xPosition;
	protected int yPosition;
	public int colour;

	public Piece(int posX, int posY, int newColour, int pieceType)
	{
		xPosition = posX;
		yPosition = posY;
		colour = newColour;
		this.pieceType = pieceType;
	}

	public void setPosition(int posX, int posY)
	{
		xPosition = posX;
		yPosition = posY;
	}

	public int pieceColour()
	{
		//Pretty self explanatory.
		return colour;
	}

	public boolean isPlayersPiece(int playerColour)
	{
		//Pretty self explanatory.
		return playerColour == colour;
	}

	// Returns the colour of the square that the piece is on
	public int getSquareColour()
	{
		if ((this.xPosition + this.yPosition) % 2 == 1) {
			return Definitions.WHITE;
		} else {
			return Definitions.BLACK;
		}
	}
	
	//Checks if a move is valid. I'm hoping the Board.isPathClear() function
	//could be used here.
	public abstract boolean isMoveValid(Board board, Move move);

   public abstract String getPieceName();

   public abstract int getPieceType();

	public abstract void generateMoves(Piece[][] squares, ArrayList<Move> validMoves, ArrayList<Move> validCaptures);
}
